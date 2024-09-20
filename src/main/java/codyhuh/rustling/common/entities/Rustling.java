package codyhuh.rustling.common.entities;

import codyhuh.rustling.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Rustling extends Animal implements IForgeShearable, Shearable {
    private static final EntityDataAccessor<Integer> RUST_LEVEL = SynchedEntityData.defineId(Rustling.class, EntityDataSerializers.INT);
    public int increaseRustTime = 6000;

    public Rustling(EntityType<? extends Animal> type, Level level) {
        super(type, level);
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
        return source != damageSources().sweetBerryBush() && super.hurt(source, p_27568_);
    }

    @Override
    protected void checkFallDamage(double p_20990_, boolean p_20991_, BlockState p_20992_, BlockPos p_20993_) {
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RUST_LEVEL, 0);
    }

    public int getRustLevel() {
        return this.entityData.get(RUST_LEVEL);
    }

    public void setRustLevel(int rustLevel) {
        this.entityData.set(RUST_LEVEL, rustLevel);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setRustLevel(tag.getInt("RustLevel"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("RustLevel", getRustLevel());
    }

    @Override
    public void tick() {
        super.tick();

        if (getRustLevel() < 3 && this.isAlive() && --this.increaseRustTime <= 0 && level().canSeeSky(blockPosition()) && level().isRainingAt(blockPosition())) {
            --this.increaseRustTime;
        }

        if (getRustLevel() < 3 && this.isAlive() && --this.increaseRustTime <= 0) {
            this.playSound(SoundEvents.AXE_SCRAPE, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.setRustLevel(getRustLevel() + 1);
            this.increaseRustTime = 6000;
        }
    }

    @Override
    public @NotNull List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, Level level, BlockPos pos, int fortune) {
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
        this.level().playSound(null, this, SoundEvents.AXE_SCRAPE, soundSource, 1.0F, 1.0F);
        this.setRustLevel(0);

        ItemEntity itementity = spawnAtLocation(new ItemStack(ModItems.RUST.get(), getRustLevel() * 2));
        if (itementity != null) {
            itementity.moveTo(position());
            itementity.setDeltaMovement(itementity.getDeltaMovement().add(((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (this.random.nextFloat() * 0.05F), ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
        }
    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && getRustLevel() > 0;
    }
}
