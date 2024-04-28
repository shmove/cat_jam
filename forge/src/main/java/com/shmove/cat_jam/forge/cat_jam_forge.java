package com.shmove.cat_jam.forge;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.forge.compat.ForgeMods;
import com.shmove.cat_jam.helpers.discs.Disc;
import com.shmove.cat_jam.helpers.discs.DiscSegment;
import com.shmove.cat_jam.helpers.discs.NodPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.shmove.cat_jam.cat_jam.discManager;

@Mod(cat_jam.MOD_ID)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class cat_jam_forge {

    public cat_jam_forge() {
        cat_jam.init();

        initialiseModdedDiscs();

        cat_jam.LOGGER.info("cat_jam successfully initialised! (" + discManager.getDiscCount() + " customised jams loaded)");
    }

    @SubscribeEvent
    public static void onClientWorldTickEnd(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.CLIENT && event.phase == TickEvent.Phase.END)
            cat_jam.tickPlayingDiscs(MinecraftClient.getInstance().world);
    }

    private void initialiseModdedDiscs() {

        // Modded Discs

        if (ForgeMods.QUARK.isInstalled()) {

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
            discManager.addDisc(new Disc("quark:music_disc_endermosh", DISC_ENDERMOSH_SEGMENTS, 0.15));

            // Ambience Discs
            discManager.addDisc(new Disc("quark:music_disc_chatter",  0, 0));
            discManager.addDisc(new Disc("quark:music_disc_crickets", 0, 0));
            discManager.addDisc(new Disc("quark:music_disc_clock",    0, 0));
            discManager.addDisc(new Disc("quark:music_disc_fire",     0, 0));
            discManager.addDisc(new Disc("quark:music_disc_wind",     0, 0));
            discManager.addDisc(new Disc("quark:music_disc_rain",     0, 0));
            discManager.addDisc(new Disc("quark:music_disc_ocean",    0, 0));
            discManager.addDisc(new Disc("quark:music_disc_drips",    0, 0));

        }

    }

}
