package com.shmove.cat_jam.helpers.discs;

public class DiscPlayback {

    private final Disc disc;
    private int beat = 0;
    private int segment = 0;

    private int offsetTicks;
    private int ticksTilBeat = 0; // immediate beat

    private double tickError = 0;

    public DiscPlayback(Disc disc) {
        this.disc = disc;
        offsetTicks = (int) Math.floor(20 * disc.offsetInSeconds());
    }

    private void setTicksToNextBeat() {
        if (ticksTilBeat <= 0) {
            double v = disc.getSegment(segment).beatTickInterval();
            ticksTilBeat = (int) Math.floor(v);
            tickError += v - ticksTilBeat; // accumulate error
            while (tickError >= 0.5) {
                ticksTilBeat++; // maintain <0.5t de-sync
                tickError--;
            }
        }
    }

    public void tick() {

        // cat_jam.LOGGER.info("playback tick | offsetTicks: " + offsetTicks + "; ticksTilBeat: " + ticksTilBeat + "; beat: " + beat + "; nodType: " + disc.getSegment(segment).nodPattern().getNodType(beat) + "; segment: " + segment);

        if (offsetTicks > 0) {
            offsetTicks--;
            return;
        }

        if (ticksTilBeat <= 0) {
            beat++;
            if (beat >= disc.getSegment(segment).lengthInBeats() && disc.getSegment(segment).lengthInBeats() != -1) {
                beat = 0;
                segment++;
            }
            setTicksToNextBeat();
        }
        ticksTilBeat--;
    }

    public boolean anticipateBeat(int ticks) {
        if (offsetTicks > 0) return offsetTicks <= ticks;
        return ticksTilBeat <= ticks;
    }

    public boolean isNodBeat() {
        return disc.getSegment(segment).nodPattern().getNodType(beat) == NodPattern.NOD_NORMAL;
    }

    public boolean isSlightNodBeat() {
        return disc.getSegment(segment).nodPattern().getNodType(beat) == NodPattern.NOD_SLIGHT;
    }

    public boolean isPauseBeat() {
        return disc.getSegment(segment).nodPattern().getNodType(beat) == NodPattern.NOD_NONE;
    }

}
