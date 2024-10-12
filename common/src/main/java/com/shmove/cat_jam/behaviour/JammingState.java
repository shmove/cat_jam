package com.shmove.cat_jam.behaviour;

import com.shmove.cat_jam.access.JammingEntity;
import com.shmove.cat_jam.cat_jam;
import com.shmove.cat_jam.discs.DiscPlayback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;

public class JammingState {

    private final LivingEntity jammingEntity;
    private BlockPos musicSourceBlock = null;
    private Integer musicSourceEntityID = null;
    private boolean catJamming = false;
    private DiscPlayback discPlayback = null;
    private int nodTick = -1;
    private int slightNodTick = -1;

    public JammingState(LivingEntity jammingEntity) {
        this.jammingEntity = jammingEntity;
    }

    public void tick() {

        // Ensure clientside
        if (!jammingEntity.getWorld().isClient) return;

        if (this.musicSourceBlock != null) {
            // Lose interest if out of range / playback no longer being ticked
            if (!this.musicSourceBlock.isWithinDistance(jammingEntity.getPos(), cat_jam.JAM_RADIUS) || !cat_jam.isSourcePlayingAtPos(this.musicSourceBlock))
                resetJammingInfo();
        }

        if (this.musicSourceEntityID != null) {
            // Lose interest if out of range / entity is dead / playback no longer being ticked
            Entity musicSourceEntity = jammingEntity.getWorld().getEntityById(this.musicSourceEntityID);
            if (musicSourceEntity == null || !musicSourceEntity.isInRange(jammingEntity, cat_jam.JAM_RADIUS) || !musicSourceEntity.isAlive() || !cat_jam.isSourcePlayingFromEntity(this.musicSourceEntityID))
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

    public void resetJammingInfo() {
        this.musicSourceBlock = null;
        this.musicSourceEntityID = null;
        this.discPlayback = null;
        this.catJamming = false;

        this.nodTick = -1;
        this.slightNodTick = -1;
    }

    public int getNodTick() {
        return this.nodTick;
    }

    public int getSlightNodTick() {
        return this.slightNodTick;
    }

    public void updateMusicSource(BlockPos sourcePos) {
        // Ensure entity is valid (e.g. tamed)
        if (!((JammingEntity) jammingEntity).cat_jam$isInValidStateToJam()) return;

        this.musicSourceBlock = sourcePos;
        this.discPlayback = cat_jam.getDiscPlaybackAtPos(sourcePos);
        this.catJamming = true;
        jammingEntity.getWorld().addParticle(ParticleTypes.NOTE, jammingEntity.getX(), jammingEntity.getY() + 0.3, jammingEntity.getZ(), 0, 0, 0);
    }

    public void updateMusicSource(Integer sourceEntityID) {
        // Ensure entity is valid (e.g. tamed)
        if (!((JammingEntity) jammingEntity).cat_jam$isInValidStateToJam()) return;

        this.musicSourceEntityID = sourceEntityID;
        this.discPlayback = cat_jam.getDiscPlaybackFromEntity(sourceEntityID);
        this.catJamming = true;
        jammingEntity.getWorld().addParticle(ParticleTypes.NOTE, jammingEntity.getX(), jammingEntity.getY() + 0.3, jammingEntity.getZ(), 0, 0, 0);
    }

    private void findNewMusicSource() {

        BlockPos nearbyBlockSource = cat_jam.getClosestListenableSourcePos(jammingEntity.getPos());
        Entity nearbyEntitySource = cat_jam.getClosestListenableSourceEntity(jammingEntity.getPos());

        final boolean FOUND_BLOCK_SOURCE = nearbyBlockSource != null;
        final boolean FOUND_ENTITY_SOURCE = nearbyEntitySource != null;

        if (!FOUND_BLOCK_SOURCE && !FOUND_ENTITY_SOURCE) return;

        if (FOUND_BLOCK_SOURCE && FOUND_ENTITY_SOURCE) {
            // Set to closer source
            double blockDist = nearbyBlockSource.getSquaredDistance(jammingEntity.getPos());
            double entityDist = nearbyEntitySource.squaredDistanceTo(jammingEntity);
            if (blockDist < entityDist) {
                updateMusicSource(nearbyBlockSource);
            }
            else {
                updateMusicSource(nearbyEntitySource.getId());
            }
        } else if (FOUND_BLOCK_SOURCE) {
            updateMusicSource(nearbyBlockSource);
        } else if (FOUND_ENTITY_SOURCE) {
            updateMusicSource(nearbyEntitySource.getId());
        }

    }

    private void updateNod() {

        final int nodPreempt = 2;
        final int slightNodPreempt = 1;

        if (discPlayback.anticipateBeat(nodPreempt) && discPlayback.isNodBeat()) {
            if (nodTick >= 0 && nodTick <= nodPreempt) return;
            nodTick = 0;
        }
        else if (discPlayback.anticipateBeat(slightNodPreempt) && discPlayback.isSlightNodBeat()) {
            if (slightNodTick >= 0 && slightNodTick <= slightNodPreempt) return;
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

}
