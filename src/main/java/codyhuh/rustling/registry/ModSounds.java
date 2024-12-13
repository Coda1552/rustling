package codyhuh.rustling.registry;

import codyhuh.rustling.RustlingMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RustlingMod.MOD_ID);

    public static final RegistryObject<SoundEvent> RUSTLING_IDLE = registerSoundEvents("rustling_idle");
    public static final RegistryObject<SoundEvent> RUSTLING_HURT = registerSoundEvents("rustling_hurt");
    public static final RegistryObject<SoundEvent> RUSTLING_DEATH = registerSoundEvents("rustling_death");
    public static final RegistryObject<SoundEvent> RUSTLING_SHEAR = registerSoundEvents("rustling_shear");

    public static final RegistryObject<SoundEvent> RUSTMUNCHER_IDLE = registerSoundEvents("rustmuncher_idle");
    public static final RegistryObject<SoundEvent> RUSTMUNCHER_HURT = registerSoundEvents("rustmuncher_hurt");
    public static final RegistryObject<SoundEvent> RUSTMUNCHER_DEATH = registerSoundEvents("rustmuncher_death");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(RustlingMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
