package org.elzzz.linkage.register;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.elzzz.linkage.LinkageMod;
import org.elzzz.linkage.item.BaseGlyphRingItem;


public class ModItems {

    /* Items */

    public static final Item GLYPH_RING = new BaseGlyphRingItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));
    public static final Item INTERDIMENSIONAL_GLYPH_RING = new BaseGlyphRingItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));

    public static void register() {
        registerItem("glyph_ring", GLYPH_RING);
        registerItem("interdimensional_glyph_ring", INTERDIMENSIONAL_GLYPH_RING);
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(LinkageMod.MOD_ID, name), item);
    }
}
