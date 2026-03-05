package xyz.poketech.dyeityourself.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.RandomUtils;
import xyz.poketech.dyeityourself.network.PacketHandler;
import xyz.poketech.dyeityourself.util.color.ColorUtil;
import xyz.poketech.dyeityourself.util.color.NBTColorUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class DyeBrushItem extends Item {

    public DyeBrushItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand usedHand) {
        if(!player.level().isClientSide()) {
            if(target instanceof Sheep) {
                int color = NBTColorUtil.getColor(stack);
                target.getPersistentData().putInt(NBTColorUtil.COLOR_KEY, color);
                PacketHandler.sendColorUpdate(target.getId(), color, target.blockPosition(), player.level().dimension(), 25);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(!player.level().isClientSide() && player.getPose() == Pose.CROUCHING) {
            ItemStack itemStack = player.getItemInHand(usedHand);

            if(itemStack.getTag() == null) {
                itemStack.setTag(new CompoundTag());
            }

            int r = RandomUtils.nextInt(0, 256);
            int g = RandomUtils.nextInt(0, 256);
            int b = RandomUtils.nextInt(0, 256);

            int color = ColorUtil.getRGB(r,g,b);

            itemStack.getTag().putInt(NBTColorUtil.COLOR_KEY, color);

            player.displayClientMessage(Component.translatable("tooltip.dyeityourself.current_color", r, g,b)
                    .withStyle(style -> style.withColor(color)), true);
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(usedHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);

        int color = NBTColorUtil.getColor(stack);
        int[] rgb = ColorUtil.toRGB(color);
        tooltipComponents.add(Component.literal("WIP"));
        tooltipComponents.add(Component.translatable("tooltip.dyeityourself.current_color", rgb[0], rgb[1],rgb[2])
                .withStyle(style -> style.withColor(color)));
        tooltipComponents.add(Component.translatable("item.dyeityourself.dye_brush.tooltip"));
    }
}
