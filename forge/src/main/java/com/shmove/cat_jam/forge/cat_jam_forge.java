package com.shmove.cat_jam.forge;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.helpers.discs.Disc;
import com.shmove.cat_jam.helpers.discs.DiscSegment;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.shmove.cat_jam.cat_jam.discManager;

@Mod(cat_jam.MOD_ID)
public class cat_jam_forge {

    public cat_jam_forge() {
        cat_jam.init();
        initialiseModdedDiscs();
    }

    private void initialiseModdedDiscs() {

        // Mod List

        /* final boolean example_mod = ModList.get().isLoaded("example_mod"); */

        // Modded Discs

        /* if (example_mod) discManager.addDisc(new Disc("example_mod:example_disc", 0, 0)); */

    }

}
