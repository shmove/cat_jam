package com.shmove.cat_jam.neoforge;

import com.shmove.cat_jam.cat_jam;
import net.minecraft.client.MinecraftClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import static com.shmove.cat_jam.cat_jam.discManager;

@Mod(cat_jam.MOD_ID)
@EventBusSubscriber(Dist.CLIENT)
public final class cat_jam_neoforge {

    public cat_jam_neoforge() {
        cat_jam.init();

        initialiseModdedDiscs();

        cat_jam.LOGGER.info("cat_jam successfully initialised! (" + discManager.getDiscCount() + " customised jams loaded)");
    }

    @SubscribeEvent
    public static void onClientWorldTickEnd(LevelTickEvent.Post event) {
        if (event.getLevel().isClient)
            cat_jam.tickPlayingDiscs(MinecraftClient.getInstance().world);
    }

    private void initialiseModdedDiscs() {

        // Mod List

        /* final boolean example_mod = ModList.get().isLoaded("example_mod"); */

        // Modded Discs

        /* if (example_mod) discManager.addDisc(new Disc("example_mod:example_disc", 0, 0)); */

    }

}
