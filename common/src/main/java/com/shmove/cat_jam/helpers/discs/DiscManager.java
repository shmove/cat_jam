package com.shmove.cat_jam.helpers.discs;

import com.shmove.cat_jam.cat_jam;

import java.util.ArrayList;
import java.util.List;

public class DiscManager {

    public final static int DEFAULT_BPM = 60;
    public final static int DEFAULT_OFFSET = 0;

    private final List<Disc> discs;

    public DiscManager() {
        discs = new ArrayList<>();
    }

    public void addDisc(Disc disc) {
        discs.add(disc);
    }

    /**
     * Get a disc by its id
     * @param id The id of the disc
     * @return The disc, or a default disc if the disc is not found
     */
    public Disc getDisc(String id) {
        for (Disc d : discs) {
            if (d.id().equals(id)) {
                return d;
            }
        }
        cat_jam.LOGGER.warn("Playing unknown disc '" + id + "' using default BPM and offset");
        return new Disc(id, DEFAULT_BPM, DEFAULT_OFFSET);
    }

}
