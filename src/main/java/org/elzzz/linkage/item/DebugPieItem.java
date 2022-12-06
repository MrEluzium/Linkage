package org.elzzz.linkage.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.PlayerListEntry;
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

import java.util.HashMap;

public class DebugPieItem extends Item {
    public DebugPieItem(Settings settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    public static void showAvailablePlayers(PlayerEntity user, HashMap<String, PlayerListEntry> availablePlayers){
        availablePlayers.forEach((key, value) -> {
            user.sendMessage(Text.literal(key + " | " + value.getProfile().getName()));
        });
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient() || hand != Hand.MAIN_HAND) return TypedActionResult.pass(user.getStackInHand(hand));

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(LinkageMod.availablePlayers.size());
        LinkageMod.availablePlayers.forEach((key, value) -> {
            buf.writeUuid(value.getUuid());
        });

        ServerPlayNetworking.send((ServerPlayerEntity)user, LinkageMod.availablePlayersPacketIdentifier, buf);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
