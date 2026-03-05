package xyz.poketech.dyeityourself;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.poketech.dyeityourself.network.DistHelper;
import xyz.poketech.dyeityourself.network.PacketHandler;

@Mod.EventBusSubscriber
@Mod(DyeItYourself.MODID)
public final class DyeItYourself {
    public static final String MODID = "dyeityourself";

    public static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static ConfigHandler CONFIG;

    public DyeItYourself() {
        IEventBus eventBus =  FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::addTabContents);

        DIYItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONFIG = ConfigHelper.register(ModConfig.Type.SERVER, ConfigHandler::new);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(DistHelper::addRenderLayers);
        });
    }

    public void setup(FMLCommonSetupEvent e) {
        PacketHandler.registerMessages();
    }


    public void addTabContents(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(DIYItems.DYE_BRUSH.get());
        }
    }
}