package codyhuh.rustling;

import codyhuh.rustling.client.ClientEvents;
import codyhuh.rustling.common.entities.Rustling;
import codyhuh.rustling.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RustlingMod.MOD_ID)
public class RustlingMod {
    public static final String MOD_ID = "rustling";

    public RustlingMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModItems.ITEMS.register(bus);
        ModTabs.CREATIVE_MODE_TABS.register(bus);
        ModEffects.register(bus);

        bus.addListener(this::createAttributes);

        bus.addListener(this::clientSetup);
    }

    private void createAttributes(EntityAttributeCreationEvent e) {
        e.put(ModEntities.RUSTLING.get(), Rustling.createRustlingAttributes().build());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }
}
