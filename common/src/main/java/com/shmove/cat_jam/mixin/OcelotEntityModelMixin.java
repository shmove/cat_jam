package com.shmove.cat_jam.mixin;

import com.shmove.cat_jam.access.JammingEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.OcelotEntityModel;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(OcelotEntityModel.class)
public abstract class OcelotEntityModelMixin<T extends Entity> extends AnimalModel<T> implements JammingEntityModel {

    @Shadow
    protected ModelPart head;

    @Override
    public ModelPart cat_jam$getHead() {
        return head;
    }

    @Override
    public float cat_jam$getInitialHeadPivotY() {
        return head.pivotY; // Cat head pivots are reset every frame
    }

}
