package codyhuh.rustling.common.entities;

import codyhuh.rustling.registry.ModEffects;
import codyhuh.rustling.registry.ModEntities;
import codyhuh.rustling.registry.ModItems;
import codyhuh.rustling.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Rustling extends Animal implements IForgeShearable, Shearable {

    private static final EntityDataAccessor<Integer> BREEDING_COOLDOWN = SynchedEntityData.defineId(Rustling.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RUST_LEVEL = SynchedEntityData.defineId(Rustling.class, EntityDataSerializers.INT);

    private int increaseRustTime;
    private int attackedByPlayerTime;
    private int prevBreedingCooldown;

    public Rustling(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        this.setMaxUpStep(1);
        this.increaseRustTime = random.nextInt(6000) + (2000);
    }

    public static AttributeSupplier.Builder createRustlingAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.75D);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return type != Fluids.WATER.getFluidType() && type != Fluids.FLOWING_WATER.getFluidType();
    }

    @Override
    public boolean hurt(DamageSource source, float p_27568_) {
        if (source.is(DamageTypes.PLAYER_ATTACK)){
            this.attackedByPlayerTime = 60*20;
        }
        return source != damageSources().sweetBerryBush() && super.hurt(source, p_27568_);
    }

    @Override
    protected void checkFallDamage(double p_20990_, boolean p_20991_, BlockState p_20992_, BlockPos p_20993_) {
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RUST_LEVEL, 0);
        this.entityData.define(BREEDING_COOLDOWN, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setRustLevel(tag.getInt("RustLevel"));
        this.setBreedingCooldown(tag.getInt("BreedingCooldown"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("RustLevel", getRustLevel());
        tag.putInt("BreedingCooldown", getBreedingCooldown());
    }

    public int getRustLevel() {
        return this.entityData.get(RUST_LEVEL);
    }

    public void setRustLevel(int rustLevel) {
        this.entityData.set(RUST_LEVEL, rustLevel);
    }

    public int getBreedingCooldown() {
        return this.entityData.get(BREEDING_COOLDOWN);
    }

    public void setBreedingCooldown(int breedingCooldown) {
        this.entityData.set(BREEDING_COOLDOWN, breedingCooldown);
    }

    public int getAttackedByPlayerTime(){
        return this.attackedByPlayerTime;
    }

    @Override
    public void tick() {

        if (this.getBreedingCooldown() > 0){
            this.prevBreedingCooldown = this.getBreedingCooldown();
            this.setBreedingCooldown(prevBreedingCooldown-1);
        }

        super.tick();
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.attackedByPlayerTime >0){
            --this.attackedByPlayerTime;
        }

        if (getRustLevel() < 3 && this.isAlive() && this.increaseRustTime > 0) {
            --this.increaseRustTime;

            if (level().canSeeSky(blockPosition()) && level().isRainingAt(blockPosition()) && this.increaseRustTime > 0){
                --this.increaseRustTime;
            }
        }

        if (getRustLevel() < 3 && this.isAlive() && this.increaseRustTime == 0) {
            this.playSound(SoundEvents.AXE_SCRAPE, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.setRustLevel(getRustLevel() + 1);
            this.increaseRustTime = random.nextInt(6000) + (2000);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.is(Items.GLOW_BERRIES) && this.getBreedingCooldown() <= 0){
            this.duplicateRustling();
            this.level().broadcastEntityEvent(this, (byte)18);
            this.level().playSound(player, this, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.NEUTRAL, 2.0F, 1.0F);

            this.removeInteractionItem(player, itemstack);
            this.setBreedingCooldown(20*60*20);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    private void removeInteractionItem(Player player, ItemStack itemStack) {
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
    }

    private void duplicateRustling() {
        Rustling rustling = ModEntities.RUSTLING.get().create(this.level());
        if (rustling != null) {
            rustling.moveTo(this.position());
            rustling.setRustLevel(0);
            rustling.setBreedingCooldown(20*60*20);
            this.level().addFreshEntity(rustling);
        }

    }

    @Override
    public @NotNull List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, Level level, BlockPos pos, int fortune) {
        level.playSound(null, this, ModSounds.RUSTLING_SHEAR.get(), player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
        this.gameEvent(GameEvent.SHEAR, player);

        List<ItemStack> items = new ArrayList<>(List.of());

        items.add(new ItemStack(ModItems.RUST.get(), getRustLevel() * 2));
        setRustLevel(0);

        return items;
    }

    @Override
    public boolean isShearable(@NotNull ItemStack item, Level level, BlockPos pos) {
        return getRustLevel() > 0;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public void shear(SoundSource soundSource) {

        this.level().playSound(null, this, ModSounds.RUSTLING_SHEAR.get(), soundSource, 1.0F, 1.0F);

        ItemEntity itementity = spawnAtLocation(new ItemStack(ModItems.RUST.get(), getRustLevel() * 2));
        if (itementity != null) {
            itementity.moveTo(position());
            itementity.setDeltaMovement(itementity.getDeltaMovement().add(((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (this.random.nextFloat() * 0.05F), ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
        }

        this.setRustLevel(0);
    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && this.getRustLevel() > 0;
    }


    public boolean canBeAffected(MobEffectInstance pPotioneffect) {
        return pPotioneffect.getEffect() == ModEffects.TETANUS.get() ? false : super.canBeAffected(pPotioneffect);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.RUSTLING_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.RUSTLING_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.RUSTLING_DEATH.get();
    }
}
