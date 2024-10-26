package com.shmove.cat_jam.access;

public interface JammingEntityRenderState {

    boolean cat_jam$isInValidStateToJam();
    void cat_jam$setInValidStateToJam(boolean valid);

    float cat_jam$getNodAnimationProgress();
    void cat_jam$setNodAnimationProgress(float progress);

    float cat_jam$getSlightNodAnimationProgress();
    void cat_jam$setSlightNodAnimationProgress(float progress);

}
