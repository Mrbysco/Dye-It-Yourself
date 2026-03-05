package xyz.poketech.dyeityourself;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;
import xyz.poketech.dyeityourself.config.ConfigHandler;
import xyz.poketech.dyeityourself.config.ConfigHelper;
import xyz.poketech.dyeityourself.registry.DIYAttachments;
import xyz.poketech.dyeityourself.registry.DIYDataComponents;
import xyz.poketech.dyeityourself.registry.DIYItems;

@Mod(DyeItYourself.MODID)
public final class DyeItYourself {
    public static final String MODID = "dyeityourself";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static ConfigHandler CONFIG;

    public DyeItYourself(IEventBus eventBus, ModContainer container) {
        eventBus.addListener(this::addTabContents);

        DIYItems.ITEMS.register(eventBus);
        DIYDataComponents.DATA_COMPONENT_TYPES.register(eventBus);
        DIYAttachments.ATTACHMENT_TYPES.register(eventBus);

        CONFIG = ConfigHelper.register(eventBus, container, ModConfig.Type.SERVER, ConfigHandler::new);
    }

    public void addTabContents(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(DIYItems.DYE_BRUSH.get());
        }
    }

    public static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}