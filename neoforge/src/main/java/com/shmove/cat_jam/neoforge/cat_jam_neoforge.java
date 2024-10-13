package com.shmove.cat_jam.neoforge;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.discs.Disc;
import com.shmove.cat_jam.discs.DiscSegment;
import com.shmove.cat_jam.discs.NodPattern;
import com.shmove.cat_jam.neoforge.compat.NeoForgeMods;
import net.minecraft.client.MinecraftClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.List;

import static com.shmove.cat_jam.cat_jam.discManager;

@Mod(cat_jam.MOD_ID)
@EventBusSubscriber(Dist.CLIENT)
public final class cat_jam_neoforge {

    public cat_jam_neoforge() {
        cat_jam.init();

        initialiseModdedDiscs();

        cat_jam.LOGGER.info("cat_jam successfully initialised! (" + discManager.getDiscCount() + " customised jams loaded)");
    }

    @SubscribeEvent
    public static void onClientWorldTickEnd(LevelTickEvent.Post event) {
        if (event.getLevel().isClient)
            cat_jam.tickPlayingDiscs(MinecraftClient.getInstance().world);
    }

    private void initialiseModdedDiscs() {

        if (NeoForgeMods.ALEXS_MOBS.isInstalled()) {

            final List<DiscSegment> DISC_DAZE_SEGMENTS = List.of(
                    new DiscSegment(159, 32, new NodPattern("x___")),
                    new DiscSegment(159, 96, new NodPattern("x_x_x_xx")),
                    new DiscSegment(159, 20, NodPattern.NONE),
                    new DiscSegment(159, 16, new NodPattern("x___")),
                    new DiscSegment(159, 32, new NodPattern("X_xx")),
                    new DiscSegment(159, 64, new NodPattern("Xxxx")),
                    new DiscSegment(159, 76, NodPattern.NONE),
                    new DiscSegment(159, 32, new NodPattern("x___")),
                    new DiscSegment(159 * 2, 1, NodPattern.NONE), // single offset to account for oddly timed upcoming segment
                    new DiscSegment(159 * 2, 128, new NodPattern("X__x__x_")),
                    new DiscSegment(159 * 2, 1, NodPattern.SLIGHT), // undo offset
                    new DiscSegment(159, 3, NodPattern.NONE),
                    new DiscSegment(159, 42, new NodPattern("x___")),
                    new DiscSegment(159, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("alexsmobs:music_disc_daze", DISC_DAZE_SEGMENTS, 2.652));

            final List<DiscSegment> DISC_THIME_SEGMENTS = List.of(
                    new DiscSegment(84, 16, NodPattern.NONE),
                    new DiscSegment(84, 16, new NodPattern("x___")),
                    new DiscSegment(84, 16, new NodPattern("x_")),
                    new DiscSegment(84, 32, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                    new DiscSegment(84, 16, new NodPattern("XxXxXxXX")),
                    new DiscSegment(84, 16, new NodPattern("x_")),
                    new DiscSegment(84, 16, new NodPattern("x___")),
                    new DiscSegment(84, 32, new NodPattern("x_______")),
                    new DiscSegment(84, 32, new NodPattern("x___")),
                    new DiscSegment(84, 32, new NodPattern("X_x_x_x_")),
                    new DiscSegment(84, 1, NodPattern.NORMAL),
                    new DiscSegment(84, 7, NodPattern.NONE),
                    new DiscSegment(84, 48, new NodPattern("x_______")),
                    new DiscSegment(84, 8, new NodPattern("x_x_x___")),
                    new DiscSegment(84, 32, new NodPattern("x_______")),
                    new DiscSegment(84, 32, new NodPattern("x___")),
                    new DiscSegment(84, 32, new NodPattern("X_x_x_x_")),
                    new DiscSegment(84, 32, new NodPattern("XxXxXxXxXxXxXxXX")), // when does a pattern become too long
                    new DiscSegment(84, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("alexsmobs:music_disc_thime", DISC_THIME_SEGMENTS, 0));

        }

        if (NeoForgeMods.QUARK.isInstalled()) {

            List<DiscSegment> DISC_ENDERMOSH_SEGMENTS = List.of(
                    new DiscSegment(60, 8, NodPattern.NONE),
                    new DiscSegment(60, 8, NodPattern.SLIGHT),
                    new DiscSegment(60, 16, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                    new DiscSegment(60, 8, NodPattern.SLIGHT),
                    new DiscSegment(60, 1),
                    new DiscSegment(60, 3, NodPattern.NONE),
                    new DiscSegment(60, 1, NodPattern.SLIGHT),
                    new DiscSegment(160, 1, NodPattern.NONE),
                    new DiscSegment(160, 26, new NodPattern("x_")),
                    new DiscSegment(160, 4, NodPattern.SLIGHT),
                    new DiscSegment(160, 64), // too fast?
                    new DiscSegment(160, 64, new NodPattern("x_x_x_XX")),
                    new DiscSegment(160, 32, new NodPattern("x_x_x_xx")),
                    new DiscSegment(160, 64, new NodPattern("X_X_X_xx")),
                    new DiscSegment(160, 32, new NodPattern("x_x___x_")),
                    new DiscSegment(160, 32, NodPattern.SLIGHT),
                    new DiscSegment(160, 32),
                    new DiscSegment(160, 16, NodPattern.SLIGHT),
                    new DiscSegment(160, 16, new NodPattern("x_")),
                    new DiscSegment(160, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("quark:music.endermosh", DISC_ENDERMOSH_SEGMENTS, 0.15));

            // Ambience Discs
            discManager.addDisc(new Disc("quark:ambient.chatter",  0, 0));
            discManager.addDisc(new Disc("quark:ambient.crickets", 0, 0));
            discManager.addDisc(new Disc("quark:ambient.clock",    0, 0));
            discManager.addDisc(new Disc("quark:ambient.fire",     0, 0));
            discManager.addDisc(new Disc("quark:ambient.wind",     0, 0));
            discManager.addDisc(new Disc("quark:ambient.rain",     0, 0));
            discManager.addDisc(new Disc("quark:ambient.ocean",    0, 0));
            discManager.addDisc(new Disc("quark:ambient.drips",    0, 0));

        }

    }

}
