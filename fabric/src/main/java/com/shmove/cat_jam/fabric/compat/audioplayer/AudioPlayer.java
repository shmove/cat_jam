package com.shmove.cat_jam.fabric.compat.audioplayer;

import com.shmove.cat_jam.cat_jam;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.OpenALSoundEvent;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;

public class AudioPlayer implements VoicechatPlugin {

    public static final String DISC_SOUND_CATEGORY = "music_discs";
    public static final String CUSTOM_DISC_ID = "audioplayer:custom_disc";
    private static final int MAX_LIVELINESS = 5; // ticks to wait before considering a disc stopped
    private static final HashMap<BlockPos, Integer> playbackLiveliness = new HashMap<>();

    @Override
    public String getPluginId() { return cat_jam.MOD_ID; }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(OpenALSoundEvent.Pre.class, this::onSound);
    }

    private void onSound(OpenALSoundEvent.Pre sound) {
        if (sound.getCategory() == null || sound.getPosition() == null) return;

        if (sound.getCategory().equals(DISC_SOUND_CATEGORY))
            pingPos(new BlockPos((int)Math.floor(sound.getPosition().getX()), (int)Math.floor(sound.getPosition().getY()), (int)Math.floor(sound.getPosition().getZ())));
    }

    public static void pingPos(BlockPos pos) {
        if (!playbackLiveliness.containsKey(pos))
            cat_jam.addMusicSource(pos, cat_jam.discManager.getDisc(CUSTOM_DISC_ID));
        playbackLiveliness.put(pos, MAX_LIVELINESS); // refresh liveliness
    }

    public static void tick() {
        for (BlockPos pos : new ArrayList<>(playbackLiveliness.keySet())) {
            if (playbackLiveliness.get(pos) > 0) {
                playbackLiveliness.put(pos, playbackLiveliness.get(pos) - 1); // decrement liveliness
            } else {
                playbackLiveliness.remove(pos); // remove if no longer playing
                cat_jam.removeMusicSource(pos);
            }
        }
    }

}
