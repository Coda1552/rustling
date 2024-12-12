package codyhuh.rustling.registry;

import codyhuh.rustling.RustlingMod;
import codyhuh.rustling.common.block.RusticleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RustlingMod.MOD_ID);

    public static final RegistryObject<Block> RUST_BLOCK = BLOCKS.register("rust_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));

    public static final RegistryObject<Block> RUSTICLE = BLOCKS.register("rusticle", () -> new RusticleBlock(BlockBehaviour.Properties.copy(Blocks.POINTED_DRIPSTONE)));
}
