package codyhuh.rustling.client.renders;

import codyhuh.rustling.RustlingMod;
import codyhuh.rustling.client.ModModelLayers;
import codyhuh.rustling.client.models.RustlingModel;
import codyhuh.rustling.common.entities.Rustling;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RustlingRenderer extends MobRenderer<Rustling, RustlingModel<Rustling>> {

   public RustlingRenderer(EntityRendererProvider.Context p_173954_) {
      super(p_173954_, new RustlingModel<>(p_173954_.bakeLayer(ModModelLayers.RUSTLING)), 0.5F);
   }

   public ResourceLocation getTextureLocation(Rustling pEntity) {
      return new ResourceLocation(RustlingMod.MOD_ID, "textures/entity/rustling/rustling_" + pEntity.getRustLevel() + ".png");
   }
}