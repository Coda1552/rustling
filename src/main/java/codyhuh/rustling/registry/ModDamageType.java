package codyhuh.rustling.registry;

import codyhuh.rustling.RustlingMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageType {
    public static final ResourceKey<DamageType> RUSTICLE = ResourceKey.create(Registries.DAMAGE_TYPE,
            new ResourceLocation(RustlingMod.MOD_ID, "falling_rusticle"));
}
