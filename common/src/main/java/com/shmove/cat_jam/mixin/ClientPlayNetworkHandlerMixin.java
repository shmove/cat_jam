package com.shmove.cat_jam.mixin;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.helpers.discs.Disc;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.packet.s2c.play.WorldEventS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements TickablePacketListener, ClientPlayPacketListener {

    @Shadow
    public abstract ClientWorld getWorld();

    @Inject(method = "onWorldEvent(Lnet/minecraft/network/packet/s2c/play/WorldEventS2CPacket;)V", at = @At("TAIL"))
    public void onNetworkJukeboxWorldEvent(WorldEventS2CPacket packet, CallbackInfo ci) {

        final World world = getWorld();
        final BlockPos pos = packet.getPos();

        // Fires custom event when jukebox disc is inserted or ejected
        final int DISC_INSERT_EVENT_ID = 1010;
        final int DISC_EJECT_EVENT_ID = 1011;

        if (packet.getEventId() == DISC_INSERT_EVENT_ID) {
            final String discID = Registries.ITEM.getId(Registries.ITEM.get(packet.getData())).toString();
            final Disc disc = cat_jam.discManager.getDisc(discID);
            cat_jam.jukeboxDiscUpdateEvent(world, pos, disc);
        }
        else if (packet.getEventId() == DISC_EJECT_EVENT_ID) {
            cat_jam.jukeboxDiscUpdateEvent(world, pos, null);
        }

    }

}
