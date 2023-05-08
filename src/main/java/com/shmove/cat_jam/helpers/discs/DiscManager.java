package com.shmove.cat_jam.helpers.discs;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DiscManager {

    private final List<Disc> discs;

    public DiscManager() {
        discs = new ArrayList<>();
    }

    public void addDisc(Disc disc) {
        discs.add(disc);
    }

    @Nullable
    public Disc getDisc(String id) {
        for (Disc d : discs) {
            if (d.id().equals(id)) {
                return d;
            }
        }
        return null;
    }

}
