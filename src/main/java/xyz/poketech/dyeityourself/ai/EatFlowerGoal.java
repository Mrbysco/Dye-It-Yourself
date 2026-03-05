package xyz.poketech.dyeityourself.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import xyz.poketech.dyeityourself.DyeItYourself;
import xyz.poketech.dyeityourself.util.DyeUtil;
import xyz.poketech.dyeityourself.util.WorldUtil;

import java.util.EnumSet;

public class EatFlowerGoal extends Goal {

    /**
     * The entity owner of this AITask
     */
    private final Mob flowerEaterEntity;

    /**
     * The world the flower eater entity is eating from
     */
    private final Level entityWorld;

    /**
     * Number of ticks since the entity started to eat flowers
     */
    int eatingFlowerTimer;

    public EatFlowerGoal(Mob flowerEaterEntityIn) {
        this.flowerEaterEntity = flowerEaterEntityIn;
        this.entityWorld = flowerEaterEntityIn.level();
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
        return this.flowerEaterEntity.getRandom().nextInt(this.flowerEaterEntity.isBaby() ? 50 : 1000) == 0 && WorldUtil.isEntityOnFlower(this.flowerEaterEntity);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.eatingFlowerTimer = 40;
        this.entityWorld.broadcastEntityEvent(this.flowerEaterEntity, (byte) 10);
        this.flowerEaterEntity.getNavigation().stop();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
    public void stop() {
        this.eatingFlowerTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return this.eatingFlowerTimer > 0;
    }

    /**
     * Number of ticks since the entity started to eat flowers
     */
    public int getEatingFlowerTimer() {
        return this.eatingFlowerTimer;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        this.eatingFlowerTimer = Math.max(0, this.eatingFlowerTimer - 1);

        if (this.eatingFlowerTimer == 4) {
            BlockPos blockpos = getBlockPos();

            if (WorldUtil.isEntityOnFlower(this.flowerEaterEntity)) {

                DyeColor color = DyeUtil.getDyeForFlowerAt(this.entityWorld, blockpos);
                if (this.entityWorld.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }

                this.flowerEaterEntity.ate();

                if (this.flowerEaterEntity instanceof Sheep sheep && DyeItYourself.CONFIG.sheepAbsorbColor.get()) {
	                sheep.setColor(color);
                }
            }
        }
    }

    private BlockPos getBlockPos() {
        return this.flowerEaterEntity.blockPosition();
    }

}
