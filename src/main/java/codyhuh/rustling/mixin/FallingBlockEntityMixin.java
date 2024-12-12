package codyhuh.rustling.mixin;

import codyhuh.rustling.registry.ModBlocks;
import codyhuh.rustling.registry.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity{

    @Shadow private boolean hurtEntities;

    @Shadow private BlockState blockState;

    public FallingBlockEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(
            method = {"causeFallDamage"},
            cancellable = true,
            at = @At(value = "HEAD")
    )
    private void applyEffect(CallbackInfoReturnable<Boolean> infoReturnable){
        if (this.hurtEntities){
            if (this.blockState.is(ModBlocks.RUSTICLE.get())){
                Predicate<Entity> predicate = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);

                this.level().getEntities(this, this.getBoundingBox(), predicate).forEach((entity) -> {
                    if (entity instanceof LivingEntity living){
                        living.addEffect(new MobEffectInstance(ModEffects.TETANUS.get(), 90, 0));
                    }
                });
            }
        }

    }
}
