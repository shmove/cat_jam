package com.shmove.cat_jam.access;

import com.shmove.cat_jam.behaviour.JammingState;

public interface JammingEntity {

    JammingState cat_jam$getJammingState();

    boolean cat_jam$isInValidStateToJam();

}
