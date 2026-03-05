package xyz.poketech.dyeityourself.util;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DyeUtil {

    private static Map<BlockState, DyeColor> stateColorCache = new HashMap<>();

    public static DyeColor getDyeForFlowerAt(Level level, BlockPos pos) {

    	BlockState state = level.getBlockState(pos);
    	
    	// If possible, return early from the cache.
    	if (stateColorCache.containsKey(state)) {
    		
    		return stateColorCache.get(state);
    	}
    	
        //Grab the flower as an ItemStack
        ItemStack stack = WorldUtil.getItemStackForBlockAt(level, pos, state);

        ItemStack dye = getFlowerDye(stack, level);
        DyeColor color = DyeColor.getColor(dye);
        stateColorCache.put(state, color);
        return color;
    }

    private static ItemStack getFlowerDye(ItemStack stack, Level level) {
        //Simulate the crafting of a dye from a flower
        FakeContainer inv = new FakeContainer(stack);
        Optional<CraftingRecipe> recipe = level.getRecipeManager().getRecipeFor(
                RecipeType.CRAFTING, inv, level);
        return recipe.map(iCraftingRecipe -> iCraftingRecipe.assemble(inv, level.registryAccess())).orElse(null);
    }
}
