package xyz.poketech.dyeityourself.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.NotNull;
import xyz.poketech.dyeityourself.registry.DIYAttachments;
import xyz.poketech.dyeityourself.registry.DIYDataComponents;
import xyz.poketech.dyeityourself.util.color.ColorUtil;
import xyz.poketech.dyeityourself.util.color.NBTColorUtil;

import java.util.function.Consumer;

public class DyeBrushItem extends Item {

    public DyeBrushItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand usedHand) {
        if(!player.level().isClientSide()) {
            if(target instanceof Sheep) {
                int color = NBTColorUtil.getColor(stack);
                target.setData(DIYAttachments.COLOR, color);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @NotNull
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if(!player.level().isClientSide() && player.getPose() == Pose.CROUCHING) {
            ItemStack itemStack = player.getItemInHand(hand);

            int r = RandomUtils.nextInt(0, 256);
            int g = RandomUtils.nextInt(0, 256);
            int b = RandomUtils.nextInt(0, 256);

            int color = ColorUtil.getRGB(r,g,b);

            itemStack.set(DIYDataComponents.COLOR, color);

            player.displayClientMessage(Component.translatable("tooltip.dyeityourself.current_color", r, g,b)
                    .withStyle(style -> style.withColor(color)), true);
        }
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay,
                                Consumer<Component> tooltipAdder, TooltipFlag flag) {
        int color = NBTColorUtil.getColor(stack);
        int[] rgb = ColorUtil.toRGB(color);
        tooltipAdder.accept(Component.literal("WIP"));
        tooltipAdder.accept(Component.translatable("tooltip.dyeityourself.current_color", rgb[0], rgb[1],rgb[2])
                .withStyle(style -> style.withColor(color)));
        tooltipAdder.accept(Component.translatable("item.dyeityourself.dye_brush.tooltip"));
    }
}
