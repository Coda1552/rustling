package codyhuh.rustling.registry;

import codyhuh.rustling.RustlingMod;
import codyhuh.rustling.common.entities.Rustling;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RustlingMod.MOD_ID);

    public static final RegistryObject<EntityType<Rustling>> RUSTLING = ENTITIES.register("rustling", () -> EntityType.Builder.of(Rustling::new, MobCategory.CREATURE).sized(0.625F, 1.2F).build("rustling"));
}
