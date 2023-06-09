package com.shmove.cat_jam.helpers.discs;

public record DiscSegment(double bpm, int lengthInBeats, NodType nodType, int beatsPerBar, double beatTickInterval) {

    public enum NodType {
        NONE,
        NORMAL,
        SLIGHT,
        NORMAL_WITH_SLIGHT_ON_HALF,
        SLIGHT_WITH_NORMAL_DOWNBEAT
    }

    public DiscSegment {
        if (bpm < 0) throw new IllegalArgumentException("Segment BPM must be positive");
        if (lengthInBeats <= 0 && lengthInBeats != -1) throw new IllegalArgumentException("Segment length must be positive or -1 (infinite)");
    }

    public DiscSegment(double bpm) { this(bpm, -1, NodType.NORMAL); } // infinite length basic segment constructor

    public DiscSegment(double bpm, int lengthInBeats) { this(bpm, lengthInBeats, NodType.NORMAL); }

    public DiscSegment(double bpm, int lengthInBeats, NodType nodType) {
        this(bpm, lengthInBeats, nodType, 4); // default 4/4
    }

    public DiscSegment(double bpm, int lengthInBeats, NodType nodType, int beatsPerBar) {
        this(bpm, lengthInBeats, nodType, beatsPerBar, getBeatTickInterval(bpm));
    }

    private static double getBeatTickInterval(double bpm) {
        final double TICKS_IN_ONE_MINUTE = 1200D;
        if (bpm == 0) return -1D;
        return TICKS_IN_ONE_MINUTE / bpm;
    }

}
