package codyhuh.rustling.registry;

import codyhuh.rustling.RustlingMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RustlingMod.MOD_ID);

    public static final RegistryObject<Item> RUST = ITEMS.register("rust", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUST_BLOCK = ITEMS.register("rust_block", () -> new BlockItem(ModBlocks.RUST_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> RUSTLING_SPAWN_EGG = ITEMS.register("rustling_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.RUSTLING, 0x4d7a41, 0xed9c3c, new Item.Properties()));
}
