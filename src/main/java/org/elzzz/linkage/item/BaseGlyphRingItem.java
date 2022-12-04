package org.elzzz.linkage.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.elzzz.linkage.LinkageMod;

public class BaseGlyphRingItem extends TrinketItem {

    public BaseGlyphRingItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!entity.world.isClient()) {
            LinkageMod.LOGGER.info(String.format("Ring equipped | entity: %s dimension: %s stack: %s",
                    entity.toString(),
                    entity.world.getDimensionKey(),
                    stack.getItem().toString()));

            LinkageMod.availablePlayers.put(((PlayerEntity)entity).getUuidAsString(), (PlayerEntity)entity);
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!entity.world.isClient()){
            LinkageMod.LOGGER.info(String.format("Ring UNequipped | entity: %s dimension: %s stack: %s",
                    entity.toString(),
                    entity.world.getDimensionKey(),
                    stack.getItem().toString()));

            LinkageMod.availablePlayers.remove(((PlayerEntity)entity).getUuidAsString());
        }
    }
}
