package com.shmove.cat_jam.helpers.discs;

public record DiscSegment(double bpm, int lengthInBeats, NodPattern nodPattern, double beatTickInterval) {

    public DiscSegment {
        if (bpm < 0) throw new IllegalArgumentException("Segment BPM must be positive");
        if (lengthInBeats <= 0 && lengthInBeats != -1) throw new IllegalArgumentException("Segment length must be positive or -1 (infinite)");
    }

    public DiscSegment(double bpm) { this(bpm, -1, NodPattern.NORMAL); } // infinite length basic segment constructor

    public DiscSegment(double bpm, int lengthInBeats) { this(bpm, lengthInBeats, NodPattern.NORMAL); }

    public DiscSegment(double bpm, int lengthInBeats, NodPattern nodPattern) {
        this(bpm, lengthInBeats, nodPattern, getBeatTickInterval(bpm));
    }

    private static double getBeatTickInterval(double bpm) {
        final double TICKS_IN_ONE_MINUTE = 1200D;
        if (bpm == 0) return -1D;
        return TICKS_IN_ONE_MINUTE / bpm;
    }

}
