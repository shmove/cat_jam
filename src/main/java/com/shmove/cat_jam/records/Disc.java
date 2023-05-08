package com.shmove.cat_jam.records;

import java.util.List;

public record Disc(String id, List<DiscSegment> segments, double offsetInSeconds) {

    public Disc {
        if (segments == null || segments.isEmpty()) throw new IllegalArgumentException("Disc " + id + " has no segments");
        if (offsetInSeconds < 0) throw new IllegalArgumentException("Disc " + id + " has negative offset");
    }

    // infinite length single segment disc constructor
    public Disc(String id, double bpm, double offsetInSeconds) { this(id, List.of(new DiscSegment(bpm)), offsetInSeconds); }

    public DiscSegment getSegment(int index) {
        if (index < 0 || index >= segments.size()) throw new IndexOutOfBoundsException("Segment index " + index + " out of bounds for disc " + id);
        return segments.get(index);
    }

}
