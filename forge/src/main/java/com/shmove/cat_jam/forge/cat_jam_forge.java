package com.shmove.cat_jam.forge;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.discs.Disc;
import com.shmove.cat_jam.discs.DiscSegment;
import com.shmove.cat_jam.discs.NodPattern;
import com.shmove.cat_jam.forge.compat.ForgeMods;
import net.minecraft.client.MinecraftClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.shmove.cat_jam.cat_jam.discManager;

@Mod(cat_jam.MOD_ID)
@Mod.EventBusSubscriber(Dist.CLIENT)
public final class cat_jam_forge {

    public cat_jam_forge() {
        cat_jam.init();

        for (ForgeMods mod : ForgeMods.values()) mod.runIfInstalled(mod::initialiseCompatibility); // Initialise mod compatibility
        for (ForgeMods mod : ForgeMods.values()) mod.runIfInstalled(mod::initialiseDiscs); // Initialise modded discs

        cat_jam.LOGGER.info("cat_jam successfully initialised! (" + discManager.getDiscCount() + " customised jams loaded)");
    }

    @SubscribeEvent
    public static void onServerTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            cat_jam.tickPlayingDiscs(MinecraftClient.getInstance().world);
    }

}
