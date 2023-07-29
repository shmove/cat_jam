package com.shmove.cat_jam.helpers;

import net.minecraft.util.math.BlockPos;

public interface JammingEntity {

    void resetJammingInfo();

    void updateMusicSource(BlockPos sourcePos);

    int getNodTick();

    int getSlightNodTick();

}
