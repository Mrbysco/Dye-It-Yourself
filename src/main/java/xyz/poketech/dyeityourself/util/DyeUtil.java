package xyz.poketech.dyeityourself.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DyeUtil {

    private static Map<BlockState, DyeColor> stateColorCache = new HashMap<>();

    public static DyeColor getDyeForFlowerAt(ServerLevel serverLevel, BlockPos pos) {

    	BlockState state = serverLevel.getBlockState(pos);
    	
    	// If possible, return early from the cache.
    	if (stateColorCache.containsKey(state)) {
    		
    		return stateColorCache.get(state);
    	}
    	
        //Grab the flower as an ItemStack
        ItemStack stack = WorldUtil.getItemStackForBlockAt(serverLevel, pos, state);

        ItemStack dye = getFlowerDye(stack, serverLevel);
        DyeColor color = DyeColor.getColor(dye);
        stateColorCache.put(state, color);
        return color;
    }

    private static ItemStack getFlowerDye(ItemStack stack, ServerLevel serverLevel) {
        //Simulate the crafting of a dye from a flower
	    CraftingInput inv = CraftingInput.of(1, 1, List.of(stack));
        Optional<RecipeHolder<CraftingRecipe>> recipe = serverLevel.recipeAccess().getRecipeFor(
                RecipeType.CRAFTING, inv, serverLevel);
        return recipe.map(iCraftingRecipe -> iCraftingRecipe.value().assemble(inv)).orElse(null);
    }
}
