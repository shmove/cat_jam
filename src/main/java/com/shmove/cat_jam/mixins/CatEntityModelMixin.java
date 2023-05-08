package com.shmove.cat_jam.mixins;

import com.shmove.cat_jam.CatMixinAccess;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelUtil;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.render.entity.model.OcelotEntityModel;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatEntityModel.class)
public class CatEntityModelMixin<T extends CatEntity> extends OcelotEntityModel<T> {

    @Unique
    private static final float[] CAT_JAM_PIVOTS = { 0.10f, 0.50f, 0.60f, 0.60f, 0.60f, 0.10f, 0.00f, 0.00f };
    @Unique
    private static final float[] CAT_JAM_ANGLES = { 0.05f, 0.15f, 0.18f, 0.20f, 0.15f, 0.05f, -0.02f, 0.00f };

    @Unique
    private static final float[] SLIGHT_NOD_ANGLES = { 0.05f, 0.08f, 0.10f, 0.05f, -0.02f, 0.00f };

    public CatEntityModelMixin(ModelPart modelPart) { super(modelPart); }

    @Inject(method = "animateModel(Lnet/minecraft/entity/passive/CatEntity;FFF)V", at = @At("TAIL"))
    public void animateModel(T meow, float f, float g, float tickDelta, CallbackInfo ci) {
        CatMixinAccess meowmix = (CatMixinAccess) meow;

        if (!meow.isInSittingPose() && !meow.isInSleepingPose()) return;

        if (meowmix.getNodTick() >= 0) {
            float target = this.head.pivotY + CAT_JAM_PIVOTS[meowmix.getNodTick()];
            if (meowmix.getNodTick() > 0) this.head.pivotY += CAT_JAM_PIVOTS[meowmix.getNodTick() - 1]; // recentres pivot to last anim position
            this.head.pivotY = MathHelper.lerp(tickDelta, this.head.pivotY, target);
        }

    }

    @Inject(method = "setAngles(Lnet/minecraft/entity/passive/CatEntity;FFFFF)V", at = @At("TAIL"))
    public void setAngles(T meow, float f, float g, float animationProgress, float i, float j, CallbackInfo ci) {
        CatMixinAccess meowmix = (CatMixinAccess) meow;
        float tickDelta = animationProgress - meow.age;

        if (!meow.isInSittingPose() && !meow.isInSleepingPose()) return;

        if (meowmix.getNodTick() >= 0) {
            float target = this.head.pitch + CAT_JAM_ANGLES[meowmix.getNodTick()];
            if (meowmix.getNodTick() > 0) this.head.pitch += CAT_JAM_ANGLES[meowmix.getNodTick() - 1];
            this.head.pitch = ModelUtil.interpolateAngle(this.head.pitch, target, tickDelta);
        }

        else if (meowmix.getSlightNodTick() >= 0) {
            float target = this.head.pitch + SLIGHT_NOD_ANGLES[meowmix.getSlightNodTick()];
            if (meowmix.getSlightNodTick() > 0) this.head.pitch += SLIGHT_NOD_ANGLES[meowmix.getSlightNodTick() - 1];
            this.head.pitch = ModelUtil.interpolateAngle(this.head.pitch, target, tickDelta);
        }

    }

}
