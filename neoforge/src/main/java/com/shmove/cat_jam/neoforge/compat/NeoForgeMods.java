package com.shmove.cat_jam.neoforge.compat;

import com.shmove.cat_jam.compat.Mods;
import net.neoforged.fml.ModList;

public enum NeoForgeMods implements Mods {

    ALEXS_MOBS("alexsmobs"),
    QUARK("quark");

    public final String MOD_ID;

    NeoForgeMods(String modID) {
        this.MOD_ID = modID;
    }

    @Override
    public boolean isInstalled() {
        return ModList.get().isLoaded(MOD_ID);
    }

}
