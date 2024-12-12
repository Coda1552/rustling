package codyhuh.rustling.common.entities.ai;

import codyhuh.rustling.common.entities.RustMuncherEntity;
import codyhuh.rustling.common.entities.Rustling;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.EnumSet;

public class ShearRustlingGoal extends Goal {
    private final RustMuncherEntity muncher;
    private final double speedModifier;
    @javax.annotation.Nullable
    private Rustling rustling;
    private int nextStartTick;

    public ShearRustlingGoal(RustMuncherEntity pMuncher, double pSpeedModifier) {
        this.muncher = pMuncher;
        this.speedModifier = pSpeedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(this.muncher);
            this.rustling = this.muncher.level().getNearestEntity(Rustling.class, RustMuncherEntity.SHEAR_RUSTLING, this.muncher, this.muncher.getX(), this.muncher.getY(), this.muncher.getZ(), this.muncher.getBoundingBox().inflate(6.0D, 2.0D, 6.0D));
            return this.rustling != null;
        }
    }

    protected int nextStartTick(PathfinderMob pCreature) {
        return reducedTickDelay(4200 + pCreature.getRandom().nextInt(800));
    }

    public boolean canContinueToUse() {
        return this.rustling != null && this.rustling.readyForShearing()
                && this.muncher.distanceToSqr(this.rustling) < 256.0D && this.muncher.wantsToMunch();
    }

    public void start() {
        this.muncher.setWantsToMunch(true);
    }

    public void stop() {
        this.rustling = null;
        this.muncher.getNavigation().stop();
        this.muncher.setWantsToMunch(false);
    }

    public void tick() {

        this.muncher.getLookControl().setLookAt(this.rustling, (float)(this.muncher.getMaxHeadYRot() + 20), (float)this.muncher.getMaxHeadXRot());

        if (this.muncher.distanceToSqr(this.rustling) < 0.1D) {
            this.muncher.getNavigation().stop();
        }else {
            this.muncher.getNavigation().moveTo(this.rustling, this.speedModifier);
        }

    }
}
