package com.shmove.cat_jam.neoforge.compat.quark;

import com.shmove.cat_jam.discs.Disc;
import com.shmove.cat_jam.discs.DiscSegment;
import com.shmove.cat_jam.discs.NodPattern;

import java.util.List;

import static com.shmove.cat_jam.cat_jam.discManager;

public final class Quark {

    public static final String MOD_ID = "quark";

    public static void initialiseDiscs()
    {
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
