package xyz.poketech.dyeityourself;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.poketech.dyeityourself.item.DyeBrushItem;

public class DIYItems {

    static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DyeItYourself.MODID);

    public static final RegistryObject<Item> DYE_BRUSH = ITEMS.register("dye_brush", () ->
            new DyeBrushItem(
                    new Item.Properties().stacksTo(1)
            )
    );

}
