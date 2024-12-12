package codyhuh.rustling.registry;

import codyhuh.rustling.RustlingMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, RustlingMod.MOD_ID);

    public static final RegistryObject<Potion> TETANUS_POTION = POTIONS.register("tetanus_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.TETANUS.get(), 1800, 0)));

    public static final RegistryObject<Potion> TETANUS_POTION_2 = POTIONS.register("tetanus_potion_2",
            () -> new Potion(new MobEffectInstance(ModEffects.TETANUS.get(), 2400, 0)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
