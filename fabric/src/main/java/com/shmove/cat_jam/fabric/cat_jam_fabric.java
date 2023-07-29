package com.shmove.cat_jam.fabric;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.fabric.compat.FabricMods;
import com.shmove.cat_jam.fabric.compat.audioplayer.AudioPlayer;
import com.shmove.cat_jam.helpers.discs.Disc;
import com.shmove.cat_jam.helpers.discs.DiscManager;
import com.shmove.cat_jam.helpers.discs.DiscSegment;
import com.shmove.cat_jam.helpers.discs.NodPattern;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.List;

import static com.shmove.cat_jam.cat_jam.discManager;

public class cat_jam_fabric implements ModInitializer {

    @Override
    public void onInitialize() {
        cat_jam.init();

        if (FabricMods.AUDIO_PLAYER.isInstalled()) ClientTickEvents.END_WORLD_TICK.register(world -> AudioPlayer.tick());

        ClientTickEvents.END_WORLD_TICK.register(cat_jam::tickPlayingDiscs);
        initialiseModdedDiscs();
    }

    private void initialiseModdedDiscs() {

        // Modded Discs

        if (FabricMods.AUDIO_PLAYER.isInstalled()) discManager.addDisc(new Disc(AudioPlayer.CUSTOM_DISC_ID, DiscManager.DEFAULT_BPM, DiscManager.DEFAULT_OFFSET));

        if (FabricMods.DISCS_WHERE_DISCS_SHOULDNT_BE.isInstalled()) {

            final List<DiscSegment> DISC_WAVE_SEGMENTS = List.of(
                    new DiscSegment(60, 3, NodPattern.SLIGHT),
                    new DiscSegment(120, 1, NodPattern.NONE),
                    new DiscSegment(120, 21, new NodPattern("_x_x_x_")),
                    new DiscSegment(60, 16, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(60, 2, NodPattern.NONE),
                    new DiscSegment(60, 1, NodPattern.SLIGHT),
                    new DiscSegment(60, 1),
                    new DiscSegment(60, 3, NodPattern.NONE),
                    new DiscSegment(60, 1),
                    new DiscSegment(60, 2, NodPattern.NONE),
                    new DiscSegment(60, 1, NodPattern.SLIGHT),
                    new DiscSegment(60, 1),
                    new DiscSegment(60, 3, NodPattern.NONE),
                    new DiscSegment(60, 1, NodPattern.SLIGHT),
                    new DiscSegment(60, 1),
                    new DiscSegment(120, 28, new NodPattern("__xxx_X")),
                    new DiscSegment(120, 2, NodPattern.NONE),
                    new DiscSegment(120, 2, NodPattern.SLIGHT),
                    new DiscSegment(120, 32, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(120, 1, NodPattern.SLIGHT),
                    new DiscSegment(60, -1, NodPattern.SLIGHT)
            );
            discManager.addDisc(new Disc("dwdsb:wave", DISC_WAVE_SEGMENTS, 6.08));

            final List<DiscSegment> DISC_LOST_SEGMENTS = List.of(
                    new DiscSegment(48, 15, NodPattern.SLIGHT),
                    new DiscSegment(48, 1, NodPattern.NONE),
                    new DiscSegment(48, 20, NodPattern.DOWNBEAT4),
                    new DiscSegment(48, 2, NodPattern.NONE),
                    new DiscSegment(48, 1, NodPattern.SLIGHT),
                    new DiscSegment(48, 2, NodPattern.NONE),
                    new DiscSegment(144, 3, NodPattern.SLIGHT),
                    new DiscSegment(48, 1),
                    new DiscSegment(48, 8, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(48, 3),
                    new DiscSegment(144, 24, NodPattern.NONE),
                    new DiscSegment(48, 21, NodPattern.SLIGHT),
                    new DiscSegment(144, 12, NodPattern.NONE),
                    new DiscSegment(48, 4, NodPattern.SLIGHT),
                    new DiscSegment(144, 2, NodPattern.SLIGHT),
                    new DiscSegment(144, 1),
                    new DiscSegment(48, 19),
                    new DiscSegment(48, 3, NodPattern.SLIGHT),
                    new DiscSegment(144, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("dwdsb:lost", DISC_LOST_SEGMENTS, 2.074));

            final List<DiscSegment> DISC_WATCHED_SEGMENTS = List.of(
                    new DiscSegment(71, 16, NodPattern.NONE),
                    new DiscSegment( (71 / 3.0) , 3, NodPattern.SLIGHT),
                    new DiscSegment(71, 2, NodPattern.NONE),
                    new DiscSegment( (71 / 3.0) , 1, NodPattern.SLIGHT),
                    new DiscSegment(71, 27, NodPattern.NONE),
                    new DiscSegment(35.5, 4, NodPattern.SLIGHT),
                    new DiscSegment( (71 / 3.0) , 2, NodPattern.SLIGHT),
                    new DiscSegment(35.5, 3, NodPattern.SLIGHT),
                    new DiscSegment( (71 / 3.0) , 1, NodPattern.SLIGHT),
                    new DiscSegment(71, 29, NodPattern.NONE),
                    new DiscSegment(35.5, 2, NodPattern.SLIGHT),
                    new DiscSegment(71, 2, NodPattern.NONE),
                    new DiscSegment(35.5, 2, NodPattern.SLIGHT),
                    new DiscSegment(71, 1, NodPattern.NONE),
                    new DiscSegment(35.5, 7, NodPattern.SLIGHT),
                    new DiscSegment( (71 / 3.0) , 3, NodPattern.SLIGHT),
                    new DiscSegment(71, 3, NodPattern.NONE),
                    new DiscSegment( (71 / 3.0) , 8, NodPattern.SLIGHT),
                    new DiscSegment(35.5, 6, NodPattern.SLIGHT),
                    new DiscSegment(71, 2, NodPattern.NONE),
                    new DiscSegment(35.5, 5, NodPattern.SLIGHT),
                    new DiscSegment(71, 5, NodPattern.NONE),
                    new DiscSegment(35.5, 2, NodPattern.SLIGHT),
                    new DiscSegment(71, 3, NodPattern.NONE),
                    new DiscSegment(142, 1, NodPattern.NONE), // hacky half beat
                    new DiscSegment(71, 1, NodPattern.SLIGHT),
                    new DiscSegment(142, 1, NodPattern.NONE), // hacky half beat
                    new DiscSegment(71, 2, NodPattern.NONE),
                    new DiscSegment(71, 1, NodPattern.SLIGHT),
                    new DiscSegment(71, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("dwdsb:watched", DISC_WATCHED_SEGMENTS, 0.23));

            final List<DiscSegment> DISC_REST_SEGMENTS = List.of(
                    new DiscSegment(92, 15, NodPattern.SLIGHT),
                    new DiscSegment(92, 1, NodPattern.NONE),
                    new DiscSegment(92, 12, NodPattern.DOWNBEAT4),
                    new DiscSegment(92, 1),
                    new DiscSegment(92, 2, NodPattern.SLIGHT),
                    new DiscSegment(92, 17, NodPattern.NONE),
                    new DiscSegment(92, 16, NodPattern.SLIGHT),
                    new DiscSegment(92, 16, NodPattern.DOWNBEAT4),
                    new DiscSegment(92, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("dwdsb:rest", DISC_REST_SEGMENTS, 47.03));

            final List<DiscSegment> DISC_MIRROR_SEGMENTS = List.of(
                    new DiscSegment(57, 8, NodPattern.SLIGHT),
                    new DiscSegment(57, 24),
                    new DiscSegment(114, 1, NodPattern.SLIGHT),
                    new DiscSegment(38, 2, NodPattern.SLIGHT),
                    new DiscSegment(114, 1),
                    new DiscSegment(57, 7),
                    new DiscSegment(114, 32, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(114, 1),
                    new DiscSegment(19, 1, NodPattern.SLIGHT),
                    new DiscSegment(14.25, 3, NodPattern.SLIGHT),
                    new DiscSegment(57, 1, NodPattern.SLIGHT),
                    new DiscSegment(114, 32, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(114, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("dwdsb:mirror", DISC_MIRROR_SEGMENTS, 17.4));

            final List<DiscSegment> DISC_RAIN_SEGMENTS = List.of(
                    new DiscSegment(92, 37, NodPattern.NONE),
                    new DiscSegment(23, 4, NodPattern.SLIGHT),
                    new DiscSegment(46, 2, NodPattern.SLIGHT),
                    new DiscSegment(23, 3, NodPattern.SLIGHT),
                    new DiscSegment(46, 2, NodPattern.SLIGHT),
                    new DiscSegment(23, 6, NodPattern.SLIGHT),
                    new DiscSegment(46, 2, NodPattern.SLIGHT),
                    new DiscSegment(23, 3, NodPattern.SLIGHT),
                    new DiscSegment(23, 1),
                    new DiscSegment(46, 14, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(46, 2, NodPattern.SLIGHT),
                    new DiscSegment(11.5, 3, NodPattern.SLIGHT),
                    new DiscSegment(46, 16, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(46, 2, NodPattern.SLIGHT),
                    new DiscSegment(46, 3, NodPattern.NONE),
                    new DiscSegment(23, 12, NodPattern.SLIGHT),
                    new DiscSegment(23, 2, NodPattern.NONE),
                    new DiscSegment(46, 1, NodPattern.SLIGHT),
                    new DiscSegment(92, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("dwdsb:rain", DISC_RAIN_SEGMENTS, 2.7));

            final List<DiscSegment> DISC_ALONE_SEGMENTS = List.of(
                    new DiscSegment(71, 16, NodPattern.NONE),
                    new DiscSegment(71, 16, NodPattern.DOWNBEAT4),
                    new DiscSegment(71, 9, NodPattern.NONE),
                    new DiscSegment(71, 7, NodPattern.SLIGHT),
                    new DiscSegment(71, 14, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(71, 1, NodPattern.SLIGHT),
                    new DiscSegment(71, 4, NodPattern.NONE), // the lick
                    new DiscSegment(71, 1),
                    new DiscSegment(71, 8, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(71, 1, NodPattern.NONE),
                    new DiscSegment(71, 7, NodPattern.SLIGHT),
                    new DiscSegment(71, 8, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(71, 3, NodPattern.NONE),
                    new DiscSegment(71, 17, NodPattern.SLIGHT),
                    new DiscSegment(71, 16, NodPattern.DOWNBEAT4),
                    new DiscSegment(71, 4, NodPattern.NONE),
                    new DiscSegment(71, 1, NodPattern.SLIGHT),
                    new DiscSegment(71, 2, NodPattern.NONE),
                    new DiscSegment(71, 1, NodPattern.SLIGHT),
                    new DiscSegment(71, 36, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(71, 1, NodPattern.SLIGHT),
                    new DiscSegment(71, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("dwdsb:alone", DISC_ALONE_SEGMENTS, 0.085));

            List<DiscSegment> DISC_BEAN_SEGMENTS = List.of(
                    new DiscSegment(30, 6, NodPattern.SLIGHT),
                    new DiscSegment(60, 1, NodPattern.SLIGHT),
                    new DiscSegment(240, 5, NodPattern.NONE), // hackiest example of semi beats
                    new DiscSegment(240, 24, new NodPattern("_X__x___")),
                    new DiscSegment(120, 1),
                    new DiscSegment(120, 1, NodPattern.NONE),
                    new DiscSegment(480, 1, NodPattern.NONE), // quarter beat
                    new DiscSegment(120, 16, new NodPattern("_x_x_x__")),
                    new DiscSegment(120, 16, new NodPattern("xX_x_x__")),
                    new DiscSegment(120, 1, NodPattern.SLIGHT),
                    new DiscSegment(120, 32, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(120, 1, NodPattern.SLIGHT),
                    new DiscSegment(60, 15, NodPattern.SLIGHT),
                    new DiscSegment(120, 1, NodPattern.NONE),
                    new DiscSegment(120, 32, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(120, 1, NodPattern.SLIGHT),
                    new DiscSegment(60, 8, NodPattern.SLIGHT),
                    new DiscSegment(30, 3, NodPattern.SLIGHT),
                    new DiscSegment(60, 1, NodPattern.SLIGHT),
                    new DiscSegment(120, 1, NodPattern.NONE),
                    new DiscSegment(240, 1, NodPattern.NONE), // half beat
                    new DiscSegment(60, 8),
                    new DiscSegment(120, 1, NodPattern.NONE),
                    new DiscSegment(240, 1, NodPattern.NONE), // half beat
                    new DiscSegment(60, 8),
                    new DiscSegment(120, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("dwdsb:bean", DISC_BEAN_SEGMENTS, 4.08));

            List<DiscSegment> DISC_LYRE_SEGMENTS = List.of(
                    new DiscSegment(23, 5, NodPattern.SLIGHT),
                    new DiscSegment(46, 7, NodPattern.SLIGHT),
                    new DiscSegment(46, 17),
                    new DiscSegment(46, 2, NodPattern.NONE),
                    new DiscSegment(46, 16, new NodPattern("_x_x_xxx")),
                    new DiscSegment(46, 1, NodPattern.NONE),
                    new DiscSegment(46, 9),
                    new DiscSegment(46, 6, new NodPattern("__x")),
                    new DiscSegment(46, 1, NodPattern.NONE),
                    new DiscSegment(46, 18, NodPattern.SLIGHT),
                    new DiscSegment(46, 64),
                    new DiscSegment(46, -1, NodPattern.SLIGHT)
            );
            discManager.addDisc(new Disc("dwdsb:lyre", DISC_LYRE_SEGMENTS, 5.29));

            List<DiscSegment> DISC_EMBER_SEGMENTS = List.of(
                    new DiscSegment(35.5, 9, NodPattern.NONE),
                    new DiscSegment(35.5, 1, NodPattern.SLIGHT),
                    new DiscSegment(35.5, 7, NodPattern.NONE),
                    new DiscSegment(35.5, 53, NodPattern.SLIGHT),
                    new DiscSegment(35.5, 16, new NodPattern("_X_X_X__")),
                    new DiscSegment(17.75, 2, NodPattern.SLIGHT),
                    new DiscSegment(35.5, 4, NodPattern.NONE),
                    new DiscSegment(17.75, 2, NodPattern.SLIGHT),
                    new DiscSegment(35.5, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("dwdsb:ember", DISC_EMBER_SEGMENTS, 0.08));

            List<DiscSegment> DISC_STAR_SEGMENTS = List.of(
                    new DiscSegment(105, 1, NodPattern.SLIGHT),
                    new DiscSegment(105, 60, NodPattern.DOWNBEAT3),
                    new DiscSegment(105, 1),
                    new DiscSegment(105, 2, NodPattern.NONE),
                    new DiscSegment(105, 1),
                    new DiscSegment(105, 2, NodPattern.NONE),
                    new DiscSegment(105, 12, NodPattern.DOWNBEAT3),
                    new DiscSegment(105, 43),
                    new DiscSegment(105, 18, new NodPattern("__x")),
                    new DiscSegment(105, 5, NodPattern.NONE),
                    new DiscSegment(105, 1, NodPattern.SLIGHT),
                    new DiscSegment(105, 102, NodPattern.NONE),
                    new DiscSegment(210, 1, NodPattern.NONE), // hacky half beat to correct sync
                    new DiscSegment(105, 88),
                    new DiscSegment(105, 1, NodPattern.NONE),
                    new DiscSegment(105, 1),
                    new DiscSegment(105, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("dwdsb:star", DISC_STAR_SEGMENTS, 6.36));

            List<DiscSegment> DISC_RAVE_SEGMENTS = List.of(
                    new DiscSegment(60, 15, NodPattern.SLIGHT),
                    new DiscSegment(120, 2, NodPattern.NONE),
                    new DiscSegment(120, 2, NodPattern.SLIGHT),
                    new DiscSegment(60, 7, NodPattern.SLIGHT),
                    new DiscSegment(120, 1, NodPattern.SLIGHT),
                    new DiscSegment(120, 28, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(120, 1, NodPattern.NONE),
                    new DiscSegment(120, 4, NodPattern.SLIGHT),
                    new DiscSegment(60, 20, NodPattern.SLIGHT),
                    new DiscSegment(120, 4, NodPattern.NONE),
                    new DiscSegment(120, 2, NodPattern.SLIGHT),
                    new DiscSegment(60, 5, NodPattern.SLIGHT),
                    new DiscSegment(120, 5, NodPattern.NONE),
                    new DiscSegment(120, 2, NodPattern.SLIGHT),
                    new DiscSegment(120, 60, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(120, 1, NodPattern.NONE),
                    new DiscSegment(120, 4, NodPattern.SLIGHT),
                    new DiscSegment(60, 5, NodPattern.SLIGHT),
                    new DiscSegment(120, 3, NodPattern.NONE),
                    new DiscSegment(120, 3, NodPattern.SLIGHT),
                    new DiscSegment(60, 4, NodPattern.SLIGHT),
                    new DiscSegment(120, 5, NodPattern.NONE),
                    new DiscSegment(120, 2, NodPattern.SLIGHT),
                    new DiscSegment(120, 30, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(120, 2, NodPattern.SLIGHT),
                    new DiscSegment(120, 75),
                    new DiscSegment(240, 1, NodPattern.NONE),
                    new DiscSegment(240, 1, NodPattern.SLIGHT),
                    new DiscSegment(240, 1),
                    new DiscSegment(240, 1, NodPattern.NONE),
                    new DiscSegment(120, 3, NodPattern.NONE),
                    new DiscSegment(120, 1, NodPattern.SLIGHT),
                    new DiscSegment(120, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("dwdsb:rave", DISC_RAVE_SEGMENTS, 15.1));

            List<DiscSegment> DISC_WALTZ_SEGMENTS = List.of(
                    new DiscSegment(71, 28, NodPattern.SLIGHT),
                    new DiscSegment(71, 16, NodPattern.NONE),
                    new DiscSegment(71, 10, NodPattern.SLIGHT),
                    new DiscSegment(76, 4, NodPattern.NONE),
                    new DiscSegment(76, 1),
                    new DiscSegment(76, 12, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(76, 2, NodPattern.SLIGHT),
                    new DiscSegment(76, 38, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(76, 2, NodPattern.SLIGHT),
                    new DiscSegment(76, 8, NodPattern.DOWNBEAT4),
                    new DiscSegment(76, 20, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                    new DiscSegment(76, 11, NodPattern.SLIGHT),
                    new DiscSegment(76, 1, NodPattern.NONE),
                    new DiscSegment(76, 41),
                    new DiscSegment(76, 3, NodPattern.SLIGHT),
                    new DiscSegment(62, 2, NodPattern.SLIGHT),
                    new DiscSegment(62, 1, NodPattern.NONE),
                    new DiscSegment(62, 1, NodPattern.SLIGHT),
                    new DiscSegment(76, -1, NodPattern.NONE)
            );
            discManager.addDisc(new Disc("dwdsb:waltz", DISC_WALTZ_SEGMENTS, 0.11));

            List<DiscSegment> DISC_SUNSET_SEGMENTS = List.of(
                    new DiscSegment(18.8, -1, NodPattern.NONE) // pleasant listening
            );
            discManager.addDisc(new Disc("dwdsb:sunset", DISC_SUNSET_SEGMENTS, 0.08));

        }

    }

}
