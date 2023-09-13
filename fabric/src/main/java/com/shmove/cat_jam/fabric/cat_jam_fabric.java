package com.shmove.cat_jam.fabric;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.fabric.compat.FabricMods;
import com.shmove.cat_jam.fabric.compat.audioplayer.AudioPlayer;
import com.shmove.cat_jam.helpers.discs.Disc;
import com.shmove.cat_jam.helpers.discs.DiscManager;
import com.shmove.cat_jam.helpers.discs.DiscSegment;
import com.shmove.cat_jam.helpers.discs.NodPattern;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import java.util.List;

import static com.shmove.cat_jam.cat_jam.discManager;

public class cat_jam_fabric implements ModInitializer {

    @Override
    public void onInitialize() {
        cat_jam.init();

        if (FabricMods.AUDIO_PLAYER.isInstalled()) ClientTickEvents.END_WORLD_TICK.register(world -> AudioPlayer.tick());

        ClientTickEvents.END_WORLD_TICK.register(cat_jam::tickPlayingDiscs);
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> cat_jam.clearPlayingDiscs());
        initialiseModdedDiscs();
    }

    private void initialiseModdedDiscs() {

        // Modded Discs

        if (FabricMods.AUDIO_PLAYER.isInstalled()) discManager.addDisc(new Disc(AudioPlayer.CUSTOM_DISC_ID, DiscManager.DEFAULT_BPM, DiscManager.DEFAULT_OFFSET));

    }

}
