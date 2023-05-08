package com.shmove.cat_jam;

import com.shmove.cat_jam.records.Disc;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface CatMixinAccess {

    void setJammingInfo (BlockPos jukeboxPosition, @Nullable Disc disc);

    int getNodTick();

    int getSlightNodTick();

}
