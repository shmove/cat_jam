package com.shmove.cat_jam.mixin;

import com.shmove.cat_jam.behaviour.JammingState;
import com.shmove.cat_jam.access.JammingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatEntity.class)
public abstract class CatEntityMixin implements JammingEntity {

    @Unique
    private JammingState jammingState;

    @Inject(method = "Lnet/minecraft/entity/passive/CatEntity;<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At("TAIL"))
    private void init(EntityType<? extends CatEntity> entityType, World world, CallbackInfo ci) {
        this.jammingState = new JammingState((CatEntity) (Object) this);
    }

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        jammingState.tick();
    }

    @Override
    public JammingState cat_jam$getJammingState() {
        return jammingState;
    }

    @Override
    public boolean cat_jam$isInValidStateToJam() {
        CatEntity meow = (CatEntity) (Object) this;
        final boolean VALID_POSE = meow.isInSittingPose() || meow.isInSleepingPose();
        final boolean VALID_ATTRIBUTES = meow.isTamed();
        return VALID_POSE && VALID_ATTRIBUTES;
    }

}
