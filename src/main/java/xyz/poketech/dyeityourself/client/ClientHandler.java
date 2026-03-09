package xyz.poketech.dyeityourself.client;

import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import xyz.poketech.dyeityourself.DyeItYourself;
import xyz.poketech.dyeityourself.registry.DIYAttachments;

@EventBusSubscriber
public class ClientHandler {
	public static final ContextKey<Integer> COLOR = new ContextKey<>(Identifier.fromNamespaceAndPath(DyeItYourself.MODID, "color"));

	@SubscribeEvent
	public static void registerRenderStateModifier(RegisterRenderStateModifiersEvent event) {
		event.registerEntityModifier(SheepRenderer.class, (sheep, renderState) -> {
			if (!renderState.isJebSheep) {
				int color = sheep.getData(DIYAttachments.COLOR);
				if (color != -1) {
					renderState.setRenderData(COLOR, color);
				}
			}
		});
	}
}
