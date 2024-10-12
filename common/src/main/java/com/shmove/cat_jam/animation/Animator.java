package com.shmove.cat_jam.animation;

import com.shmove.cat_jam.access.JammingEntity;
import com.shmove.cat_jam.behaviour.JammingState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelUtil;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Unique;

public final class Animator {

    @Unique
    private static final float[] JAM_PIVOTS = { 0.10f, 0.50f, 0.60f, 0.60f, 0.60f, 0.10f, 0.00f, 0.00f };
    @Unique
    private static final float[] JAM_ANGLES = { 0.05f, 0.15f, 0.18f, 0.20f, 0.15f, 0.05f, -0.02f, 0.00f };

    @Unique
    private static final float[] SLIGHT_NOD_ANGLES = { 0.05f, 0.08f, 0.10f, 0.05f, -0.02f, 0.00f };

    public static void animateHead(JammingEntity meow, ModelPart head, float tickDelta) {
        if (!meow.cat_jam$isInValidStateToJam()) return;

        final JammingState state = meow.cat_jam$getJammingState();

        if (state.getNodTick() >= 0) {
            float pivotTarget = head.pivotY + JAM_PIVOTS[state.getNodTick()];
            float pitchTarget = head.pitch + JAM_ANGLES[state.getNodTick()];
            if (state.getNodTick() > 0) {
                head.pivotY += JAM_PIVOTS[state.getNodTick() - 1]; // recentres pivot to last anim position
                head.pitch += JAM_ANGLES[state.getNodTick() - 1];
            }
            head.pivotY = MathHelper.lerp(tickDelta, head.pivotY, pivotTarget);
            head.pitch = ModelUtil.interpolateAngle(head.pitch, pitchTarget, tickDelta);
        }

        else if (state.getSlightNodTick() >= 0) {
            float target = head.pitch + SLIGHT_NOD_ANGLES[state.getSlightNodTick()];
            if (state.getSlightNodTick() > 0) head.pitch += SLIGHT_NOD_ANGLES[state.getSlightNodTick() - 1];
            head.pitch = ModelUtil.interpolateAngle(head.pitch, target, tickDelta);
        }

    }

}
