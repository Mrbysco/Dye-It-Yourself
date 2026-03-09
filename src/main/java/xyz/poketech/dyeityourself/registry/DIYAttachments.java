package xyz.poketech.dyeityourself.registry;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import xyz.poketech.dyeityourself.DyeItYourself;
import xyz.poketech.dyeityourself.util.color.ColorUtil;

import java.util.function.Supplier;

public class DIYAttachments {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, DyeItYourself.MODID);

	public static final Supplier<AttachmentType<Integer>> COLOR = ATTACHMENT_TYPES.register(
			"color", () -> AttachmentType.builder(() -> ColorUtil.getRGB(255, 255, 255))
					.serialize(Codec.INT.fieldOf("color")).sync(ByteBufCodecs.INT).build());
}
