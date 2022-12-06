package org.elzzz.linkage.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.elzzz.linkage.LinkageMod;
import org.elzzz.linkage.data.PlayerData;
import org.elzzz.linkage.util.AvailablePlayersPacketBufHandler;

import java.util.HashMap;
import java.util.UUID;

public class DebugPieItem extends Item {
    public DebugPieItem(Settings settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    public static void showAvailablePlayers(PlayerEntity user, HashMap<UUID, PlayerData> availablePlayersData){
        if (availablePlayersData.isEmpty()) {
            user.sendMessage(Text.literal("No Available Players Found"));
        }
        else {
            user.sendMessage(Text.literal("- Available Players Data -"));
            availablePlayersData.forEach((uuid, playerData) -> {
                ClientPlayNetworkHandler clientPlayNetworkHandler = MinecraftClient.getInstance().player.networkHandler;
                String playerName = clientPlayNetworkHandler.getPlayerListEntry(uuid).getProfile().getName();
                user.sendMessage(Text.literal(uuid.toString() + " | " + playerName + " | " + playerData.getDimension() + " | " + playerData.getPos().toString()));
            });
        }
    }

    /* available_players_data packet sender */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient() || hand != Hand.MAIN_HAND) return TypedActionResult.pass(user.getStackInHand(hand));

        PacketByteBuf buf = AvailablePlayersPacketBufHandler.getBuf(LinkageMod.availablePlayers);

        ServerPlayNetworking.send((ServerPlayerEntity)user, LinkageMod.availablePlayersPacketIdentifier, buf);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
