package xyz.poketech.dyeityourself.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;
import xyz.poketech.dyeityourself.util.color.ColorUtil;
import xyz.poketech.dyeityourself.util.color.NBTColorUtil;

/**
 * Layer to override the sheep wool
 * Based on {@link net.minecraft.client.renderer.entity.layers.SheepFurLayer}
 */
public class SheepFurLayerOverride extends RenderLayer<Sheep, SheepModel<Sheep>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    private final SheepFurModel<Sheep> sheepModel;

    public SheepFurLayerOverride(SheepRenderer sheepRenderer, EntityModelSet modelSet) {
        super(sheepRenderer);
        this.sheepModel = new SheepFurModel<>(modelSet.bakeLayer(ModelLayers.SHEEP_FUR));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Sheep livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!livingEntity.isSheared() && !livingEntity.isInvisible()) {
            //Only render if a color is set
            if (livingEntity.getPersistentData().contains(NBTColorUtil.COLOR_KEY)) {
                if (livingEntity.hasCustomName() && "jeb_".equals(livingEntity.getName().getString())) {
                    return; //Don't render on top of jeb_ sheep
                }
                Vector3f color = ColorUtil.toFloat(livingEntity.getPersistentData().getInt(NBTColorUtil.COLOR_KEY));
                coloredCutoutModelCopyLayerRender(this.getParentModel(), this.sheepModel, TEXTURE, poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, color.x(), color.y(), color.z());
            }
        }
    }

}
