package com.partialpeanut.duskrosetech.item.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.Collection;

public class MilkBottleItem extends Item {
    private static final int DRINK_DURATION = 40;

    public MilkBottleItem() {
        super((new Item.Properties())
                .craftRemainder(Items.GLASS_BOTTLE)
                .tab(CreativeModeTab.TAB_FOOD).stacksTo(16)
                .food(new FoodProperties.Builder()
                        .nutrition(0).saturationMod(0)
                        .alwaysEat().build()));
    }

    public ItemStack finishUsingItem(ItemStack itemStack, Level world, LivingEntity entity) {
        super.finishUsingItem(itemStack, world, entity);
        if (entity instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, itemStack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!world.isClientSide) {
            Collection<MobEffectInstance> effects = entity.getActiveEffects();
            if(effects.size() > 0) {
                entity.removeEffect(effects.iterator().next().getEffect());
            }
        }

        if (itemStack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (entity instanceof Player player && !player.getAbilities().instabuild) {
                ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
                if (!player.getInventory().add(itemstack)) {
                    player.drop(itemstack, false);
                }
            }
            return itemStack;
        }
    }

    public int getUseDuration(ItemStack stack) {
        return 40;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand useHand) {
        return ItemUtils.startUsingInstantly(world, player, useHand);
    }
}