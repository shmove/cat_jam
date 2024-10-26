package com.shmove.cat_jam.mixin;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.discs.Disc;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.packet.s2c.play.WorldEventS2CPacket;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements TickablePacketListener, ClientPlayPacketListener {

    @Shadow public abstract DynamicRegistryManager.Immutable getRegistryManager();

    @Inject(method = "onWorldEvent(Lnet/minecraft/network/packet/s2c/play/WorldEventS2CPacket;)V", at = @At("TAIL"))
    public void onNetworkJukeboxWorldEvent(WorldEventS2CPacket packet, CallbackInfo ci) {

        final BlockPos pos = packet.getPos();

        // Additional behaviour when jukebox disc is inserted or ejected
        if (packet.getEventId() == WorldEvents.JUKEBOX_STARTS_PLAYING) {
            try {
                final Registry<JukeboxSong> jukeboxSongRegistry = this.getRegistryManager().getOptional(RegistryKeys.JUKEBOX_SONG).orElseThrow(() -> new RuntimeException("Failed to get jukebox song registry"));
                final JukeboxSong song = jukeboxSongRegistry.get(packet.getData());
                if (song == null) throw new RuntimeException("Failed to discern sound event value " + packet.getData());

                final String discID = song.soundEvent().getIdAsString();
                final Disc disc = cat_jam.discManager.getDisc(discID);
                cat_jam.addMusicSource(pos, disc);
            } catch (RuntimeException ex) {
                cat_jam.LOGGER.error("Couldn't handle world event with jukebox at (" + packet.getPos().toShortString() + ") with error; " + ex.getMessage());
            }
        }
        else if (packet.getEventId() == WorldEvents.JUKEBOX_STOPS_PLAYING) {
            cat_jam.removeMusicSource(pos);
        }

    }

}
