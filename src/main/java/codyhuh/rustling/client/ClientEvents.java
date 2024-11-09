package codyhuh.rustling.client;

import codyhuh.rustling.registry.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class ClientEvents {

    private static final UUID SPEED_UUID = UUID.fromString("dcd9ce31-977d-4e43-9be4-98c04ef49db8");
    private static final UUID SLOW_UUID = UUID.fromString("4eadfa87-767f-4bd8-bdba-f443cc94e14b");

    @SubscribeEvent
    public void computeCameraAngles(ViewportEvent.ComputeCameraAngles event) {

        Entity player = Minecraft.getInstance().getCameraEntity();
        float partialTick = Minecraft.getInstance().getPartialTick();

        if (player != null && player instanceof LivingEntity livingEntity && livingEntity.hasEffect(ModEffects.TETANUS.get())) {

            AttributeInstance attributeinstance = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);

            if (attributeinstance != null) {
                if (attributeinstance.getModifier(SPEED_UUID) != null) {
                    //speed
                    event.setPitch(event.getPitch() + (float) (Math.sin((player.tickCount + partialTick) * 0.2F) * 10F));
                }
                if (attributeinstance.getModifier(SLOW_UUID) != null) {
                    //slowness
                    event.setYaw(event.getYaw() + (float) (Math.sin((player.tickCount + partialTick) * 0.2F) * 10F));
                }

            }
        }
    }
}
