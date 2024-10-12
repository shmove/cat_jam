package com.shmove.cat_jam.animation;

import com.shmove.cat_jam.access.JammingEntity;
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
        if (!meow.cat_jam$isInValidPoseToJam()) return;

        if (meow.cat_jam$getNodTick() >= 0) {
            float pivotTarget = head.pivotY + JAM_PIVOTS[meow.cat_jam$getNodTick()];
            float pitchTarget = head.pitch + JAM_ANGLES[meow.cat_jam$getNodTick()];
            if (meow.cat_jam$getNodTick() > 0) {
                head.pivotY += JAM_PIVOTS[meow.cat_jam$getNodTick() - 1]; // recentres pivot to last anim position
                head.pitch += JAM_ANGLES[meow.cat_jam$getNodTick() - 1];
            }
            head.pivotY = MathHelper.lerp(tickDelta, head.pivotY, pivotTarget);
            head.pitch = ModelUtil.interpolateAngle(head.pitch, pitchTarget, tickDelta);
        }

        else if (meow.cat_jam$getSlightNodTick() >= 0) {
            float target = head.pitch + SLIGHT_NOD_ANGLES[meow.cat_jam$getSlightNodTick()];
            if (meow.cat_jam$getSlightNodTick() > 0) head.pitch += SLIGHT_NOD_ANGLES[meow.cat_jam$getSlightNodTick() - 1];
            head.pitch = ModelUtil.interpolateAngle(head.pitch, target, tickDelta);
        }

    }

}
