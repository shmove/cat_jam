package com.shmove.cat_jam.helpers.discs;

public class NodPattern {

    private static final String VALID_NOD_TYPES = "_Xx";
    public static final char NOD_NONE = '_';
    public static final char NOD_NORMAL = 'X';
    public static final char NOD_SLIGHT = 'x';

    public static final NodPattern NONE = new NodPattern("_");
    public static final NodPattern NORMAL = new NodPattern("X");
    public static final NodPattern SLIGHT = new NodPattern("x");
    public static final NodPattern SLIGHT_NORMAL_ALTERNATING = new NodPattern("xX");
    public static final NodPattern DOWNBEAT4 = new NodPattern("Xxxx");
    public static final NodPattern DOWNBEAT3 = new NodPattern("Xxx");

    String pattern;

    public NodPattern(String pattern) {
        if (!isValidNodPattern(pattern)) throw new IllegalArgumentException("Invalid nod pattern");
        this.pattern = pattern;
    }

    public char getNodType(int beat) {
        return pattern.charAt(beat % pattern.length());
    }

    private static boolean isValidNodPattern(String pattern) {
        if (pattern.length() == 0) return false;
        for (char c : pattern.toCharArray()) {
            if (!VALID_NOD_TYPES.contains(String.valueOf(c))) return false;
        }
        return true;
    }

}
