package com.shmove.cat_jam.mixin;

import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.helpers.JammingEntity;
import com.shmove.cat_jam.helpers.discs.DiscPlayback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatEntity.class)
public class CatEntityMixin implements JammingEntity {

    @Unique
    private BlockPos musicSourceBlock = null;
    @Unique
    private Entity musicSourceEntity = null;
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

        if (this.musicSourceBlock != null) {
            // Lose interest if out of range / playback no longer being ticked
            if (!this.musicSourceBlock.isWithinDistance(meow.getPos(), cat_jam.JAM_RADIUS) || !cat_jam.isSourcePlayingAtPos(this.musicSourceBlock))
                resetJammingInfo();
        }

        if (this.musicSourceEntity != null) {
            // Lose interest if out of range / entity is dead / playback no longer being ticked
            if (!this.musicSourceEntity.isInRange(meow, cat_jam.JAM_RADIUS) || !this.musicSourceEntity.isAlive() || !cat_jam.isSourcePlayingFromEntity(this.musicSourceEntity))
                resetJammingInfo();
        }

        // If not jamming, try to find a new music source
        if (!catJamming)
            findNewMusicSource();

        if (catJamming) {
            updateNod();
            updateNodAnim();
        }
    }

    @Override
    public void resetJammingInfo() {
        this.musicSourceBlock = null;
        this.musicSourceEntity = null;
        this.discPlayback = null;
        this.catJamming = false;

        this.nodTick = -1;
        this.slightNodTick = -1;
    }

    private void findNewMusicSource() {
        CatEntity meow = (CatEntity) (Object) this;

        BlockPos nearbyBlockSource = cat_jam.getClosestListenableSourcePos(meow.getPos());
        Entity nearbyEntitySource = cat_jam.getClosestListenableSourceEntity(meow.getPos());

        final boolean FOUND_BLOCK_SOURCE = nearbyBlockSource != null;
        final boolean FOUND_ENTITY_SOURCE = nearbyEntitySource != null;

        if (!FOUND_BLOCK_SOURCE && !FOUND_ENTITY_SOURCE) return;

        if (FOUND_BLOCK_SOURCE && FOUND_ENTITY_SOURCE) {
            // Set to closer source
            double blockDist = nearbyBlockSource.getSquaredDistance(meow.getPos());
            double entityDist = nearbyEntitySource.squaredDistanceTo(meow);
            if (blockDist < entityDist) {
                updateMusicSource(nearbyBlockSource);
            }
            else {
                updateMusicSource(nearbyEntitySource);
            }
        } else if (FOUND_BLOCK_SOURCE) {
            updateMusicSource(nearbyBlockSource);
        } else if (FOUND_ENTITY_SOURCE) {
            updateMusicSource(nearbyEntitySource);
        }

    }

    @Override
    public void updateMusicSource(BlockPos sourcePos) {
        CatEntity meow = (CatEntity) (Object) this;

        // Ensure cat is tame
        if (!meow.isTamed()) return;

        this.musicSourceBlock = sourcePos;
        this.discPlayback = cat_jam.getDiscPlaybackAtPos(sourcePos);
        this.catJamming = true;
        meow.getWorld().addParticle(ParticleTypes.NOTE, meow.getX(), meow.getY() + 0.3, meow.getZ(), 0, 0, 0);
    }

    @Override
    public void updateMusicSource(Entity sourceEntity) {
        CatEntity meow = (CatEntity) (Object) this;

        // Ensure cat is tame
        if (!meow.isTamed()) return;

        this.musicSourceEntity = sourceEntity;
        this.discPlayback = cat_jam.getDiscPlaybackFromEntity(sourceEntity);
        this.catJamming = true;
        meow.getWorld().addParticle(ParticleTypes.NOTE, meow.getX(), meow.getY() + 0.3, meow.getZ(), 0, 0, 0);
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

        if (discPlayback.anticipateBeat(2) && discPlayback.isNodBeat()) {
            if (nodTick >= 0) return;
            nodTick = 0;
        }
        else if (discPlayback.anticipateBeat(1) && discPlayback.isSlightNodBeat()) {
            if (slightNodTick >= 0) return;
            slightNodTick = 0;
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

    @Override
    public int getNodTick() {
        return this.nodTick;
    }

    @Override
    public int getSlightNodTick() {
        return this.slightNodTick;
    }

}
