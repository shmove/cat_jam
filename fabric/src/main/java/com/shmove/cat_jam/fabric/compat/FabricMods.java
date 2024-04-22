package com.shmove.cat_jam.fabric.compat;

import com.shmove.cat_jam.compat.Mods;
import com.shmove.cat_jam.compat.supplementaries.Supplementaries;
import com.shmove.cat_jam.fabric.compat.audioplayer.AudioPlayer;
import net.fabricmc.loader.api.FabricLoader;

public enum FabricMods implements Mods {

    AUDIO_PLAYER(AudioPlayer.MOD_ID, AudioPlayer::initialiseDiscs, AudioPlayer::initialiseCompatibility),
    SUPPLEMENTARIES(Supplementaries.MOD_ID, Supplementaries::initialiseDiscs);

    private final String MOD_ID;
    private final Runnable INITIALISE_DISCS;
    private final Runnable INITIALISE_COMPATIBILITY;

    FabricMods(String modID, Runnable initialiseDiscs) {
        this(modID, initialiseDiscs, () -> {});
    }

    FabricMods(String modID, Runnable initialiseDiscs, Runnable initialiseCompatibility) {
        this.MOD_ID = modID;
        this.INITIALISE_DISCS = initialiseDiscs;
        this.INITIALISE_COMPATIBILITY = initialiseCompatibility;
    }

    @Override
    public boolean isInstalled() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID);
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
