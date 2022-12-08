package org.elzzz.linkage.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.elzzz.linkage.networking.ModNetworking;

public class BaseGlyphRingItem extends TrinketItem {

    public BaseGlyphRingItem(Settings settings) {
        super(settings);
    }

    // We process onEquip and onUnequip only on client due to this issue: https://github.com/emilyploszaj/trinkets/issues/225
    // Sometimes server misses these events and players stays in available list, although they should be removed
    // To avoid this, we tell server to update available list every time client get onEquip or onUnequip call

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity.world.isClient()) {
            ClientPlayNetworking.send(ModNetworking.MARK_PLAYER_AS_AVAILABLE_ID, PacketByteBufs.empty());
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity.world.isClient()) {
            ClientPlayNetworking.send(ModNetworking.MARK_PLAYER_AS_UNAVAILABLE_ID, PacketByteBufs.empty());
        }
    }
}
