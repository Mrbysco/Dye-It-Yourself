package xyz.poketech.dyeityourself.network;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import xyz.poketech.dyeityourself.DyeItYourself;
import xyz.poketech.dyeityourself.util.color.NBTColorUtil;

import java.util.function.Supplier;

public class RequestColorPacket {

    private int entityID;

    public RequestColorPacket() {
    }

    public RequestColorPacket(int entityID) {
        this.entityID = entityID;
    }

    RequestColorPacket(Entity entity) {
        this.entityID = entity.getId();
    }

    public RequestColorPacket(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityID);
    }

    public void onMessage(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.get().getSender();
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER && serverPlayer != null) {
                Entity entity = serverPlayer.level().getEntity(this.entityID);
                if (entity != null && entity.getPersistentData().contains(NBTColorUtil.COLOR_KEY)) {
                    int color = entity.getPersistentData().getInt(NBTColorUtil.COLOR_KEY);
                    DyeItYourself.NETWORK.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new UpdateColorPacket(this.entityID, color));
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}

