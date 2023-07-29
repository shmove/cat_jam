package com.shmove.cat_jam.helpers;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public interface JammingEntity {

    void resetJammingInfo();

    void updateMusicSource(BlockPos sourcePos);

    void updateMusicSource(Entity sourceEntity);

    int getNodTick();

    int getSlightNodTick();

}
