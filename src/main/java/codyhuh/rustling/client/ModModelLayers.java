package codyhuh.rustling.client;

import codyhuh.rustling.RustlingMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation RUSTLING = create("rustling");

    private static ModelLayerLocation create(String name) {
        return new ModelLayerLocation(new ResourceLocation(RustlingMod.MOD_ID, name), "main");
    }
}
