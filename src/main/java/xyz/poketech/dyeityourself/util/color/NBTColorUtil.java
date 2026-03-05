package xyz.poketech.dyeityourself.util.color;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import xyz.poketech.dyeityourself.registry.DIYAttachments;
import xyz.poketech.dyeityourself.registry.DIYDataComponents;

public class NBTColorUtil {

    public static final String COLOR_KEY = "diy_color";
    private static final int WHITE = ColorUtil.getRGB(255, 255, 255);

    public static void setEntityColor(Entity entity, int color) {
        entity.setData(DIYAttachments.COLOR, color);
    }

    public static void setEntityColor(Entity entity, int r, int g, int b) {
        setEntityColor(entity, ColorUtil.getRGB(r, g, b));
    }

    public static void removeEntityColor(Entity entity) {
        entity.removeData(DIYAttachments.COLOR);
    }

    public static int getColor(ItemStack stack) {
        if (stack.has(DIYDataComponents.COLOR)) {
            return stack.get(DIYDataComponents.COLOR).intValue();
        } else {
            stack.set(DIYDataComponents.COLOR, WHITE);
        }
        return WHITE;
    }
}
