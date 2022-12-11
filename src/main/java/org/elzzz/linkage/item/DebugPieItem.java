package org.elzzz.linkage.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.elzzz.linkage.client.LinkageClient;

public class DebugPieItem extends Item {
    public DebugPieItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) return super.use(world, user, hand);

        if (LinkageClient.clientAvailablePlayers.isEmpty()) {
            user.sendMessage(Text.literal("No Available Players Found"));
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
        else {
            user.sendMessage(Text.literal("- Available Players Data -"));
            LinkageClient.clientAvailablePlayers.forEach((uuid, dimension) -> {
                ClientPlayNetworkHandler clientPlayNetworkHandler = MinecraftClient.getInstance().player.networkHandler;
                String playerName = clientPlayNetworkHandler.getPlayerListEntry(uuid).getProfile().getName();
                user.sendMessage(Text.literal(uuid.toString() + " | " + playerName + " | " + dimension));
            });
            return TypedActionResult.success(user.getStackInHand(hand));
        }
    }
}
