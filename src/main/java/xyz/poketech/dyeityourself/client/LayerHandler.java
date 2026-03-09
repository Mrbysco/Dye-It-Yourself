package xyz.poketech.dyeityourself.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xyz.poketech.dyeityourself.client.render.layer.SheepWoolLayerOverride;

@EventBusSubscriber
public class LayerHandler {
	@SubscribeEvent
	public static void addRenderLayers(EntityRenderersEvent.AddLayers event) {
		EntityRenderer<?, ?> renderer =  event.getRenderer(EntityType.SHEEP);
		if (renderer instanceof SheepRenderer sheepRenderer) {
			sheepRenderer.addLayer(new SheepWoolLayerOverride(sheepRenderer, event.getEntityModels()));
		}
	}
}
