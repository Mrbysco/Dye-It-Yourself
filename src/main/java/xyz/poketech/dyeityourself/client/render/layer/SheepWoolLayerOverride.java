package xyz.poketech.dyeityourself.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.animal.sheep.SheepFurModel;
import net.minecraft.client.model.animal.sheep.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.SheepRenderState;
import net.minecraft.resources.Identifier;
import xyz.poketech.dyeityourself.client.ClientHandler;

/**
 * Layer to override the sheep wool
 * Based on {@link net.minecraft.client.renderer.entity.layers.SheepWoolLayer}
 */
public class SheepWoolLayerOverride extends RenderLayer<SheepRenderState, SheepModel> {

    private static final Identifier SHEEP_WOOL_LOCATION = Identifier.withDefaultNamespace("textures/entity/sheep/sheep_wool.png");
    private final EntityModel<SheepRenderState> adultModel;
    private final EntityModel<SheepRenderState> babyModel;

    public SheepWoolLayerOverride(SheepRenderer sheepRenderer, EntityModelSet modelSet) {
        super(sheepRenderer);
        this.adultModel = new SheepFurModel(modelSet.bakeLayer(ModelLayers.SHEEP_WOOL));
        this.babyModel = new SheepFurModel(modelSet.bakeLayer(ModelLayers.SHEEP_BABY_WOOL));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight,
                       SheepRenderState renderState, float yRot, float xRot) {
        if (!renderState.isSheared && !renderState.isInvisible) {
            EntityModel<SheepRenderState> entitymodel = renderState.isBaby ? this.babyModel : this.adultModel;
            if (renderState.isJebSheep) {
                return; //Don't render on top of jeb_ sheep
            }
            Integer customColor = renderState.getRenderData(ClientHandler.COLOR);
            if (customColor != null) {
                coloredCutoutModelCopyLayerRender(entitymodel, SHEEP_WOOL_LOCATION, poseStack, nodeCollector, packedLight, renderState, customColor, 0);
            }
        }
    }

}
