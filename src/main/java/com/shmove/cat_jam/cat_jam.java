package com.shmove.cat_jam;

import com.shmove.cat_jam.helpers.CatMixinAccess;
import com.shmove.cat_jam.helpers.discs.Disc;
import com.shmove.cat_jam.helpers.discs.DiscManager;
import com.shmove.cat_jam.helpers.discs.DiscSegment;
import com.shmove.cat_jam.helpers.discs.DiscSegment.NodType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
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
        UseBlockCallback.EVENT.register(this::jukeboxRightClickEvent);
    }

    private ActionResult jukeboxRightClickEvent(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        // Ensure clientside & player is not in spectator mode
        if (!world.isClient || player.isSpectator()) return net.minecraft.util.ActionResult.PASS;

        BlockState state = world.getBlockState(hitResult.getBlockPos());

        // Ensure right-clicked block is jukebox
        if (!state.getBlock().equals(net.minecraft.block.Blocks.JUKEBOX)) return net.minecraft.util.ActionResult.PASS;

        if (!state.get(net.minecraft.block.JukeboxBlock.HAS_RECORD)) {

            // Ensure player is holding a disc
            if (!isDisc(player.getStackInHand(hand))) return net.minecraft.util.ActionResult.PASS;

            Disc disc = getDiscInHand(player, hand);

            // Get all cats in range & set jamming info
            for (CatEntity cat : getNearbyCats(hitResult.getBlockPos(), world)) {
                CatMixinAccess catmix = (CatMixinAccess) cat;
                catmix.setJammingInfo(hitResult.getBlockPos(), disc);
            }

        } else {

            // Ensure player is not sneaking
            if (player.isSneaking()) return net.minecraft.util.ActionResult.PASS;

            // Get all cats in range & remove jamming info
            for (CatEntity cat : getNearbyCats(hitResult.getBlockPos(), world)) {
                CatMixinAccess catmix = (CatMixinAccess) cat;
                catmix.setJammingInfo(hitResult.getBlockPos(), null);
            }

        }

        return net.minecraft.util.ActionResult.PASS;
    }

    private boolean isDisc(ItemStack stack) {
        return stack.getItem() instanceof MusicDiscItem;
    }

    /**
        * Get the disc in the player's hand, or a default disc if the disc is not recognised
     */
    private Disc getDiscInHand(PlayerEntity player, Hand hand) {
        String discID = player.getStackInHand(hand).getItem().toString();
        Disc disc = discManager.getDisc(discID);
        if (disc != null) return disc;

        LOGGER.warn("Playing unknown disc '" + discID + "' using default BPM and offset");
        final int DEFAULT_BPM = 60;
        final int DEFAULT_OFFSET = 0;
        return new Disc(discID, DEFAULT_BPM, DEFAULT_OFFSET);
    }

    private List<CatEntity> getNearbyCats(BlockPos jukeboxPos, World world) {
        Box box = new Box(jukeboxPos).expand(JAM_RADIUS);
        return world.getEntitiesByType(EntityType.CAT, box, EntityPredicates.VALID_LIVING_ENTITY);
    }

    /**
        * Initialise all vanilla discs with custom routines
     */
    private void initialiseDiscs() {

        discManager.addDisc(new Disc("music_disc_13", 0, 0));

        final List<DiscSegment> DISC_CAT_SEGMENTS = List.of(
                new DiscSegment(112, 56, NodType.SLIGHT),
                new DiscSegment(112, 32),
                new DiscSegment(112, 16, NodType.NONE),
                new DiscSegment(112, 64),
                new DiscSegment(112, 32, NodType.SLIGHT),
                new DiscSegment(112, 32, NodType.NONE),
                new DiscSegment(112)
        );
        discManager.addDisc(new Disc("music_disc_cat", DISC_CAT_SEGMENTS, 0));

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
        discManager.addDisc(new Disc("music_disc_blocks", DISC_BLOCKS_SEGMENTS, 0));

        final List<DiscSegment> DISC_CHIRP_SEGMENTS = List.of(
                new DiscSegment(110, 128),
                new DiscSegment(110, 80, NodType.SLIGHT),
                new DiscSegment(110, 96),
                new DiscSegment(110, -1, NodType.SLIGHT)
        );
        discManager.addDisc(new Disc("music_disc_chirp", DISC_CHIRP_SEGMENTS, 0.25));

        final List<DiscSegment> DISC_FAR_SEGMENTS = List.of(
                new DiscSegment(65, 8, NodType.NONE),
                new DiscSegment(65)
        );
        discManager.addDisc(new Disc("music_disc_far", DISC_FAR_SEGMENTS, 0));

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
        discManager.addDisc(new Disc("music_disc_mall", DISC_MALL_SEGMENTS, 0));

        final List<DiscSegment> DISC_MELLOHI_SEGMENTS = List.of(
                new DiscSegment(45.5, -1, NodType.NORMAL_WITH_SLIGHT_ON_HALF)
        );
        discManager.addDisc(new Disc("music_disc_mellohi", DISC_MELLOHI_SEGMENTS, 0));

        final List<DiscSegment> DISC_STAL_SEGMENTS = List.of(
                new DiscSegment(52.5, -1, NodType.NORMAL_WITH_SLIGHT_ON_HALF)
        );
        discManager.addDisc(new Disc("music_disc_stal", DISC_STAL_SEGMENTS, 0));

        final List<DiscSegment> DISC_STRAD_SEGMENTS = List.of(
                new DiscSegment(94, 68, NodType.SLIGHT),
                new DiscSegment(94, 4, NodType.NONE),
                new DiscSegment(94)
        );
        discManager.addDisc(new Disc("music_disc_strad", DISC_STRAD_SEGMENTS, 0));

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
        discManager.addDisc(new Disc("music_disc_ward", DISC_WARD_SEGMENTS, 18));

        discManager.addDisc(new Disc("music_disc_11", 0, 0));

        final List<DiscSegment> DISC_WAIT_SEGMENTS = List.of(
                new DiscSegment(114, 16, NodType.SLIGHT),
                new DiscSegment(114, 48),
                new DiscSegment(114, 4, NodType.NONE),
                new DiscSegment(114, 96),
                new DiscSegment(114, 132, NodType.NONE),
                new DiscSegment(114, 28, NodType.SLIGHT),
                new DiscSegment(114)
        );
        discManager.addDisc(new Disc("music_disc_wait", DISC_WAIT_SEGMENTS, 0));

        final List<DiscSegment> DISC_OTHERSIDE_SEGMENTS = List.of(
                new DiscSegment(92, 16, NodType.NONE),
                new DiscSegment(92, 15, NodType.SLIGHT),
                new DiscSegment(92, 1, NodType.NONE),
                new DiscSegment(92)
        );
        discManager.addDisc(new Disc("music_disc_otherside", DISC_OTHERSIDE_SEGMENTS, 0));

        discManager.addDisc(new Disc("music_disc_5", 0, 0));

        final List<DiscSegment> DISC_PIGSTEP_SEGMENTS = List.of(
                new DiscSegment(42.5, 16, NodType.SLIGHT),
                new DiscSegment(85, 1, NodType.NONE),
                new DiscSegment(85, -1)
        );
        discManager.addDisc(new Disc("music_disc_pigstep", DISC_PIGSTEP_SEGMENTS, 1.35));

    }

}