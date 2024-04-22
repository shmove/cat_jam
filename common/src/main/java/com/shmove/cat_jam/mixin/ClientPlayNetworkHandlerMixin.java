package com.shmove.cat_jam.mixin;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.discs.Disc;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.WorldEventS2CPacket;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements ClientPlayPacketListener {

    @Inject(method = "onWorldEvent(Lnet/minecraft/network/packet/s2c/play/WorldEventS2CPacket;)V", at = @At("TAIL"))
    public void onNetworkJukeboxWorldEvent(WorldEventS2CPacket packet, CallbackInfo ci) {

        final BlockPos pos = packet.getPos();

        // Fires custom event when jukebox disc is inserted or ejected
        final int DISC_EVENT_ID = 1010; // WorldEvents.MUSIC_DISC_PLAYED;

        // prior to 1.19.4, DISC_EVENT_ID event is fired on both eject (with 0 [air]) and insert (with disc item).
        if (packet.getEventId() != DISC_EVENT_ID) return;

        if (packet.getData() != 0) {
            final String discID = Registry.ITEM.getId(Registry.ITEM.get(packet.getData())).toString();
            final Disc disc = cat_jam.discManager.getDisc(discID);
            cat_jam.addMusicSource(pos, disc);
        } else {
            cat_jam.removeMusicSource(pos);
        }

    }

}
