package xyz.poketech.dyeityourself.registry;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.poketech.dyeityourself.DyeItYourself;
import xyz.poketech.dyeityourself.item.DyeBrushItem;

public class DIYItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DyeItYourself.MODID);

    public static final DeferredItem<DyeBrushItem> DYE_BRUSH = ITEMS.registerItem("dye_brush", DyeBrushItem::new,
            new Item.Properties().stacksTo(1)
    );

}
