package codyhuh.rustling.common.entities.ai;

import codyhuh.rustling.common.entities.RustMuncherEntity;
import codyhuh.rustling.common.entities.Rustling;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class DefendRustlingTargetGoal extends TargetGoal {

    private final RustMuncherEntity rustMuncher;
    @Nullable
    private LivingEntity potentialTarget;
    private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D);

    public DefendRustlingTargetGoal(RustMuncherEntity muncher) {
        super(muncher, false, true);
        this.rustMuncher = muncher;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean canUse() {
        AABB aabb = this.rustMuncher.getBoundingBox().inflate(10.0D, 8.0D, 10.0D);
        List<? extends LivingEntity> list = this.rustMuncher.level().getNearbyEntities(Rustling.class, this.attackTargeting, this.rustMuncher, aabb);
        List<Player> list1 = this.rustMuncher.level().getNearbyPlayers(this.attackTargeting, this.rustMuncher, aabb);

        for(LivingEntity livingentity : list) {
            Rustling rustling = (Rustling)livingentity;

            for(Player player : list1) {
                int i = rustling.getAttackedByPlayerTime();
                if (i > 0) {
                    this.potentialTarget = player;
                }
            }
        }

        if (this.potentialTarget == null) {
            return false;
        } else {
            return !(this.potentialTarget instanceof Player) || !this.potentialTarget.isSpectator() && !((Player)this.potentialTarget).isCreative();
        }
    }

    public void start() {
        this.rustMuncher.setTarget(this.potentialTarget);
        super.start();
    }
}
