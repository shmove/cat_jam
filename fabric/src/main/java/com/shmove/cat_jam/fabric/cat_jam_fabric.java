package com.shmove.cat_jam.fabric;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.fabric.compat.FabricMods;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import static com.shmove.cat_jam.cat_jam.discManager;

public class cat_jam_fabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        cat_jam.init();

        for (FabricMods mod : FabricMods.values()) mod.runIfInstalled(mod::initialiseCompatibility); // Initialise mod compatibility

        ClientTickEvents.END_WORLD_TICK.register(cat_jam::tickPlayingDiscs);
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> cat_jam.clearPlayingDiscs());

        for (FabricMods mod : FabricMods.values()) mod.runIfInstalled(mod::initialiseDiscs); // Initialise modded discs

        cat_jam.LOGGER.info("cat_jam successfully initialised! (" + discManager.getDiscCount() + " customised jams loaded)");
    }

}
