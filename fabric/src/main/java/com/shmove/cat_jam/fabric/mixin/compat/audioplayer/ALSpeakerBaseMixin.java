package com.shmove.cat_jam.fabric.mixin.compat.audioplayer;

import com.shmove.cat_jam.fabric.compat.audioplayer.AudioPlayer;
import de.maxhenkel.voicechat.voice.client.speaker.ALSpeakerBase;
import de.maxhenkel.voicechat.voice.client.speaker.Speaker;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ALSpeakerBase.class) @Pseudo
public abstract class ALSpeakerBaseMixin implements Speaker {

    @Inject(method = "play([SFLnet/minecraft/util/math/Vec3d;Ljava/lang/String;F)V", at = @At("HEAD"))
    public void detectCustomDiscPlayback(short[] data, float volume, Vec3d position, String category, float maxDistance, CallbackInfo ci) {
        if (category.equals(AudioPlayer.DISC_SOUND_CATEGORY)) {
            AudioPlayer.pingPos(position);
        }
    }

}
