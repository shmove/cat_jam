package com.shmove.cat_jam.mixin;

import com.shmove.cat_jam.access.JammingEntityRenderState;
import net.minecraft.client.render.entity.state.CatEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CatEntityRenderState.class)
public abstract class CatEntityRenderStateMixin implements JammingEntityRenderState {

    @Unique protected boolean cat_jam$isInValidStateToJam;
    @Override public boolean cat_jam$isInValidStateToJam() { return cat_jam$isInValidStateToJam; }
    @Override public void cat_jam$setInValidStateToJam(boolean valid) { cat_jam$isInValidStateToJam = valid; }

    @Unique protected float cat_jam$nodAnimationProgress;
    @Override public float cat_jam$getNodAnimationProgress() { return cat_jam$nodAnimationProgress; }
    @Override public void cat_jam$setNodAnimationProgress(float progress) { cat_jam$nodAnimationProgress = progress; }

    @Unique protected float cat_jam$slightNodAnimationProgress;
    @Override public float cat_jam$getSlightNodAnimationProgress() { return cat_jam$slightNodAnimationProgress; }
    @Override public void cat_jam$setSlightNodAnimationProgress(float progress) { cat_jam$slightNodAnimationProgress = progress; }

}
