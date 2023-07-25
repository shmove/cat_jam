package com.shmove.cat_jam.fabric.compat;

import com.shmove.cat_jam.compat.Mods;
import net.fabricmc.loader.api.FabricLoader;

public enum FabricMods implements Mods {

    AUDIO_PLAYER("audioplayer");

    public final String MOD_ID;

    FabricMods(String modID) {
        this.MOD_ID = modID;
    }

    @Override
    public boolean isInstalled() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID);
    }

}
