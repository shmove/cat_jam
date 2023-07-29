package com.shmove.cat_jam.fabric;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.fabric.compat.FabricMods;
import com.shmove.cat_jam.helpers.discs.Disc;
import com.shmove.cat_jam.helpers.discs.DiscManager;
import com.shmove.cat_jam.helpers.discs.DiscSegment;
import com.shmove.cat_jam.helpers.discs.NodPattern;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.List;

import static com.shmove.cat_jam.cat_jam.discManager;

public class cat_jam_fabric implements ModInitializer {

    @Override
    public void onInitialize() {
        cat_jam.init();

        ClientTickEvents.END_WORLD_TICK.register(cat_jam::tickPlayingDiscs);
        initialiseModdedDiscs();
    }

    private void initialiseModdedDiscs() {

        // Modded Discs

        /* if (example_mod) discManager.addDisc(new Disc("example_mod:example_disc", 0, 0)); */

    }

}
