package com.shmove.cat_jam.forge.compat.alexsmobs;

import com.shmove.cat_jam.discs.Disc;
import com.shmove.cat_jam.discs.DiscSegment;
import com.shmove.cat_jam.discs.NodPattern;

import java.util.List;

import static com.shmove.cat_jam.cat_jam.discManager;

public final class AlexsMobs {

    public static final String MOD_ID = "alexsmobs";

    public static void initialiseDiscs()
    {
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

}
