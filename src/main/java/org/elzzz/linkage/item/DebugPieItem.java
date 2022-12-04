package org.elzzz.linkage.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.elzzz.linkage.LinkageMod;

public class DebugPieItem extends Item {
    public DebugPieItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient && hand == Hand.MAIN_HAND){
            user.sendMessage(Text.literal(LinkageMod.availablePlayers.toString()));
        }

        return super.use(world, user, hand);
    }
}
