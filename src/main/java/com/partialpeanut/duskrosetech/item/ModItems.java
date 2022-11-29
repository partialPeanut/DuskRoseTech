package com.partialpeanut.duskrosetech.item;

import com.partialpeanut.duskrosetech.DuskRoseTechMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DuskRoseTechMod.MOD_ID);

    public static final RegistryObject<Item> MILK_BOTTLE = ITEMS.register("milk_bottle",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));
    public static final RegistryObject<Item> WARM_MILK_BOTTLE = ITEMS.register("warm_milk_bottle",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
