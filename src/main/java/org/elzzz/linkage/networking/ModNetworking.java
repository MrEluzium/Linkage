package org.elzzz.linkage.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import org.elzzz.linkage.LinkageMod;
import org.elzzz.linkage.data.PlayerData;
import org.elzzz.linkage.item.DebugPieItem;
import org.elzzz.linkage.networking.packet.AvailablePlayersDataS2CPacket;
import org.elzzz.linkage.util.AvailablePlayersPacketBufHandler;

import java.util.HashMap;
import java.util.UUID;

public class ModNetworking {
    public static final Identifier AVAILABLE_PLAYERS_ID = new Identifier(LinkageMod.MOD_ID, "available_players_data");

    public static void registerC2SPackets() {

    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(AVAILABLE_PLAYERS_ID, AvailablePlayersDataS2CPacket::receive);
    }
}
