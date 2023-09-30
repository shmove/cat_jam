package com.shmove.cat_jam.forge;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.helpers.discs.Disc;
import com.shmove.cat_jam.helpers.discs.DiscSegment;
import net.minecraft.client.MinecraftClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.shmove.cat_jam.cat_jam.discManager;

@Mod(cat_jam.MOD_ID)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class cat_jam_forge {

    public cat_jam_forge() {
        cat_jam.init();

        initialiseModdedDiscs();
    }

    @SubscribeEvent
    public static void onClientWorldTickEnd(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.CLIENT && event.phase == TickEvent.Phase.END)
            cat_jam.tickPlayingDiscs(MinecraftClient.getInstance().world);
    }

    private void initialiseModdedDiscs() {

        // Mod List

        /* final boolean example_mod = ModList.get().isLoaded("example_mod"); */

        // Modded Discs

        /* if (example_mod) discManager.addDisc(new Disc("example_mod:example_disc", 0, 0)); */

    }

}
