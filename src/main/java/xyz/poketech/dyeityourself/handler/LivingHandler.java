package xyz.poketech.dyeityourself.handler;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.poketech.dyeityourself.DyeItYourself;
import xyz.poketech.dyeityourself.ai.EatFlowerGoal;
import xyz.poketech.dyeityourself.network.RequestColorPacket;
import xyz.poketech.dyeityourself.util.RandomUtil;

@Mod.EventBusSubscriber(modid = DyeItYourself.MODID)
public class LivingHandler {

    public static final String NEXT_DYE_KEY = "nextDye";

    @SubscribeEvent
    public static void onEntityEnterWorld(EntityJoinLevelEvent event) {
        //Sync the sheep color on the client
        if (event.getEntity() instanceof Sheep sheep) {

	        if (event.getLevel().isClientSide()) {
                DyeItYourself.NETWORK.sendToServer(new RequestColorPacket(event.getEntity().getId()));
            }

            else if (DyeItYourself.CONFIG.sheepEatFlowers.get()) {
                sheep.goalSelector.addGoal(5, new EatFlowerGoal(sheep));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.level().isClientSide() && DyeItYourself.CONFIG.doDropDye.get() && entity instanceof Sheep sheep) {
            CompoundTag data = sheep.getPersistentData();

            if (data.contains(NEXT_DYE_KEY)) {
                int nextDye = data.getInt(NEXT_DYE_KEY);
                if (nextDye == 1) {
                    //Spawn a random amount of dye
                    int count = RandomUtil.getDyeDropAmountSafe();
                    if (count != 0) {
                        Containers.dropItemStack(sheep.level(), sheep.getX(), sheep.getY(), sheep.getZ(), new ItemStack(DyeItem.byColor(sheep.getColor()), count));

                        //Play the chicken egg sound
                        float pitch = (sheep.getRandom().nextFloat() - sheep.getRandom().nextFloat()) * 0.2F + 1.0F;
                        sheep.playSound(SoundEvents.CHICKEN_EGG, 1.0F, pitch);
                    }

                    //Set new dye
                    data.putInt(NEXT_DYE_KEY, RandomUtil.getNextDye());
                } else {
                    data.putInt(NEXT_DYE_KEY, --nextDye);
                }
            } else {
                data.putInt(NEXT_DYE_KEY, RandomUtil.getNextDye());
            }
        }
    }
}
