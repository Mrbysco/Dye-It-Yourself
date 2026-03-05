package xyz.poketech.dyeityourself.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.poketech.dyeityourself.DyeItYourself;

import java.util.function.Supplier;

public class DIYDataComponents {
	public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, DyeItYourself.MODID);

	public static final Supplier<DataComponentType<Integer>> COLOR = DATA_COMPONENT_TYPES.registerComponentType("color", builder ->
			builder
					.persistent(Codec.INT)
					.networkSynchronized(ByteBufCodecs.INT)
	);
}
