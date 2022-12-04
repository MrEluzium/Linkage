package org.elzzz.linkage.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.elzzz.linkage.LinkageMod;

public class BaseGlyphRingItem extends TrinketItem {

    public BaseGlyphRingItem(Settings settings) {
        super(settings);
    }

    @Override
    @Environment(EnvType.SERVER)
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        LinkageMod.LOGGER.info(String.format("Ring equipped: entity: %s stack: %s", entity.toString(), stack.getItem().toString()));
    }

    @Override
    @Environment(EnvType.SERVER)
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        LinkageMod.LOGGER.info(String.format("Ring UNequipped: entity: %s stack: %s", entity.toString(), stack.getItem().toString()));
    }
}
