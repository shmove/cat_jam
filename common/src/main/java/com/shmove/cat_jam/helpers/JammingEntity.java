package com.shmove.cat_jam.helpers;

import com.shmove.cat_jam.helpers.discs.Disc;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface JammingEntity {

    void setJammingInfo (BlockPos jukeboxPosition, @Nullable Disc disc);

    int getNodTick();

    int getSlightNodTick();

}
