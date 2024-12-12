package codyhuh.rustling.client.renders;

import codyhuh.rustling.RustlingMod;
import codyhuh.rustling.client.ModModelLayers;
import codyhuh.rustling.client.models.RustMuncherModel;
import codyhuh.rustling.common.entities.RustMuncherEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RustMuncherRenderer extends MobRenderer<RustMuncherEntity, RustMuncherModel<RustMuncherEntity>> {

    public RustMuncherRenderer(EntityRendererProvider.Context p_173954_) {
        super(p_173954_, new RustMuncherModel<>(p_173954_.bakeLayer(ModModelLayers.RUSTMUNCHER)), 0.5F);
    }

    public ResourceLocation getTextureLocation(RustMuncherEntity pEntity) {
        return new ResourceLocation(RustlingMod.MOD_ID, "textures/entity/rustmuncher/rustmuncher_" + pEntity.getVariantName() + ".png");
    }
}
