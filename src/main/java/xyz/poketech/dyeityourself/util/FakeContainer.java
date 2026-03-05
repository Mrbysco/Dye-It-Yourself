package xyz.poketech.dyeityourself.util;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class FakeContainer extends SimpleContainer implements CraftingContainer {
	public FakeContainer(ItemStack... items) {
		super(items);
	}

	@Override
	public int getWidth() {
		return 1;
	}

	@Override
	public int getHeight() {
		return 1;
	}

	@Override
	public List<ItemStack> getItems() {
		return items;
	}
}
