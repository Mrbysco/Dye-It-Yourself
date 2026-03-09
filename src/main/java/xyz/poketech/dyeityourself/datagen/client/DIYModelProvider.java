package xyz.poketech.dyeityourself.datagen.client;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import xyz.poketech.dyeityourself.DyeItYourself;
import xyz.poketech.dyeityourself.registry.DIYItems;

public class DIYModelProvider extends ModelProvider {
	public DIYModelProvider(PackOutput output) {
		super(output, DyeItYourself.MODID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		itemModels.generateFlatItem(DIYItems.DYE_BRUSH.get(), ModelTemplates.FLAT_ITEM);
	}
}
