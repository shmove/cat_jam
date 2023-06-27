package com.shmove.cat_jam.fabric;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.fabric.compat.audioplayer.AudioPlayer;
import com.shmove.cat_jam.helpers.discs.Disc;
import com.shmove.cat_jam.helpers.discs.DiscManager;
import com.shmove.cat_jam.helpers.discs.DiscSegment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

import static com.shmove.cat_jam.cat_jam.discManager;

public class cat_jam_fabric implements ModInitializer {

    @Override
    public void onInitialize() {
        cat_jam.init();

        final boolean AUDIO_PLAYER = FabricLoader.getInstance().isModLoaded("audioplayer");
        if (AUDIO_PLAYER) ClientTickEvents.END_WORLD_TICK.register(AudioPlayer::tick);

        initialiseModdedDiscs();
    }

    private void initialiseModdedDiscs() {

        // Mod List

        final boolean AUDIO_PLAYER = FabricLoader.getInstance().isModLoaded("audioplayer");

        // Modded Discs

        if (AUDIO_PLAYER) discManager.addDisc(new Disc(AudioPlayer.CUSTOM_DISC_ID, DiscManager.DEFAULT_BPM, DiscManager.DEFAULT_OFFSET));

    }

}
