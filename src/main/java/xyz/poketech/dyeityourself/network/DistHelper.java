package xyz.poketech.dyeityourself.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import xyz.poketech.dyeityourself.DyeItYourself;
import xyz.poketech.dyeityourself.client.render.layer.SheepFurLayerOverride;
import xyz.poketech.dyeityourself.util.color.NBTColorUtil;

public class DistHelper {
    public static void updateSheepColor(int entityId, int color) {
        if (Minecraft.getInstance().level == null) {
            DyeItYourself.LOGGER.error("Minecraft.getInstance().world was null! That shouldn't happen.");
            return;
        }
        Entity entity = Minecraft.getInstance().level.getEntity(entityId);

        if (entity != null) {
            entity.getPersistentData().putInt(NBTColorUtil.COLOR_KEY, color);
        }
    }

    public static void addRenderLayers(EntityRenderersEvent.AddLayers event) {
        EntityRenderer<?> renderer =  event.getRenderer(EntityType.SHEEP);
        if (renderer instanceof SheepRenderer sheepRenderer) {
            sheepRenderer.addLayer(new SheepFurLayerOverride(sheepRenderer, event.getEntityModels()));
        }
    }
}
