package com.shmove.cat_jam.mixin;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.helpers.CatEntityMixinAccess;
import com.shmove.cat_jam.helpers.discs.Disc;
import com.shmove.cat_jam.helpers.discs.DiscPlayback;
import com.shmove.cat_jam.helpers.discs.DiscSegment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatEntity.class)
public class CatEntityMixin implements CatEntityMixinAccess {

    @Unique
    private BlockPos jukebox = null;
    @Unique
    private boolean catJamming = false;
    @Unique
    private DiscPlayback discPlayback = null;

    @Unique private int nodTick = -1;
    @Unique private int slightNodTick = -1;

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void jamTick(CallbackInfo ci) {

        CatEntity meow = (CatEntity) (Object) this;

        // Ensure clientside
        if (!meow.getWorld().isClient) return;

        // Lose interest if out of range / jukebox is broken
        if (this.jukebox == null || !this.jukebox.isWithinDistance(meow.getPos(), cat_jam.JAM_RADIUS) || !meow.getWorld().getBlockState(this.jukebox).getBlock().equals(Blocks.JUKEBOX))
            resetJammingInfo();

        if (catJamming) {
            updateNod();
            updateNodAnim();

            discPlayback.tick(); // tick disc playback
        }
    }

    // This would be cool, but sound appears to only be played on the server
    /*@Inject(method = "getAmbientSound()Lnet/minecraft/sound/SoundEvent;", at = @At("HEAD"), cancellable = true)
    private void preventOffBeatAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        CatEntity meow = (CatEntity) (Object) this;

        // Ensure clientside
        if (!meow.world.isClient) return;

        if (catJamming) {
            final boolean ON_BEAT = discPlayback.anticipateBeat(0);
            final boolean NODLESS_SEGMENT = discPlayback.getCurrentSegment().nodType() == DiscSegment.NodType.NONE;
            cat_jam.LOGGER.info("ON_BEAT: " + ON_BEAT + " | NODLESS_SEGMENT: " + NODLESS_SEGMENT);
            if (!ON_BEAT || NODLESS_SEGMENT) cir.setReturnValue(null);
        }
    }*/

    private void updateNod() {

        final boolean DISABLE_NOD = discPlayback.getCurrentSegment().nodType() == DiscSegment.NodType.NONE;

        final boolean SLIGHT_NODS_ONLY = discPlayback.getCurrentSegment().nodType() == DiscSegment.NodType.SLIGHT;

        final boolean DOWNBEAT_NODS = discPlayback.getCurrentSegment().nodType() == DiscSegment.NodType.SLIGHT_WITH_NORMAL_DOWNBEAT;
        final boolean IS_DOWNBEAT = discPlayback.getBeat() % 4 == 0; // assumes a time signature of 4/4

        final boolean FORCE_SLIGHT_NOD = SLIGHT_NODS_ONLY || DOWNBEAT_NODS && !IS_DOWNBEAT;
        final boolean HALF_BEAT_NOD = discPlayback.getCurrentSegment().nodType() == DiscSegment.NodType.NORMAL_WITH_SLIGHT_ON_HALF;

        final boolean NOD_NOT_STARTED = nodTick == -1;
        final boolean SLIGHT_NOD_NOT_STARTED = slightNodTick == -1;

        if (DISABLE_NOD) return;

        // start nod (early by two ticks)
        if (discPlayback.anticipateBeat(2) && !FORCE_SLIGHT_NOD) {
            if (NOD_NOT_STARTED) nodTick = 0;
        }

        // start slight nod (early by one tick)
        if (discPlayback.anticipateHalfBeat(1) && HALF_BEAT_NOD || discPlayback.anticipateBeat(1) && FORCE_SLIGHT_NOD) {
            if (SLIGHT_NOD_NOT_STARTED) slightNodTick = 0;
        }

    }

    private void updateNodAnim() {

        final int nodAnimTickLength = 8;
        final int slightNodAnimTickLength = 6;

        if (nodTick >= 0) {
            // sustain nod for N ticks, then finish on Nth
            if (nodTick < (nodAnimTickLength - 1)) nodTick++;
            else if (nodTick == (nodAnimTickLength - 1)) nodTick = -1;
        }

        if (slightNodTick >= 0) {
            // sustain slight nod for N ticks, then finish on Nth
            if (slightNodTick < (slightNodAnimTickLength - 1)) slightNodTick++;
            else if (slightNodTick == (slightNodAnimTickLength - 1)) slightNodTick = -1;
        }

    }

    public void resetJammingInfo() {
        this.jukebox = null;
        this.discPlayback = null;
        this.catJamming = false;

        this.nodTick = -1;
        this.slightNodTick = -1;
    }

    @Override
    public void setJammingInfo(BlockPos jukeboxPosition, @Nullable Disc disc) {

        if (disc != null && catJamming) return; // Don't override if already jamming
        if (jukebox != null && !jukebox.equals(jukeboxPosition)) return; // Don't override if already listening to another jukebox
        if (!catJamming && disc == null) return; // Don't do anything if not jamming and disc is null

        CatEntity meow = (CatEntity) (Object) this;

        // Ensure cat is tame
        if (!meow.isTamed()) return;

        if (disc != null) {

            if (disc.getSegment(0).bpm() == 0) return; // TODO: alternate anim for spooky discs??

            this.jukebox = jukeboxPosition;
            this.discPlayback = new DiscPlayback(disc);
            this.catJamming = true;
            meow.getWorld().addParticle(ParticleTypes.NOTE, meow.getX(), meow.getY() + 0.3, meow.getZ(), 0, 0, 0);

        } else {
            resetJammingInfo();
        }

    }

    @Override
    public int getNodTick() {
        return this.nodTick;
    }

    @Override
    public int getSlightNodTick() {
        return this.slightNodTick;
    }

}
