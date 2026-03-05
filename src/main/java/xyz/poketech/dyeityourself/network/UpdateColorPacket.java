package xyz.poketech.dyeityourself.network;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateColorPacket {

    private int entityId;
    private int color;

    public UpdateColorPacket() {
    }

    public UpdateColorPacket(Entity entityIn, int color) {
        this(entityIn.getId(), color);
    }

    public UpdateColorPacket(int entityId, int color) {
        this.entityId = entityId;
        this.color = color;
    }

    public UpdateColorPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeInt(this.color);
    }


    public Entity getEntity(Level worldIn) {
        return worldIn.getEntity(this.entityId);
    }

    public int getColor() {
        return this.color;
    }

    public void onMessage(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(
                Dist.CLIENT, () -> () -> DistHelper.updateSheepColor(this.entityId, this.color)
        ));
        ctx.get().setPacketHandled(true);
    }

}
