package codyhuh.rustling.registry;

import codyhuh.rustling.RustlingMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RustlingMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("rustling_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + RustlingMod.MOD_ID))
            .icon(ModItems.RUST.get()::getDefaultInstance)
            .displayItems((itemDisplayParameters, output) -> ModItems.ITEMS.getEntries().forEach(itemRegistryObject -> output.accept(itemRegistryObject.get())))
            .build()
    );
}
