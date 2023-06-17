package com.shmove.cat_jam.fabric;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.helpers.discs.Disc;
import com.shmove.cat_jam.helpers.discs.DiscSegment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

import static com.shmove.cat_jam.cat_jam.discManager;

public class cat_jam_fabric implements ModInitializer {

    @Override
    public void onInitialize() {
        cat_jam.init();
        initialiseModdedDiscs();
    }

    private void initialiseModdedDiscs() {

        // Mod List

        /* final boolean example_mod = FabricLoader.getInstance().isModLoaded("example_mod"); */

        // Modded Discs

        /* if (example_mod) discManager.addDisc(new Disc("example_mod:example_disc", 0, 0)); */
    }

}
