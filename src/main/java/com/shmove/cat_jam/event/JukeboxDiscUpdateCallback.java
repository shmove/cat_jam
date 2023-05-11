package com.shmove.cat_jam.event;

import com.shmove.cat_jam.helpers.discs.Disc;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface JukeboxDiscUpdateCallback {

    Event<JukeboxDiscUpdateCallback> EVENT = EventFactory.createArrayBacked(JukeboxDiscUpdateCallback.class, (listeners) -> (world, pos, disc) -> {
        for (JukeboxDiscUpdateCallback listener : listeners) {
            ActionResult res = listener.update(world, pos, disc);
            if (res != ActionResult.PASS) {
                return res;
            }
        }

        return ActionResult.PASS;
    });

    ActionResult update(World world, BlockPos pos, @Nullable Disc disc);

}
