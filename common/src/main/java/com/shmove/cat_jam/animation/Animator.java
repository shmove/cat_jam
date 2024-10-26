package com.shmove.cat_jam.animation;

import com.shmove.cat_jam.access.JammingEntityModel;
import com.shmove.cat_jam.access.JammingEntityRenderState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Unique;

public final class Animator {

    @Unique
    private static final float[] JAM_PIVOTS = { 0.10f, 0.50f, 0.60f, 0.60f, 0.60f, 0.10f, 0.00f, 0.00f };
    @Unique
    private static final float[] JAM_ANGLES = { 0.05f, 0.15f, 0.18f, 0.20f, 0.15f, 0.05f, -0.02f, 0.00f };

    @Unique
    private static final float[] SLIGHT_NOD_ANGLES = { 0.05f, 0.08f, 0.10f, 0.05f, -0.02f, 0.00f };

    public static void animateHead(JammingEntityRenderState meowrender, JammingEntityModel meowdel) {
        if (!meowrender.cat_jam$isInValidStateToJam()) return;

        final ModelPart head = meowdel.cat_jam$getHead();

        if (meowrender.cat_jam$getNodAnimationProgress() >= 0) {
            final int nodTick = (int) Math.floor(meowrender.cat_jam$getNodAnimationProgress());
            final float tickDelta = meowrender.cat_jam$getNodAnimationProgress() - nodTick;

            float pivotTarget = meowdel.cat_jam$getInitialHeadPivotY() + JAM_PIVOTS[nodTick];
            float pitchTarget = head.pitch + JAM_ANGLES[nodTick];
            if (nodTick > 0) {
                head.pivotY = meowdel.cat_jam$getInitialHeadPivotY() + JAM_PIVOTS[nodTick - 1]; // recentres pivot to last anim position
                head.pitch += JAM_ANGLES[nodTick - 1];
            }
            head.pivotY = MathHelper.lerp(tickDelta, head.pivotY, pivotTarget);
            head.pitch = MathHelper.lerpAngleRadians(tickDelta, head.pitch, pitchTarget);
        }

        else if (meowrender.cat_jam$getSlightNodAnimationProgress() >= 0) {
            final int slightNodTick = (int) Math.floor(meowrender.cat_jam$getSlightNodAnimationProgress());
            final float tickDelta = meowrender.cat_jam$getSlightNodAnimationProgress() - slightNodTick;

            float target = head.pitch + SLIGHT_NOD_ANGLES[slightNodTick];
            if (slightNodTick > 0) head.pitch += SLIGHT_NOD_ANGLES[slightNodTick - 1];
            head.pitch = MathHelper.lerpAngleRadians(tickDelta, head.pitch, target);
        }

    }

}
