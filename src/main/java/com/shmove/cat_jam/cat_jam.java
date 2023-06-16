package com.shmove.cat_jam;

import com.shmove.cat_jam.event.JukeboxDiscUpdateCallback;
import com.shmove.cat_jam.helpers.CatEntityMixinAccess;
import com.shmove.cat_jam.helpers.discs.Disc;
import com.shmove.cat_jam.helpers.discs.DiscManager;
import com.shmove.cat_jam.helpers.discs.DiscSegment;
import com.shmove.cat_jam.helpers.discs.DiscSegment.NodType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Environment(EnvType.CLIENT)
public class cat_jam implements ModInitializer {

    public static final DiscManager discManager = new DiscManager();
    public static final double JAM_RADIUS = 3.46D;

    public static final Logger LOGGER = LoggerFactory.getLogger("cat_jam");

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        initialiseDiscs();
        JukeboxDiscUpdateCallback.EVENT.register(this::jukeboxDiscUpdateEvent);
    }

    private ActionResult jukeboxDiscUpdateEvent(World world, BlockPos jukeboxPos, @Nullable Disc disc) {
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

        return ActionResult.PASS;
    }

    private List<CatEntity> getNearbyCats(BlockPos jukeboxPos, World world) {
        Box box = new Box(jukeboxPos).expand(JAM_RADIUS);
        return world.getEntitiesByType(EntityType.CAT, box, EntityPredicates.VALID_LIVING_ENTITY);
    }

    /**
        * Initialise all vanilla discs with custom routines
     */
    private void initialiseDiscs() {

        discManager.addDisc(new Disc("minecraft:music_disc_13", 0, 0));

        final List<DiscSegment> DISC_CAT_SEGMENTS = List.of(
                new DiscSegment(112, 56, NodType.SLIGHT),
                new DiscSegment(112, 32),
                new DiscSegment(112, 16, NodType.NONE),
                new DiscSegment(112, 64),
                new DiscSegment(112, 32, NodType.SLIGHT),
                new DiscSegment(112, 32, NodType.NONE),
                new DiscSegment(112)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_cat", DISC_CAT_SEGMENTS, 0));

        final List<DiscSegment> DISC_BLOCKS_SEGMENTS = List.of(
                new DiscSegment(110, 12, NodType.NONE),
                new DiscSegment(110, 47, NodType.SLIGHT),
                new DiscSegment(110, 1, NodType.NONE),
                new DiscSegment(55, 48, NodType.NORMAL_WITH_SLIGHT_ON_HALF),
                new DiscSegment(110, 47, NodType.SLIGHT),
                new DiscSegment(55, 12, NodType.SLIGHT),
                new DiscSegment(110, 40, NodType.NONE),
                new DiscSegment(55, 33, NodType.NORMAL_WITH_SLIGHT_ON_HALF),
                new DiscSegment(110, 26, NodType.SLIGHT),
                new DiscSegment(110, 101, NodType.NONE),
                new DiscSegment(110, 1, NodType.SLIGHT),
                new DiscSegment(110, 4, NodType.NONE),
                new DiscSegment(55, 2, NodType.SLIGHT),
                new DiscSegment(110, 7, NodType.NONE),
                new DiscSegment(110, 1, NodType.SLIGHT),
                new DiscSegment(110, 6, NodType.NONE),
                new DiscSegment(55, 4, NodType.SLIGHT),
                new DiscSegment(110, -1, NodType.SLIGHT)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_blocks", DISC_BLOCKS_SEGMENTS, 0));

        final List<DiscSegment> DISC_CHIRP_SEGMENTS = List.of(
                new DiscSegment(110, 128),
                new DiscSegment(110, 80, NodType.SLIGHT),
                new DiscSegment(110, 96),
                new DiscSegment(110, -1, NodType.SLIGHT)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_chirp", DISC_CHIRP_SEGMENTS, 0.25));

        final List<DiscSegment> DISC_FAR_SEGMENTS = List.of(
                new DiscSegment(65, 8, NodType.NONE),
                new DiscSegment(65)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_far", DISC_FAR_SEGMENTS, 0));

        final List<DiscSegment> DISC_MALL_SEGMENTS = List.of(
                new DiscSegment(115, 32, NodType.NONE),
                new DiscSegment(115, 32, NodType.SLIGHT),
                new DiscSegment(115, 30),
                new DiscSegment(115, 2, NodType.NONE),
                new DiscSegment(115, 64),
                new DiscSegment(115, 56, NodType.NONE),
                new DiscSegment(115, 32, NodType.SLIGHT),
                new DiscSegment(115)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_mall", DISC_MALL_SEGMENTS, 0));

        final List<DiscSegment> DISC_MELLOHI_SEGMENTS = List.of(
                new DiscSegment(45.5, -1, NodType.NORMAL_WITH_SLIGHT_ON_HALF)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_mellohi", DISC_MELLOHI_SEGMENTS, 0));

        final List<DiscSegment> DISC_STAL_SEGMENTS = List.of(
                new DiscSegment(52.5, -1, NodType.NORMAL_WITH_SLIGHT_ON_HALF)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_stal", DISC_STAL_SEGMENTS, 0));

        final List<DiscSegment> DISC_STRAD_SEGMENTS = List.of(
                new DiscSegment(94, 68, NodType.SLIGHT),
                new DiscSegment(94, 4, NodType.NONE),
                new DiscSegment(94)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_strad", DISC_STRAD_SEGMENTS, 0));

        final List<DiscSegment> DISC_WARD_SEGMENTS = List.of(
                new DiscSegment(107, 32, NodType.SLIGHT),
                new DiscSegment(107, 48),
                new DiscSegment(107, 64, NodType.SLIGHT),
                new DiscSegment(107, 36, NodType.NONE),
                new DiscSegment(107, 64),
                new DiscSegment(107, 16, NodType.SLIGHT),
                new DiscSegment(107, 28, NodType.NONE),
                new DiscSegment(107, 16, NodType.SLIGHT),
                new DiscSegment(107)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_ward", DISC_WARD_SEGMENTS, 18));

        discManager.addDisc(new Disc("minecraft:music_disc_11", 0, 0));

        final List<DiscSegment> DISC_WAIT_SEGMENTS = List.of(
                new DiscSegment(114, 16, NodType.SLIGHT),
                new DiscSegment(114, 48),
                new DiscSegment(114, 4, NodType.NONE),
                new DiscSegment(114, 96),
                new DiscSegment(114, 132, NodType.NONE),
                new DiscSegment(114, 28, NodType.SLIGHT),
                new DiscSegment(114)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_wait", DISC_WAIT_SEGMENTS, 0));

        final List<DiscSegment> DISC_OTHERSIDE_SEGMENTS = List.of(
                new DiscSegment(92, 16, NodType.NONE),
                new DiscSegment(92, 15, NodType.SLIGHT),
                new DiscSegment(92, 1, NodType.NONE),
                new DiscSegment(92)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_otherside", DISC_OTHERSIDE_SEGMENTS, 0));

        discManager.addDisc(new Disc("minecraft:music_disc_5", 0, 0));

        final List<DiscSegment> DISC_PIGSTEP_SEGMENTS = List.of(
                new DiscSegment(42.5, 16, NodType.SLIGHT),
                new DiscSegment(85, 1, NodType.NONE),
                new DiscSegment(85, -1)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_pigstep", DISC_PIGSTEP_SEGMENTS, 1.35));

        final List<DiscSegment> DISC_RELIC_SEGMENTS = List.of(
                new DiscSegment(76, 5, NodType.SLIGHT),
                new DiscSegment(36, 1, NodType.SLIGHT), // this section is a little weird due to song slowing down
                new DiscSegment(68, 7, NodType.NONE),
                new DiscSegment(68, 16, NodType.SLIGHT),
                new DiscSegment(68, 14, NodType.SLIGHT_WITH_NORMAL_DOWNBEAT),
                new DiscSegment(68, 4, NodType.NONE),
                new DiscSegment(68, 27, NodType.SLIGHT),
                new DiscSegment(68, 3, NodType.NONE),
                new DiscSegment(68, 1),
                new DiscSegment(68, 32, NodType.NORMAL_WITH_SLIGHT_ON_HALF),
                new DiscSegment(68, 31),
                new DiscSegment(68, -1, NodType.SLIGHT_WITH_NORMAL_DOWNBEAT)
        );
        discManager.addDisc(new Disc("minecraft:music_disc_relic", DISC_RELIC_SEGMENTS, 4.1));

    }

}