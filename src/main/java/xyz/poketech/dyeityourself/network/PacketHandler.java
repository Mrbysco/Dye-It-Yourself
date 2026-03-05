package xyz.poketech.dyeityourself.network;

import net.minecraft.resources.ResourceKey;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import xyz.poketech.dyeityourself.DyeItYourself;

public class PacketHandler {

    public static void registerMessages() {
        int id = 0;

        DyeItYourself.NETWORK.registerMessage(
                id++,
                RequestColorPacket.class,
                RequestColorPacket::encode,
                RequestColorPacket::new,
                RequestColorPacket::onMessage
        );
        DyeItYourself.NETWORK.registerMessage(
                id++,
                UpdateColorPacket.class,
                UpdateColorPacket::encode,
                UpdateColorPacket::new,
                UpdateColorPacket::onMessage
        );
    }

    public static void sendColorUpdate(int target, int color, BlockPos pos, ResourceKey<Level> dimension, int range) {
        DyeItYourself.NETWORK.send(
                PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), range, dimension)),
                new UpdateColorPacket(target,  color)
        );
    }
}
