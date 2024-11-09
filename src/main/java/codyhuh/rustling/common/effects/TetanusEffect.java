package codyhuh.rustling.common.effects;

import codyhuh.rustling.registry.ModEffects;
import com.google.common.collect.Maps;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import org.joml.Random;

import java.util.Map;
import java.util.UUID;

public class TetanusEffect extends MobEffect {

    private static final UUID SPEED_UUID = UUID.fromString("dcd9ce31-977d-4e43-9be4-98c04ef49db8");
    private static final UUID SLOW_UUID = UUID.fromString("4eadfa87-767f-4bd8-bdba-f443cc94e14b");

    public boolean isSpeed = false;
    private int changeCooldown = 100;

    public TetanusEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        AttributeInstance attributeinstance = pLivingEntity.getAttribute(Attributes.MOVEMENT_SPEED);

        if (attributeinstance != null) {

            removeSpeedModifier(pLivingEntity);

            if (changeCooldown < 100){
                changeCooldown++;
            }

            if (changeCooldown == 100){
                isSpeed= !isSpeed;
                changeCooldown = 0;
            }

            if (isSpeed){
                attributeinstance.addTransientModifier(new AttributeModifier(SPEED_UUID, "Tetanus speed",
                        0.035f, AttributeModifier.Operation.ADDITION));

            }else {
                attributeinstance.addTransientModifier(new AttributeModifier(SLOW_UUID, "Tetanus slow",
                        -0.025f, AttributeModifier.Operation.ADDITION));

            }
        }

    }


    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {

        return pDuration > 0;

    }

    protected void removeSpeedModifier(LivingEntity living) {
        AttributeInstance attributeinstance = living.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attributeinstance != null) {
            if (attributeinstance.getModifier(SPEED_UUID) != null) {
                attributeinstance.removeModifier(SPEED_UUID);
            }
            if (attributeinstance.getModifier(SLOW_UUID) != null) {
                attributeinstance.removeModifier(SLOW_UUID);
            }

        }
    }


    public void addAttributeModifiers(LivingEntity entity, AttributeMap map, int i) {
        super.addAttributeModifiers(entity, map, i);
    }

    public void removeAttributeModifiers(LivingEntity entity, AttributeMap map, int i) {
        super.removeAttributeModifiers(entity, map, i);
        removeSpeedModifier(entity);
    }
}
