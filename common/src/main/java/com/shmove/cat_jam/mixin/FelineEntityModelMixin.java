package com.shmove.cat_jam.mixin;

import com.shmove.cat_jam.access.JammingEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.FelineEntityModel;
import net.minecraft.client.render.entity.state.FelineEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FelineEntityModel.class)
public abstract class FelineEntityModelMixin<T extends FelineEntityRenderState> extends EntityModel<T> implements JammingEntityModel {

    @Shadow
    protected ModelPart head;

    protected FelineEntityModelMixin(ModelPart root) {
        super(root);
    }

    @Override
    public ModelPart cat_jam$getHead() {
        return head;
    }

    @Override
    public float cat_jam$getInitialHeadPivotY() {
        return head.pivotY; // Cat head pivots are reset every frame
    }

}
