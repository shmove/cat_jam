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
                new DiscSegment(112, 32),
                new DiscSegment(112, 16, NodPattern.NONE),
                new DiscSegment(112, 64),
                new DiscSegment(112, 32, NodPattern.SLIGHT),
                new DiscSegment(112, 32, NodPattern.NONE),
                new DiscSegment(112)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_cat", DISC_CAT_SEGMENTS, 0));

        final List<DiscSegment> DISC_BLOCKS_SEGMENTS = java.util.List.of(
                new DiscSegment(110, 12, NodPattern.NONE),
                new DiscSegment(110, 47, NodPattern.SLIGHT),
                new DiscSegment(110, 1, NodPattern.NONE),
                new DiscSegment(110, 96, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(110, 47, NodPattern.SLIGHT),
                new DiscSegment(55, 12, NodPattern.SLIGHT),
                new DiscSegment(110, 40, NodPattern.NONE),
                new DiscSegment(110, 66, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(110, 26, NodPattern.SLIGHT),
                new DiscSegment(110, 101, NodPattern.NONE),
                new DiscSegment(110, 1, NodPattern.SLIGHT),
                new DiscSegment(110, 4, NodPattern.NONE),
                new DiscSegment(55, 2, NodPattern.SLIGHT),
                new DiscSegment(110, 7, NodPattern.NONE),
                new DiscSegment(110, 1, NodPattern.SLIGHT),
                new DiscSegment(110, 6, NodPattern.NONE),
                new DiscSegment(55, 4, NodPattern.SLIGHT),
                new DiscSegment(110, -1, NodPattern.SLIGHT)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_blocks", DISC_BLOCKS_SEGMENTS, 0));

        final List<DiscSegment> DISC_CHIRP_SEGMENTS = java.util.List.of(
                new DiscSegment(110, 128),
                new DiscSegment(110, 80, NodPattern.SLIGHT),
                new DiscSegment(110, 96),
                new DiscSegment(110, -1, NodPattern.SLIGHT)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_chirp", DISC_CHIRP_SEGMENTS, 0.25));

        final List<DiscSegment> DISC_FAR_SEGMENTS = java.util.List.of(
                new DiscSegment(65, 8, NodPattern.NONE),
                new DiscSegment(65)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_far", DISC_FAR_SEGMENTS, 0));

        final List<DiscSegment> DISC_MALL_SEGMENTS = java.util.List.of(
                new DiscSegment(115, 32, NodPattern.NONE),
                new DiscSegment(115, 32, NodPattern.SLIGHT),
                new DiscSegment(115, 30),
                new DiscSegment(115, 2, NodPattern.NONE),
                new DiscSegment(115, 64),
                new DiscSegment(115, 56, NodPattern.NONE),
                new DiscSegment(115, 32, NodPattern.SLIGHT),
                new DiscSegment(115)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_mall", DISC_MALL_SEGMENTS, 0));

        final List<DiscSegment> DISC_MELLOHI_SEGMENTS = java.util.List.of(
                new DiscSegment(91, -1, NodPattern.SLIGHT_NORMAL_ALTERNATING)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_mellohi", DISC_MELLOHI_SEGMENTS, 0));

        final List<DiscSegment> DISC_STAL_SEGMENTS = java.util.List.of(
                new DiscSegment(105, -1, NodPattern.SLIGHT_NORMAL_ALTERNATING)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_stal", DISC_STAL_SEGMENTS, 0));

        final List<DiscSegment> DISC_STRAD_SEGMENTS = java.util.List.of(
                new DiscSegment(94, 68, NodPattern.SLIGHT),
                new DiscSegment(94, 4, NodPattern.NONE),
                new DiscSegment(94)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_strad", DISC_STRAD_SEGMENTS, 0));

        final List<DiscSegment> DISC_WARD_SEGMENTS = java.util.List.of(
                new DiscSegment(107, 32, NodPattern.SLIGHT),
                new DiscSegment(107, 48),
                new DiscSegment(107, 64, NodPattern.SLIGHT),
                new DiscSegment(107, 36, NodPattern.NONE),
                new DiscSegment(107, 64),
                new DiscSegment(107, 16, NodPattern.SLIGHT),
                new DiscSegment(107, 28, NodPattern.NONE),
                new DiscSegment(107, 16, NodPattern.SLIGHT),
                new DiscSegment(107)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_ward", DISC_WARD_SEGMENTS, 18));

        discManager.addDisc(new Disc("minecraft:music_disc_11", 0, 0));

        final List<DiscSegment> DISC_WAIT_SEGMENTS = java.util.List.of(
                new DiscSegment(114, 16, NodPattern.SLIGHT),
                new DiscSegment(114, 48),
                new DiscSegment(114, 4, NodPattern.NONE),
                new DiscSegment(114, 96),
                new DiscSegment(114, 132, NodPattern.NONE),
                new DiscSegment(114, 28, NodPattern.SLIGHT),
                new DiscSegment(114)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_wait", DISC_WAIT_SEGMENTS, 0));

        final List<DiscSegment> DISC_OTHERSIDE_SEGMENTS = java.util.List.of(
                new DiscSegment(92, 16, NodPattern.NONE),
                new DiscSegment(92, 15, NodPattern.SLIGHT),
                new DiscSegment(92, 1, NodPattern.NONE),
                new DiscSegment(92)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_otherside", DISC_OTHERSIDE_SEGMENTS, 0));

        discManager.addDisc(new Disc("minecraft:music_disc_5", 0, 0));

        final List<DiscSegment> DISC_PIGSTEP_SEGMENTS = java.util.List.of(
                new DiscSegment(42.5, 16, NodPattern.SLIGHT),
                new DiscSegment(85, 1, NodPattern.NONE),
                new DiscSegment(85, -1)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_pigstep", DISC_PIGSTEP_SEGMENTS, 1.35));

        final List<DiscSegment> DISC_RELIC_SEGMENTS = java.util.List.of(
                new DiscSegment(76, 5, NodPattern.SLIGHT),
                new DiscSegment(36, 1, NodPattern.SLIGHT), // this section is a little weird due to song slowing down
                new DiscSegment(68, 7, NodPattern.NONE),
                new DiscSegment(68, 16, NodPattern.SLIGHT),
                new DiscSegment(68, 14, NodPattern.DOWNBEAT4),
                new DiscSegment(68, 4, NodPattern.NONE),
                new DiscSegment(68, 27, NodPattern.SLIGHT),
                new DiscSegment(68, 3, NodPattern.NONE),
                new DiscSegment(68, 1),
                new DiscSegment(136, 64, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(68, 31),
                new DiscSegment(68, -1, NodPattern.DOWNBEAT4)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_relic", DISC_RELIC_SEGMENTS, 4.1));

    }

}
