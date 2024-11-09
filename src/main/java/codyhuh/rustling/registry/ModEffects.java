package codyhuh.rustling.registry;

import codyhuh.rustling.RustlingMod;
import codyhuh.rustling.common.effects.TetanusEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, RustlingMod.MOD_ID);

    public static final RegistryObject<MobEffect> TETANUS = MOB_EFFECTS.register("tetanus",
            ()-> new TetanusEffect(MobEffectCategory.HARMFUL, 0xb56a28));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
