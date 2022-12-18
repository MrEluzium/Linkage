package org.elzzz.linkage.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.elzzz.linkage.LinkageMod;
import org.elzzz.linkage.networking.ModNetworking;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            String item_key = stack.getRegistryEntry().getKey().get().getValue().toString().split(":")[1];

            // tooltip translatable text is separated with :: symbols pair, which indicates line break
            String[] tooltip_lines = Text.translatable(String.format("item.linkage.%s.tooltip", item_key)).getString().split("::");
            for (String line : tooltip_lines) {
                tooltip.add(Text.literal(line));
            }
        }
        else {
            tooltip.add(Text.translatable("linkage.gui.press_shift_for_info").formatted(Formatting.GRAY));
        }
    }
}
