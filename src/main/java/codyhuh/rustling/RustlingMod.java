package codyhuh.rustling;

import codyhuh.rustling.client.ClientEvents;
import codyhuh.rustling.common.entities.RustMuncherEntity;
import codyhuh.rustling.common.entities.Rustling;
import codyhuh.rustling.registry.*;
import codyhuh.rustling.util.RustlingBrewingRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
        ModPotions.register(bus);
        ModSounds.register(bus);

        bus.addListener(this::createAttributes);

        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);
    }

    private void createAttributes(EntityAttributeCreationEvent e) {
        e.put(ModEntities.RUSTLING.get(), Rustling.createRustlingAttributes().build());
        e.put(ModEntities.RUSTMUNCHER.get(), RustMuncherEntity.createAttributes().build());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BrewingRecipeRegistry.addRecipe(new RustlingBrewingRecipe(Potions.AWKWARD,
                    ModItems.RUST.get(), ModPotions.TETANUS_POTION.get()));

            BrewingRecipeRegistry.addRecipe(new RustlingBrewingRecipe(ModPotions.TETANUS_POTION.get(),
                    Items.REDSTONE, ModPotions.TETANUS_POTION_2.get()));
        });
    }
}
