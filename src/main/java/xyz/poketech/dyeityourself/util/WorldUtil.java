package xyz.poketech.dyeityourself.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WorldUtil {

    /**
     * Checks if a position is a flower
     *
     * @param world the world to check in
     * @param pos   the position to check
     * @return if there's a flower at the given position
     */
    public static boolean isFlower(Level world, BlockPos pos) {
        return world.getBlockState(pos).is(BlockTags.SMALL_FLOWERS);
    }

    /**
     * Checks if the entity is on a flower
     *
     * @param entity the entity to check
     * @return if the entity is on a flower
     */
    public static boolean isEntityOnFlower(Entity entity) {
        return isFlower(entity.level(), entity.blockPosition());
    }

    /**
     * Returns the ItemStack of a flower for a given position
     *
     * @param world the world in which the flower is
     * @param pos   the position of the flower
     * @return the ItemStack of the flower
     */
    public static ItemStack getItemStackForBlockAt(Level world, BlockPos pos, BlockState state) {
        return world.getBlockState(pos).getBlock().getCloneItemStack(state, null, world, pos, null);
    }


    public static void spawnItem(Level world, BlockPos pos, Item item) {
        //TODO: change the ammount in the config (or let it be random)
        world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(item)));
    }
}
