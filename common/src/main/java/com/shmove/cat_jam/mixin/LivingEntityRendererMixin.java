package com.shmove.cat_jam.mixin;

import com.shmove.cat_jam.access.JammingEntity;
import com.shmove.cat_jam.access.JammingEntityModel;
import com.shmove.cat_jam.access.JammingEntityRenderState;
import com.shmove.cat_jam.animation.Animator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {

    @Shadow public abstract M getModel();

    @Inject(method = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
    private void updateRenderState(T livingEntity, S livingEntityRenderState, float tickDelta, CallbackInfo ci) {
        if (!(livingEntity instanceof JammingEntity meowmix)) return;
        if (!(livingEntityRenderState instanceof JammingEntityRenderState meowrender)) throw new IllegalArgumentException("Cannot update render state of a JammingEntity without an accessible JammingEntityRenderState!");
        meowrender.cat_jam$setInValidStateToJam(meowmix.cat_jam$isInValidStateToJam());
        meowrender.cat_jam$setNodAnimationProgress(meowmix.cat_jam$getJammingState().getNodTick() + tickDelta);
        meowrender.cat_jam$setSlightNodAnimationProgress(meowmix.cat_jam$getJammingState().getSlightNodTick() + tickDelta);
    }

    @Inject(
        method = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V",
            shift = At.Shift.AFTER
        )
    )
    private void render(S livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (!(livingEntityRenderState instanceof JammingEntityRenderState meowrender)) return;
        if (!(this.getModel() instanceof JammingEntityModel meowdel)) throw new IllegalArgumentException("Cannot animate JammingEntity without an accessible JammingEntityModel!");
        Animator.animateHead(meowrender, meowdel);
    }

}
