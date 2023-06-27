package com.shmove.cat_jam.fabric.compat.audioplayer;

import com.shmove.cat_jam.cat_jam;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.HashMap;

public class AudioPlayer {

    public static final String DISC_SOUND_CATEGORY = "music_discs";
    public static final String CUSTOM_DISC_ID = "audioplayer:custom_disc";
    private static final int MAX_LIVELINESS = 5; // ticks to wait before considering a disc stopped
    private static final HashMap<Vec3d, Integer> playbackLiveliness = new HashMap<>();

    public static void pingPos(Vec3d pos) {
        if (!playbackLiveliness.containsKey(pos))
            cat_jam.jukeboxDiscUpdateEvent(MinecraftClient.getInstance().world, getBlockPos(pos), cat_jam.discManager.getDisc(CUSTOM_DISC_ID));
        playbackLiveliness.put(pos, MAX_LIVELINESS); // refresh liveliness
    }

    public static void tick(ClientWorld world) {
        for (Vec3d pos : new ArrayList<>(playbackLiveliness.keySet())) {
            if (playbackLiveliness.get(pos) > 0) {
                playbackLiveliness.put(pos, playbackLiveliness.get(pos) - 1); // decrement liveliness
            } else {
                playbackLiveliness.remove(pos); // remove if no longer playing
                cat_jam.jukeboxDiscUpdateEvent(world, getBlockPos(pos), null);
            }
        }
    }

    private static BlockPos getBlockPos(Vec3d pos) {
        return new BlockPos(new Vec3i((int)Math.floor(pos.x), (int)Math.floor(pos.y), (int)Math.floor(pos.z)));
    }

}
