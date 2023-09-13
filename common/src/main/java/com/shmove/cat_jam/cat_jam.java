package com.shmove.cat_jam;

import com.shmove.cat_jam.helpers.discs.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cat_jam {

    public static final String MOD_ID = "cat_jam";

    public static final DiscManager discManager = new DiscManager();
    private static final HashMap<BlockPos, DiscPlayback> musicSourceBlocks = new HashMap<>();
    private static final HashMap<Integer, DiscPlayback> musicSourceEntities = new HashMap<>();
    public static final double JAM_RADIUS = 3.46D;

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        initialiseDiscs();
    }

    public static void tickPlayingDiscs(World world) {

        for (Map.Entry<BlockPos, DiscPlayback> playingDisc : musicSourceBlocks.entrySet()) {
            if (isBlockEntityLoadedAtPos(world, playingDisc.getKey())) {
                playingDisc.getValue().tick();
            } else {
                removeMusicSource(playingDisc.getKey());
            }
        }

        for (Map.Entry<Integer, DiscPlayback> playingDisc : musicSourceEntities.entrySet()) {
            if (world.getEntityById(playingDisc.getKey()) != null) {
                playingDisc.getValue().tick();
            } else {
                removeMusicSource(playingDisc.getKey());
            }
        }

    }

    public static void clearPlayingDiscs() {
        musicSourceBlocks.clear();
        musicSourceEntities.clear();
    }

    public static void addMusicSource(BlockPos sourcePos, Disc disc) {
        musicSourceBlocks.put(sourcePos, new DiscPlayback(disc));
    }

    public static void addMusicSource(Integer sourceEntityID, Disc disc) {
        musicSourceEntities.put(sourceEntityID, new DiscPlayback(disc));
    }

    public static void removeMusicSource(BlockPos sourcePos) {
        musicSourceBlocks.remove(sourcePos);
    }

    public static void removeMusicSource(Integer sourceEntityID) {
        musicSourceEntities.remove(sourceEntityID);
    }

    @Nullable
    public static BlockPos getClosestListenableSourcePos(Vec3d catPos) {
        BlockPos closestPos = null;
        double closestDistance = Double.MAX_VALUE;
        for (BlockPos sourcePos : musicSourceBlocks.keySet()) {
            double distance = sourcePos.getSquaredDistance(catPos);
            if (distance < closestDistance && distance < MathHelper.square(JAM_RADIUS)) {
                closestPos = sourcePos;
                closestDistance = distance;
            }
        }
        return closestPos;
    }

    @Nullable
    public static Entity getClosestListenableSourceEntity(Vec3d catPos) {
        World world = MinecraftClient.getInstance().world;
        if (world == null) return null;

        Entity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;
        for (Integer sourceEntityID : musicSourceEntities.keySet()) {

            Entity sourceEntity = world.getEntityById(sourceEntityID);
            if (sourceEntity == null) continue;

            double distance = sourceEntity.getPos().squaredDistanceTo(catPos);
            if (distance < closestDistance && distance < MathHelper.square(JAM_RADIUS)) {
                closestEntity = sourceEntity;
                closestDistance = distance;
            }

        }

        return closestEntity;
    }

    private static boolean isBlockEntityLoadedAtPos(World world, BlockPos pos) {
        if (world == null) return false;
        return world.getBlockEntity(pos) != null; // not sure if this is the best way to do this, but World.isPosLoaded() works unexpectedly
    }

    public static boolean isSourcePlayingAtPos(BlockPos sourcePos) {
        return musicSourceBlocks.containsKey(sourcePos);
    }

    public static boolean isSourcePlayingFromEntity(Integer sourceEntityID) {
        return musicSourceEntities.containsKey(sourceEntityID);
    }

    public static DiscPlayback getDiscPlaybackAtPos(BlockPos sourcePos) {
        return musicSourceBlocks.get(sourcePos);
    }

    public static DiscPlayback getDiscPlaybackFromEntity(Integer sourceEntityID) {
        return musicSourceEntities.get(sourceEntityID);
    }

    /**
     * Initialise all vanilla discs with custom routines
     */
    private static void initialiseDiscs() {

        discManager.addDisc(new Disc("minecraft:music_disc_13", 0, 0));

        final java.util.List<DiscSegment> DISC_CAT_SEGMENTS = java.util.List.of(
                new DiscSegment(112, 56, NodPattern.SLIGHT),
                new DiscSegment(112, 32, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                new DiscSegment(112, 16, NodPattern.NONE),
                new DiscSegment(112, 64),
                new DiscSegment(112, 32, NodPattern.SLIGHT),
                new DiscSegment(112, 32, NodPattern.NONE),
                new DiscSegment(112, 16, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                new DiscSegment(112, 64),
                new DiscSegment(112, 16, NodPattern.SLIGHT),
                new DiscSegment(112, 13, new NodPattern("x_")),
                new DiscSegment(112, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_cat", DISC_CAT_SEGMENTS, 0));

        final List<DiscSegment> DISC_BLOCKS_SEGMENTS = java.util.List.of(
                new DiscSegment(110, 47, NodPattern.SLIGHT),
                new DiscSegment(110, 1, NodPattern.NONE),
                new DiscSegment(110, 95, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(110, 1, NodPattern.NONE),
                new DiscSegment(110, 47, NodPattern.SLIGHT),
                new DiscSegment(55, 12, NodPattern.SLIGHT),
                new DiscSegment(110, 41, NodPattern.NONE),
                new DiscSegment(110, 63, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                new DiscSegment(110, 27, NodPattern.SLIGHT),
                new DiscSegment(110, 101, NodPattern.NONE),
                new DiscSegment(880, 7, NodPattern.NONE), // desync by -a half step
                new DiscSegment(110, 1, NodPattern.SLIGHT),
                new DiscSegment(110, 4, NodPattern.NONE),
                new DiscSegment(55, 2, NodPattern.SLIGHT),
                new DiscSegment(110, 7, NodPattern.NONE),
                new DiscSegment(110, 1, NodPattern.SLIGHT),
                new DiscSegment(110, 6, NodPattern.NONE),
                new DiscSegment(55, 4, NodPattern.SLIGHT),
                new DiscSegment(110, 66, NodPattern.SLIGHT),
                new DiscSegment(55, 34, NodPattern.SLIGHT),
                new DiscSegment(110, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_blocks", DISC_BLOCKS_SEGMENTS, 6.56));

        final List<DiscSegment> DISC_CHIRP_SEGMENTS = java.util.List.of(
                new DiscSegment(109.85, 16, new NodPattern("X_x_")),
                new DiscSegment(109.85, 65),
                new DiscSegment(109.85, 16, new NodPattern("__xX")),
                new DiscSegment(109.85, 16),
                new DiscSegment( (109.85 / 2.0) , 6, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment( (109.85 / 2.0) , 2, NodPattern.SLIGHT),
                new DiscSegment( (109.85 / 4.0) , 16, NodPattern.SLIGHT),
                new DiscSegment( (109.85 / 2.0) , 8, NodPattern.SLIGHT),
                new DiscSegment(109.85, 64, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                new DiscSegment( (109.85 / 4.0) , 1, NodPattern.SLIGHT),
                new DiscSegment(109.85, 4, new NodPattern("__xX")),
                new DiscSegment(109.85, 23),
                new DiscSegment(109.85, 24, new NodPattern("x_______")),
                new DiscSegment(109.85, 6, new NodPattern("x_")),
                new DiscSegment(109.85, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_chirp", DISC_CHIRP_SEGMENTS, 0));

        final List<DiscSegment> DISC_FAR_SEGMENTS = java.util.List.of(
                new DiscSegment( (65.0 / 4.0) , 11, NodPattern.SLIGHT),
                new DiscSegment( (65.0 / 2.0) , 12, NodPattern.SLIGHT),
                new DiscSegment(65, 16, NodPattern.SLIGHT),
                new DiscSegment( (65.0 / 2.0) , 3, NodPattern.SLIGHT),
                new DiscSegment( (65.0 / 2.0) , 9, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                new DiscSegment(65, 12, new NodPattern("_XXX")),
                new DiscSegment(65, 3, NodPattern.NONE),
                new DiscSegment(260, 3, NodPattern.NONE),
                new DiscSegment(260, 63, new NodPattern("X_______x__x____")),
                new DiscSegment(130, 65, NodPattern.DOWNBEAT4),
                new DiscSegment(65, 12, new NodPattern("___X")),
                new DiscSegment(65, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_far", DISC_FAR_SEGMENTS, 0));

        final List<DiscSegment> DISC_MALL_SEGMENTS = java.util.List.of(
                new DiscSegment(115, 28, new NodPattern("x_")),
                new DiscSegment(115, 4, NodPattern.SLIGHT),
                new DiscSegment(115, 30),
                new DiscSegment(115, 2, NodPattern.NONE),
                new DiscSegment(115, 64),
                new DiscSegment(115, 56, NodPattern.NONE),
                new DiscSegment(115, 32, NodPattern.SLIGHT),
                new DiscSegment(115, 32, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                new DiscSegment(115, 64),
                new DiscSegment(115 * 2, 12, new NodPattern("_x___x__x__x")), // odd unique pattern to fit music
                new DiscSegment(115, 10, NodPattern.NONE),
                new DiscSegment(115, 1, NodPattern.SLIGHT),
                new DiscSegment(115, 7, NodPattern.NONE),
                new DiscSegment(115, 1, NodPattern.SLIGHT),
                new DiscSegment(115, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_mall", DISC_MALL_SEGMENTS, 16.69));

        final List<DiscSegment> DISC_MELLOHI_SEGMENTS = java.util.List.of(
                new DiscSegment(90.75, 24, new NodPattern("x__")),
                new DiscSegment(90.75, 21, NodPattern.DOWNBEAT3),
                new DiscSegment(90.75, 3, new NodPattern("X__")),
                new DiscSegment(90.75, 45, NodPattern.DOWNBEAT3),
                new DiscSegment(90.75, 3, new NodPattern("X__")),
                new DiscSegment(90.75, 24, NodPattern.DOWNBEAT3),
                new DiscSegment(90.75, 12, new NodPattern("X__")),
                new DiscSegment(90.75, 12, new NodPattern("x__")),
                new DiscSegment(90.75, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_mellohi", DISC_MELLOHI_SEGMENTS, 0));

        final List<DiscSegment> DISC_STAL_SEGMENTS = java.util.List.of(
                new DiscSegment(105, 16, new NodPattern("xX_X")),
                new DiscSegment(105, 50, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(105, 1, NodPattern.NONE),
                new DiscSegment(105, 1, NodPattern.SLIGHT),
                new DiscSegment(105, 32, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(105, 4, new NodPattern("x_xx")),
                new DiscSegment(105, 12, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(105, 2, NodPattern.NONE),
                new DiscSegment(105, 2, NodPattern.SLIGHT),
                new DiscSegment(105, 12, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(105, 1, NodPattern.SLIGHT),
                new DiscSegment(105, 16, NodPattern.NONE),
                new DiscSegment(105 / 2.0, 3, NodPattern.SLIGHT),
                new DiscSegment(105, 1, NodPattern.NONE),
                new DiscSegment(105, 52, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(105, 4, new NodPattern("x_xx")),
                new DiscSegment(105, 33, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(105 / 2.0, 5, NodPattern.SLIGHT),
                new DiscSegment(105, 1),
                new DiscSegment(105, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_stal", DISC_STAL_SEGMENTS, 0));

        final List<DiscSegment> DISC_STRAD_SEGMENTS = java.util.List.of(
                new DiscSegment(94 / 2.0, 8, NodPattern.SLIGHT),
                new DiscSegment(94, 53, NodPattern.SLIGHT),
                new DiscSegment(94, 4, NodPattern.NONE),
                new DiscSegment(94, 64),
                new DiscSegment(94, 1, NodPattern.SLIGHT),
                new DiscSegment(94 / 2.0, 2, NodPattern.SLIGHT),
                new DiscSegment(94, 32, NodPattern.SLIGHT),
                new DiscSegment(94 / 2.0, 8, NodPattern.SLIGHT),
                new DiscSegment(94, 15, NodPattern.SLIGHT),
                new DiscSegment(94, 16, NodPattern.DOWNBEAT4),
                new DiscSegment(94, 16, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(94, 17),
                new DiscSegment(94, 6, NodPattern.NONE),
                new DiscSegment(94 / 2.0, 9, NodPattern.SLIGHT),
                new DiscSegment(94, 8, NodPattern.SLIGHT),
                new DiscSegment(94 * 2, 15, NodPattern.SLIGHT),
                new DiscSegment(94 * 2, 1),
                new DiscSegment(94, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_strad", DISC_STRAD_SEGMENTS, 0));

        final List<DiscSegment> DISC_WARD_SEGMENTS = java.util.List.of(
                new DiscSegment(107, 32, NodPattern.SLIGHT),
                new DiscSegment(107, 48, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(107, 64, NodPattern.SLIGHT),
                new DiscSegment(107, 36, NodPattern.NONE),
                new DiscSegment(107, 64),
                new DiscSegment(107, 16, NodPattern.SLIGHT),
                new DiscSegment(107, 10, NodPattern.NONE),
                new DiscSegment(107 * 2, 1, NodPattern.NONE), // desync by a half beat
                new DiscSegment(107 / 2.0, 5, NodPattern.SLIGHT),
                new DiscSegment(107, 7, NodPattern.SLIGHT),
                new DiscSegment(107, 16, NodPattern.DOWNBEAT4),
                new DiscSegment(107, 1, NodPattern.SLIGHT),
                new DiscSegment(107 * 2, 1, NodPattern.NONE), // correct sync
                new DiscSegment(107, 72, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                new DiscSegment(107, 8, NodPattern.SLIGHT),
                new DiscSegment(107 / 2.0, 7, NodPattern.SLIGHT),
                new DiscSegment(107, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_ward", DISC_WARD_SEGMENTS, 17.95));

        discManager.addDisc(new Disc("minecraft:music_disc_11", 0, 0));

        final List<DiscSegment> DISC_WAIT_SEGMENTS = java.util.List.of(
                new DiscSegment(114 / 2.0, 8, NodPattern.SLIGHT),
                new DiscSegment(114, 1, NodPattern.NONE),
                new DiscSegment(114, 48, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(114, 4, NodPattern.SLIGHT),
                new DiscSegment(114, 16, NodPattern.DOWNBEAT8),
                new DiscSegment(114, 7, NodPattern.DOWNBEAT4),
                new DiscSegment(114, 1, NodPattern.NONE),
                new DiscSegment(114, 8, NodPattern.DOWNBEAT4),
                new DiscSegment(114, 32),
                new DiscSegment(114, 32, NodPattern.DOWNBEAT8),
                new DiscSegment(114, 32, new NodPattern("x_______")),
                new DiscSegment(114, 128, NodPattern.NONE),
                new DiscSegment(114, 32, NodPattern.DOWNBEAT8),
                new DiscSegment(114, 33, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(114 / 2.0, 8, NodPattern.SLIGHT),
                new DiscSegment(114 / 4.0, 4, NodPattern.SLIGHT),
                new DiscSegment(114 / 8.0, 1, NodPattern.SLIGHT),
                new DiscSegment(114, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_wait", DISC_WAIT_SEGMENTS, 0));

        final List<DiscSegment> DISC_OTHERSIDE_SEGMENTS = java.util.List.of(
                new DiscSegment(92, 15, NodPattern.NONE),
                new DiscSegment(92 / 2.0, 8, NodPattern.SLIGHT),
                new DiscSegment(92, 1),
                new DiscSegment(92, 126, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(92 / 2.0, 9, NodPattern.SLIGHT),
                new DiscSegment(92, 16, NodPattern.SLIGHT),
                new DiscSegment(92, 64),
                new DiscSegment(92, 25, NodPattern.SLIGHT),
                new DiscSegment(92 / 2.0, 3, NodPattern.SLIGHT),
                new DiscSegment(92, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_otherside", DISC_OTHERSIDE_SEGMENTS, 0));

        discManager.addDisc(new Disc("minecraft:music_disc_5", 0, 0));

        final List<DiscSegment> DISC_PIGSTEP_SEGMENTS = java.util.List.of(
                new DiscSegment(85 / 4.0, 8, NodPattern.SLIGHT),
                new DiscSegment(60.0 / ((60.0 / (85.0 / 2.0)) * .75 ), 1, NodPattern.SLIGHT), // weird offset note after 3/4 beat [ 60s / ((60s / HALF-BPM) * 3/4) ]
                new DiscSegment(60.0 / ((60.0 / (85.0 / 2.0)) * 1.25 ), 1, NodPattern.NORMAL), // correct offset
                new DiscSegment(85, 29),
                new DiscSegment(85, 2, NodPattern.SLIGHT),
                new DiscSegment(85, 30),
                new DiscSegment(85, 2, NodPattern.SLIGHT),
                new DiscSegment(85, 16, NodPattern.DOWNBEAT8),
                new DiscSegment(85, 16, NodPattern.DOWNBEAT4),
                new DiscSegment(85, 16, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                new DiscSegment(85, 1, NodPattern.SLIGHT),
                new DiscSegment(85 / 3.0, 1, NodPattern.SLIGHT),
                new DiscSegment(85 / 4.0, 1, NodPattern.SLIGHT),
                new DiscSegment(85, 8, NodPattern.NONE),
                new DiscSegment(85, 32),
                new DiscSegment(85, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_pigstep", DISC_PIGSTEP_SEGMENTS, 1.4));

        final List<DiscSegment> DISC_RELIC_SEGMENTS = java.util.List.of(
                new DiscSegment(75, 4),
                new DiscSegment(69, 4),
                new DiscSegment(59, 4),
                new DiscSegment(59, 2, NodPattern.NONE),
                new DiscSegment(59 * 4, 1), // high bpm to space note out by 1/4 beat
                new DiscSegment(68 / 4.0, 4),
                new DiscSegment(68 / 2.0, 3, NodPattern.SLIGHT),
                new DiscSegment(68 / 2.0, 1),
                new DiscSegment(68 / 2.0, 2, NodPattern.SLIGHT),
                new DiscSegment(68, 5, NodPattern.NONE),
                new DiscSegment(68, 2, NodPattern.SLIGHT),
                new DiscSegment(68, 2, NodPattern.NONE),
                new DiscSegment(68, 7, NodPattern.SLIGHT),
                new DiscSegment(68, 3, NodPattern.NONE),
                new DiscSegment(68, 11, NodPattern.SLIGHT),
                new DiscSegment(68, 1, NodPattern.NONE),
                new DiscSegment(68, 1, NodPattern.SLIGHT),
                new DiscSegment(68, 3, NodPattern.NONE),
                new DiscSegment(68 * 2, 62, new NodPattern("x_X_xxX_")),
                new DiscSegment(68, 1, NodPattern.NONE),
                new DiscSegment(68 * 2, 64, new NodPattern("xxxX")),
                new DiscSegment(68, 8, new NodPattern("xX__")),
                new DiscSegment(68, 31, NodPattern.DOWNBEAT8),
                new DiscSegment(68, 1, NodPattern.NONE),
                new DiscSegment(68, 48, NodPattern.DOWNBEAT8),
                new DiscSegment(68, 1, NodPattern.SLIGHT),
                new DiscSegment(68 / 2.0, 4, NodPattern.SLIGHT),
                new DiscSegment(68, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_relic", DISC_RELIC_SEGMENTS, 4.05));

    }

}
