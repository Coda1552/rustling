package codyhuh.rustling.client;

import codyhuh.rustling.RustlingMod;
import codyhuh.rustling.client.gui.TetanusGUI;
import codyhuh.rustling.client.models.RustMuncherModel;
import codyhuh.rustling.client.models.RustlingModel;
import codyhuh.rustling.client.renders.RustMuncherRenderer;
import codyhuh.rustling.client.renders.RustlingRenderer;
import codyhuh.rustling.registry.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RustlingMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BusClientEvents {

    @SubscribeEvent
    public static void registerRenders(EntityRenderersEvent.RegisterRenderers e) {
        e.registerEntityRenderer(ModEntities.RUSTLING.get(), RustlingRenderer::new);
        e.registerEntityRenderer(ModEntities.RUSTMUNCHER.get(), RustMuncherRenderer::new);
    }

    @SubscribeEvent
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
        e.registerLayerDefinition(ModModelLayers.RUSTLING, RustlingModel::createBodyLayer);
        e.registerLayerDefinition(ModModelLayers.RUSTMUNCHER, RustMuncherModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        MinecraftForge.EVENT_BUS.register(new TetanusGUI());
        event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), "tetanus", new TetanusGUI());
    }
}