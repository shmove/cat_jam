package com.shmove.cat_jam.neoforge.compat;

import com.shmove.cat_jam.compat.Mods;
import com.shmove.cat_jam.compat.supplementaries.Supplementaries;
import com.shmove.cat_jam.neoforge.compat.alexsmobs.AlexsMobs;
import com.shmove.cat_jam.neoforge.compat.quark.Quark;
import net.neoforged.fml.ModList;

public enum NeoForgeMods implements Mods {

    ALEXS_MOBS(AlexsMobs.MOD_ID, AlexsMobs::initialiseDiscs),
    QUARK(Quark.MOD_ID, Quark::initialiseDiscs),
    SUPPLEMENTARIES(Supplementaries.MOD_ID, Supplementaries::initialiseDiscs);

    private final String MOD_ID;
    private final Runnable INITIALISE_DISCS;
    private final Runnable INITIALISE_COMPATIBILITY;

    NeoForgeMods(String modID, Runnable initialiseDiscs) {
        this(modID, initialiseDiscs, () -> {});
    }

    NeoForgeMods(String modID, Runnable initialiseDiscs, Runnable initialiseCompatibility) {
        this.MOD_ID = modID;
        this.INITIALISE_DISCS = initialiseDiscs;
        this.INITIALISE_COMPATIBILITY = initialiseCompatibility;
    }

    @Override
    public boolean isInstalled() {
        return ModList.get().isLoaded(MOD_ID);
    }

    @Override
    public void initialiseCompatibility() {
        INITIALISE_COMPATIBILITY.run();
    }

    @Override
    public void initialiseDiscs() {
        INITIALISE_DISCS.run();
    }

}
