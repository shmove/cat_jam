package com.shmove.cat_jam;

import com.shmove.cat_jam.helpers.CatEntityMixinAccess;
import com.shmove.cat_jam.helpers.discs.Disc;
import com.shmove.cat_jam.helpers.discs.DiscManager;
import com.shmove.cat_jam.helpers.discs.DiscSegment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class cat_jam {

    public static final String MOD_ID = "cat_jam";

    public static final DiscManager discManager = new DiscManager();
    public static final double JAM_RADIUS = 3.46D;

    public static final Logger LOGGER = LoggerFactory.getLogger("cat_jam");

    public static void init() {
        initialiseDiscs();
    }

    public static void jukeboxDiscUpdateEvent(World world, BlockPos jukeboxPos, @Nullable Disc disc) {
        if (disc == null) {
            // Get all cats in range & remove jamming info
            for (CatEntity cat : getNearbyCats(jukeboxPos, world)) {
                CatEntityMixinAccess catmix = (CatEntityMixinAccess) cat;
                catmix.setJammingInfo(jukeboxPos, null);
            }
        } else {
            // Get all cats in range & set jamming info
            for (CatEntity cat : getNearbyCats(jukeboxPos, world)) {
                CatEntityMixinAccess catmix = (CatEntityMixinAccess) cat;
                catmix.setJammingInfo(jukeboxPos, disc);
            }
        }
    }

    private static List<CatEntity> getNearbyCats(BlockPos jukeboxPos, World world) {
        Box box = new Box(jukeboxPos).expand(JAM_RADIUS);
        return world.getEntitiesByType(EntityType.CAT, box, EntityPredicates.VALID_LIVING_ENTITY);
    }

    /**
     * Initialise all vanilla discs with custom routines
     */
    private static void initialiseDiscs() {

        discManager.addDisc(new Disc("minecraft:music_disc_13", 0, 0));

        final java.util.List<DiscSegment> DISC_CAT_SEGMENTS = java.util.List.of(
                new DiscSegment(112, 56, DiscSegment.NodType.SLIGHT),
                new DiscSegment(112, 32),
                new DiscSegment(112, 16, DiscSegment.NodType.NONE),
                new DiscSegment(112, 64),
                new DiscSegment(112, 32, DiscSegment.NodType.SLIGHT),
                new DiscSegment(112, 32, DiscSegment.NodType.NONE),
                new DiscSegment(112)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_cat", DISC_CAT_SEGMENTS, 0));

        final List<DiscSegment> DISC_BLOCKS_SEGMENTS = java.util.List.of(
                new DiscSegment(110, 12, DiscSegment.NodType.NONE),
                new DiscSegment(110, 47, DiscSegment.NodType.SLIGHT),
                new DiscSegment(110, 1, DiscSegment.NodType.NONE),
                new DiscSegment(55, 48, DiscSegment.NodType.NORMAL_WITH_SLIGHT_ON_HALF),
                new DiscSegment(110, 47, DiscSegment.NodType.SLIGHT),
                new DiscSegment(55, 12, DiscSegment.NodType.SLIGHT),
                new DiscSegment(110, 40, DiscSegment.NodType.NONE),
                new DiscSegment(55, 33, DiscSegment.NodType.NORMAL_WITH_SLIGHT_ON_HALF),
                new DiscSegment(110, 26, DiscSegment.NodType.SLIGHT),
                new DiscSegment(110, 101, DiscSegment.NodType.NONE),
                new DiscSegment(110, 1, DiscSegment.NodType.SLIGHT),
                new DiscSegment(110, 4, DiscSegment.NodType.NONE),
                new DiscSegment(55, 2, DiscSegment.NodType.SLIGHT),
                new DiscSegment(110, 7, DiscSegment.NodType.NONE),
                new DiscSegment(110, 1, DiscSegment.NodType.SLIGHT),
                new DiscSegment(110, 6, DiscSegment.NodType.NONE),
                new DiscSegment(55, 4, DiscSegment.NodType.SLIGHT),
                new DiscSegment(110, -1, DiscSegment.NodType.SLIGHT)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_blocks", DISC_BLOCKS_SEGMENTS, 0));

        final List<DiscSegment> DISC_CHIRP_SEGMENTS = java.util.List.of(
                new DiscSegment(110, 128),
                new DiscSegment(110, 80, DiscSegment.NodType.SLIGHT),
                new DiscSegment(110, 96),
                new DiscSegment(110, -1, DiscSegment.NodType.SLIGHT)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_chirp", DISC_CHIRP_SEGMENTS, 0.25));

        final List<DiscSegment> DISC_FAR_SEGMENTS = java.util.List.of(
                new DiscSegment(65, 8, DiscSegment.NodType.NONE),
                new DiscSegment(65)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_far", DISC_FAR_SEGMENTS, 0));

        final List<DiscSegment> DISC_MALL_SEGMENTS = java.util.List.of(
                new DiscSegment(115, 32, DiscSegment.NodType.NONE),
                new DiscSegment(115, 32, DiscSegment.NodType.SLIGHT),
                new DiscSegment(115, 30),
                new DiscSegment(115, 2, DiscSegment.NodType.NONE),
                new DiscSegment(115, 64),
                new DiscSegment(115, 56, DiscSegment.NodType.NONE),
                new DiscSegment(115, 32, DiscSegment.NodType.SLIGHT),
                new DiscSegment(115)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_mall", DISC_MALL_SEGMENTS, 0));

        final List<DiscSegment> DISC_MELLOHI_SEGMENTS = java.util.List.of(
                new DiscSegment(45.5, -1, DiscSegment.NodType.NORMAL_WITH_SLIGHT_ON_HALF)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_mellohi", DISC_MELLOHI_SEGMENTS, 0));

        final List<DiscSegment> DISC_STAL_SEGMENTS = java.util.List.of(
                new DiscSegment(52.5, -1, DiscSegment.NodType.NORMAL_WITH_SLIGHT_ON_HALF)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_stal", DISC_STAL_SEGMENTS, 0));

        final List<DiscSegment> DISC_STRAD_SEGMENTS = java.util.List.of(
                new DiscSegment(94, 68, DiscSegment.NodType.SLIGHT),
                new DiscSegment(94, 4, DiscSegment.NodType.NONE),
                new DiscSegment(94)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_strad", DISC_STRAD_SEGMENTS, 0));

        final List<DiscSegment> DISC_WARD_SEGMENTS = java.util.List.of(
                new DiscSegment(107, 32, DiscSegment.NodType.SLIGHT),
                new DiscSegment(107, 48),
                new DiscSegment(107, 64, DiscSegment.NodType.SLIGHT),
                new DiscSegment(107, 36, DiscSegment.NodType.NONE),
                new DiscSegment(107, 64),
                new DiscSegment(107, 16, DiscSegment.NodType.SLIGHT),
                new DiscSegment(107, 28, DiscSegment.NodType.NONE),
                new DiscSegment(107, 16, DiscSegment.NodType.SLIGHT),
                new DiscSegment(107)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_ward", DISC_WARD_SEGMENTS, 18));

        discManager.addDisc(new Disc("minecraft:music_disc_11", 0, 0));

        final List<DiscSegment> DISC_WAIT_SEGMENTS = java.util.List.of(
                new DiscSegment(114, 16, DiscSegment.NodType.SLIGHT),
                new DiscSegment(114, 48),
                new DiscSegment(114, 4, DiscSegment.NodType.NONE),
                new DiscSegment(114, 96),
                new DiscSegment(114, 132, DiscSegment.NodType.NONE),
                new DiscSegment(114, 28, DiscSegment.NodType.SLIGHT),
                new DiscSegment(114)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_wait", DISC_WAIT_SEGMENTS, 0));

        final List<DiscSegment> DISC_OTHERSIDE_SEGMENTS = java.util.List.of(
                new DiscSegment(92, 16, DiscSegment.NodType.NONE),
                new DiscSegment(92, 15, DiscSegment.NodType.SLIGHT),
                new DiscSegment(92, 1, DiscSegment.NodType.NONE),
                new DiscSegment(92)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_otherside", DISC_OTHERSIDE_SEGMENTS, 0));

        final List<DiscSegment> DISC_PIGSTEP_SEGMENTS = java.util.List.of(
                new DiscSegment(42.5, 16, DiscSegment.NodType.SLIGHT),
                new DiscSegment(85, 1, DiscSegment.NodType.NONE),
                new DiscSegment(85, -1)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_pigstep", DISC_PIGSTEP_SEGMENTS, 1.35));

    }

}
