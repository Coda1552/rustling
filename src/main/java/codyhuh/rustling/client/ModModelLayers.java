package codyhuh.rustling.client;

import codyhuh.rustling.RustlingMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation RUSTLING = create("rustling");
    public static final ModelLayerLocation RUSTMUNCHER = create("rustmuncher");

    private static ModelLayerLocation create(String name) {
        return new ModelLayerLocation(new ResourceLocation(RustlingMod.MOD_ID, name), "main");
    }
}
