package com.partialpeanut.duskrosetech.item.custom;

import com.mojang.datafixers.util.Either;
import com.partialpeanut.duskrosetech.block.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.List;

public class WarmMilkBottleItem extends MilkBottleItem {
    public WarmMilkBottleItem() {
        super();
    }

    public ItemStack finishUsingItem(ItemStack itemStack, Level world, LivingEntity entity) {
        super.finishUsingItem(itemStack, world, entity);
        if (entity instanceof ServerPlayer player) {
            startSleepOnFloor(player).ifLeft((problem) -> {
                if (problem.getMessage() != null) {
                    player.displayClientMessage(problem.getMessage(), true);
                }
            });
        }
        return itemStack;
    }

    public Either<Player.BedSleepingProblem, Unit> startSleepOnFloor(ServerPlayer player) {
        java.util.Optional<BlockPos> optAt = java.util.Optional.of(player.blockPosition());
        Player.BedSleepingProblem ret = net.minecraftforge.event.ForgeEventFactory.onPlayerSleepInBed(player, optAt);
        if (ret != null) return Either.left(ret);

        if (!player.isSleeping() && player.isAlive()) {
            if (!player.level.dimensionType().natural()) {
                return Either.left(Player.BedSleepingProblem.NOT_POSSIBLE_HERE);
            } else {
                if (!net.minecraftforge.event.ForgeEventFactory.fireSleepingTimeCheck(player, optAt)) {
                    return Either.left(Player.BedSleepingProblem.NOT_POSSIBLE_NOW);
                } else {
                    if (!player.isCreative()) {
                        double d0 = 8.0D;
                        double d1 = 5.0D;
                        Vec3 vec3 = Vec3.atBottomCenterOf(player.blockPosition());
                        List<Monster> list = player.level.getEntitiesOfClass(Monster.class, new AABB(vec3.x() - 8.0D, vec3.y() - 5.0D, vec3.z() - 8.0D, vec3.x() + 8.0D, vec3.y() + 5.0D, vec3.z() + 8.0D), (p_9062_) ->
                                p_9062_.isPreventingPlayerRest(player));
                        if (!list.isEmpty()) {
                            return Either.left(Player.BedSleepingProblem.NOT_SAFE);
                        }
                    }

                    if (player.level.getBlockState(player.blockPosition()).isAir()) {
                        player.level.setBlock(player.blockPosition(), ModBlocks.BED_AIR.get().defaultBlockState(), 0);
                    }

                    player.startSleeping(player.blockPosition());
                    player.awardStat(Stats.SLEEP_IN_BED);
                    CriteriaTriggers.SLEPT_IN_BED.trigger(player);

                    if (!player.getLevel().canSleepThroughNights()) {
                        player.displayClientMessage(Component.translatable("sleep.not_possible"), true);
                    }

                    ((ServerLevel)player.level).updateSleepingPlayerList();
                    return Either.right(Unit.INSTANCE);
                }
            }
        } else {
            return Either.left(Player.BedSleepingProblem.OTHER_PROBLEM);
        }
    }
}