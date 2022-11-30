package com.partialpeanut.duskrosetech.event;

import com.partialpeanut.duskrosetech.DuskRoseTechMod;
import com.partialpeanut.duskrosetech.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = DuskRoseTechMod.MOD_ID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onPlayerWakeup(PlayerWakeUpEvent event) {
            BlockPos pos = event.getEntity().blockPosition();
            Level world = event.getEntity().getLevel();
            if (world.getBlockState(pos).is(ModBlocks.BED_AIR.get())) {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
            }
        }
    }
}
