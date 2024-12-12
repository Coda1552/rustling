package codyhuh.rustling.common.entities;

import codyhuh.rustling.common.entities.ai.DefendRustlingTargetGoal;
import codyhuh.rustling.common.entities.ai.ShearRustlingGoal;
import codyhuh.rustling.registry.ModEntities;
import codyhuh.rustling.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Predicate;

public class RustMuncherEntity extends Animal implements NeutralMob {

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(RustMuncherEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(RustMuncherEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> WANTS_TO_MUNCH = SynchedEntityData.defineId(RustMuncherEntity.class, EntityDataSerializers.BOOLEAN);

    private static final Predicate<LivingEntity> RUSTLING_SELECTOR = (entity) -> {
        return entity instanceof Rustling && ((Rustling)entity).getRustLevel() > 0;
    };

    public static final TargetingConditions SHEAR_RUSTLING = TargetingConditions.forNonCombat().range(20.0D).ignoreLineOfSight().selector(RUSTLING_SELECTOR);

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;

    public RustMuncherEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(ModItems.RUST.get()), false));
        this.targetSelector.addGoal(1, new DefendRustlingTargetGoal(this));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new ShearRustlingGoal(this, 1.5D));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(VARIANT, 0);
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
        this.entityData.define(WANTS_TO_MUNCH, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        pCompound.putInt("Variant", this.getVariant());
        pCompound.putBoolean("WantsToMunch", this.wantsToMunch());
        this.addPersistentAngerSaveData(pCompound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        this.setVariant(pCompound.getInt("Variant"));
        this.setWantsToMunch(pCompound.getBoolean("WantsToMunch"));
        this.readPersistentAngerSaveData(this.level(), pCompound);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(ModItems.RUST.get());
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public Boolean wantsToMunch() {
        return this.entityData.get(WANTS_TO_MUNCH);
    }

    public void setWantsToMunch(Boolean variant) {
        this.entityData.set(WANTS_TO_MUNCH, variant);
    }

    public String getVariantName() {
        return switch (this.getVariant()){
            case 1 -> "tool";
            case 2 -> "flask";
            case 3 -> "road";
            case 4 -> "spiral";
            default -> "blade";
        };
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.MAX_HEALTH, 24D)
                .add(Attributes.ATTACK_DAMAGE, 4D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75D);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        this.setVariant(this.random.nextInt(5));

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return type != Fluids.WATER.getFluidType() && type != Fluids.FLOWING_WATER.getFluidType();
    }

    @Override
    public boolean hurt(DamageSource source, float p_27568_) {
        return source != damageSources().sweetBerryBush() && super.hurt(source, p_27568_);
    }

    @Override
    protected void checkFallDamage(double p_20990_, boolean p_20991_, BlockState p_20992_, BlockPos p_20993_) {
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        RustMuncherEntity otherParent = (RustMuncherEntity) pOtherParent;
        RustMuncherEntity baby = ModEntities.RUSTMUNCHER.get().create(pLevel);

        if (baby != null){
            baby.setVariant(this.random.nextBoolean() ? this.getVariant() : otherParent.getVariant());
        }

        return baby;
    }

    @Override
    public void aiStep() {
        super.aiStep();


        if (!level().isClientSide && level().getGameTime() % 20 == 0 && this.wantsToMunch()) {
            for (Rustling living : this.level().getEntitiesOfClass(Rustling.class, this.getBoundingBox().inflate(1.0D, 1.0D, 1.0D))) {
                if (living.readyForShearing() && this.wantsToMunch()){
                   living.shear(SoundSource.NEUTRAL);
                   this.setWantsToMunch(false);
                }
            }
        }
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int pTime) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, pTime);
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID pTarget) {
        this.persistentAngerTarget = pTarget;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }
}
