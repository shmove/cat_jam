package com.shmove.cat_jam.helpers.discs;

public class DiscPlayback {

    private final Disc disc;
    private int beat = 0;
    private int segment = 0;

    private int offsetTicks;
    private int ticksTilBeat = 0; // immediate beat
    private int ticksTilHalfBeat = -1;

    private double tickError = 0;

    public DiscPlayback(Disc disc) {
        this.disc = disc;
        offsetTicks = (int) Math.floor(20 * disc.offsetInSeconds());
    }

    private void setTicksToNextBeat() {
        if (ticksTilBeat <= 0) {
            double v = disc.getSegment(segment).beatTickInterval();
            ticksTilBeat = (int) Math.floor(v);
            ticksTilHalfBeat = (int) Math.floor(v / 2);
            tickError += v - ticksTilBeat; // accumulate error
            while (tickError >= 0.5) {
                ticksTilBeat++; ticksTilHalfBeat++; // maintain <0.5t de-sync
                tickError--;
            }
        }
    }

    public void tick() {

        //cat_jam.LOGGER.info("playback tick | offsetTicks: " + offsetTicks + "; ticksTilBeat: " + ticksTilBeat + "; ticksTilHalfBeat: " + ticksTilHalfBeat + "; beat: " + beat + "; segment: " + segment);

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
        ticksTilHalfBeat--;
    }

    public boolean anticipateBeat(int ticks) {
        if (offsetTicks > 0) return offsetTicks <= ticks;
        return ticksTilBeat <= ticks;
    }

    public boolean anticipateHalfBeat(int ticks) {
        if (offsetTicks > 0) return false;
        if (ticksTilHalfBeat < 0) return false;
        return ticksTilHalfBeat <= ticks;
    }

    public int getBeat() {
        return beat;
    }

    public DiscSegment getCurrentSegment() {
        return disc.getSegment(segment);
    }

}
