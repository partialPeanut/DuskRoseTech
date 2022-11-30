package com.partialpeanut.duskrosetech.item;

import com.partialpeanut.duskrosetech.DuskRoseTechMod;
import com.partialpeanut.duskrosetech.item.custom.MilkBottleItem;
import com.partialpeanut.duskrosetech.item.custom.WarmMilkBottleItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DuskRoseTechMod.MOD_ID);

    public static final RegistryObject<Item> MILK_BOTTLE = ITEMS.register("milk_bottle", MilkBottleItem::new);
    public static final RegistryObject<Item> WARM_MILK_BOTTLE = ITEMS.register("warm_milk_bottle", WarmMilkBottleItem::new);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}