package org.elzzz.linkage.register;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.registry.Registry;
import org.elzzz.linkage.LinkageMod;
import org.elzzz.linkage.item.BaseGlyphRingItem;
import org.elzzz.linkage.item.DebugPieItem;


public class ModItems {

    /* Items */

    public static final Item GLYPH_RING = new BaseGlyphRingItem(new FabricItemSettings().maxCount(1));
    public static final Item INTERDIMENSIONAL_GLYPH_RING = new BaseGlyphRingItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));
    public static final Item DEBUG_PIE = new DebugPieItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));

    public static void register() {
        registerItem("glyph_ring", GLYPH_RING, ItemGroups.TOOLS);
        registerItem("interdimensional_glyph_ring", INTERDIMENSIONAL_GLYPH_RING, ItemGroups.TOOLS);
        registerItem("debug_pie", DEBUG_PIE, ItemGroups.TOOLS);
    }

    private static void registerItem(String name, Item item, ItemGroup group) {

        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        Registry.register(Registries.ITEM, new Identifier(LinkageMod.MOD_ID, name), item);
    }
}
