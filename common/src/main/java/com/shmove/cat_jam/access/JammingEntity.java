package com.shmove.cat_jam.access;

import net.minecraft.util.math.BlockPos;

public interface JammingEntity {

    void cat_jam$resetJammingInfo();

    void cat_jam$updateMusicSource(BlockPos sourcePos);

    void cat_jam$updateMusicSource(Integer sourceEntityID);

    int cat_jam$getNodTick();

    int cat_jam$getSlightNodTick();

}
