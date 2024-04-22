package com.shmove.cat_jam.compat.supplementaries;

import com.shmove.cat_jam.discs.Disc;
import com.shmove.cat_jam.discs.DiscSegment;
import com.shmove.cat_jam.discs.NodPattern;

import java.util.List;

import static com.shmove.cat_jam.cat_jam.discManager;

public final class Supplementaries {

    public static final String MOD_ID = "supplementaries";

    public static void initialiseDiscs()
    {
        final List<DiscSegment> PANCAKE_SEGMENTS = List.of(
                new DiscSegment(105, 16, NodPattern.SLIGHT),
                new DiscSegment(105, 16, NodPattern.DOWNBEAT8),
                new DiscSegment(105, 32, NodPattern.DOWNBEAT4),
                new DiscSegment(105, 32, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                new DiscSegment(105, 32, NodPattern.NORMAL),
                new DiscSegment(105, 32, NodPattern.DOWNBEAT4),
                new DiscSegment(105, 16, NodPattern.NONE),
                new DiscSegment(105, 1, NodPattern.SLIGHT),
                new DiscSegment(105, 7, NodPattern.NONE),
                new DiscSegment(210, 2, new NodPattern("x_")), // silly off beat
                new DiscSegment(105, 7, NodPattern.NONE),
                new DiscSegment(105, 16, new NodPattern("x_")),
                new DiscSegment(105, 16, NodPattern.SLIGHT),
                new DiscSegment(105, 16, NodPattern.DOWNBEAT4),
                new DiscSegment(105, 16, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                new DiscSegment(105, 48, NodPattern.NORMAL),
                new DiscSegment(105, 32, NodPattern.DOWNBEAT8),
                new DiscSegment(105, 32, new NodPattern("x_")),
                new DiscSegment(105, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("supplementaries:pancake_disc", PANCAKE_SEGMENTS, 13.635));

        final List<DiscSegment> DISC_HEAVE_HO_SEGMENTS = List.of(
                new DiscSegment(120, 1, NodPattern.SLIGHT),
                new DiscSegment(120, 2, NodPattern.NONE),
                new DiscSegment(120, 1, NodPattern.SLIGHT),
                new DiscSegment(120, 6, new NodPattern("x_")),
                new DiscSegment(120, 2, NodPattern.NONE),
                new DiscSegment(120, 10, new NodPattern("x_")),
                new DiscSegment(120 * 3, 4, NodPattern.NONE), // offset
                new DiscSegment(150 * 6, 5, NodPattern.NONE), // offset
                new DiscSegment(150, 4, NodPattern.NONE),
                new DiscSegment(150, 4, new NodPattern("x_")),
                new DiscSegment(150, 22, NodPattern.SLIGHT),
                new DiscSegment(150 * 2, 6, new NodPattern("x_xx_X")),
                new DiscSegment(150, 15, NodPattern.NORMAL),
                new DiscSegment(150, 4, NodPattern.SLIGHT),
                new DiscSegment(150, 4, NodPattern.NORMAL),
                new DiscSegment(150, 5, new NodPattern("X_")),
                new DiscSegment(150 * 2, 4, new NodPattern("__X_")),
                new DiscSegment(150, 1, NodPattern.NONE),
                new DiscSegment(150, 4, new NodPattern("X_")),
                new DiscSegment(150, 12, NodPattern.NORMAL),
                new DiscSegment(150, 4, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                new DiscSegment(150, 8, NodPattern.SLIGHT),
                new DiscSegment(150, 24, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                new DiscSegment(150, 7, NodPattern.NORMAL),
                new DiscSegment(150 * 2, 2, NodPattern.SLIGHT_NORMAL_ALTERNATING),
                new DiscSegment(150, 47, NodPattern.NORMAL),
                new DiscSegment(150, 2, NodPattern.NONE),
                new DiscSegment(150, 1, NodPattern.SLIGHT),
                new DiscSegment(150, 56, NodPattern.NORMAL),
                new DiscSegment(150, 6, new NodPattern("X_")),
                new DiscSegment(150 * 2, 2, new NodPattern("X_")),
                new DiscSegment(150, 1, NodPattern.NONE),
                new DiscSegment(150, 4, new NodPattern("X_")),
                new DiscSegment(150, 3, NodPattern.NORMAL_SLIGHT_ALTERNATING),
                new DiscSegment(150, 1, NodPattern.NONE),
                new DiscSegment(150, 40, NodPattern.NORMAL),
                new DiscSegment(150, 5, new NodPattern("X_")),
                new DiscSegment(150, 9, NodPattern.NONE),
                new DiscSegment(150, 1, NodPattern.SLIGHT),
                new DiscSegment(120, 7, new NodPattern("__xx___")),
                new DiscSegment(120, 14, new NodPattern("x_")),
                new DiscSegment(120, 1, NodPattern.NORMAL),
                new DiscSegment(120, -1, NodPattern.NONE)
        );
        discManager.addDisc(new Disc("supplementaries:music_disc_heave_ho", DISC_HEAVE_HO_SEGMENTS, 0));
    }

}
