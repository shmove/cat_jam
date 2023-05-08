package com.shmove.cat_jam;

import com.shmove.cat_jam.records.Disc;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DiscManager {

    private final List<Disc> discs;

    public DiscManager() {
        discs = new ArrayList<Disc>();
    }

    public List<Disc> getDiscs() { return discs; }

    @Nullable
    public Disc getDisc(String id) {
        for (Disc d : discs) {
            if (d.id().equals(id)) {
                return d;
            }
        }
        return null;
    }

    public void addDisc(Disc disc) {
        discs.add(disc);
    }

}
