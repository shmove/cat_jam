package com.shmove.cat_jam.fabric.compat.audioplayer;

import com.shmove.cat_jam.discs.Disc;
import com.shmove.cat_jam.discs.DiscManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import static com.shmove.cat_jam.cat_jam.discManager;

public class AudioPlayer {

    public static final String MOD_ID = "audioplayer";


    public static void initialiseCompatibility() {
        ClientTickEvents.END_WORLD_TICK.register(world -> AudioPlayerPlugin.tick());
    }

    public static void initialiseDiscs() {
        discManager.addDisc(new Disc(AudioPlayerPlugin.CUSTOM_DISC_ID, DiscManager.DEFAULT_BPM, DiscManager.DEFAULT_OFFSET));
    }

}
