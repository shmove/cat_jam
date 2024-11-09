package com.shmove.cat_jam.compat;

public interface Mods {

    boolean isInstalled();

    default void runIfInstalled(Runnable fn) {
        if (isInstalled()) fn.run();
    };

    void initialiseCompatibility();

    void initialiseDiscs();

}
