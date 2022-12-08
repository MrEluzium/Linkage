package org.elzzz.linkage.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.elzzz.linkage.LinkageMod;
import org.elzzz.linkage.networking.packet.AvailablePlayersDataS2CPacket;
import org.elzzz.linkage.networking.packet.MarkPlayerAsAvailableC2SPacket;
import org.elzzz.linkage.networking.packet.MarkPlayerAsUnavailableC2SPacket;

public class ModNetworking {

    /* C2S IDs */
    public static final Identifier MARK_PLAYER_AS_AVAILABLE_ID = new Identifier(LinkageMod.MOD_ID, "mark_player_as_available");
    public static final Identifier MARK_PLAYER_AS_UNAVAILABLE_ID = new Identifier(LinkageMod.MOD_ID, "mark_player_as_unavailable");

    /* S2C IDs*/
    public static final Identifier AVAILABLE_PLAYERS_ID = new Identifier(LinkageMod.MOD_ID, "available_players_data");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(MARK_PLAYER_AS_AVAILABLE_ID, MarkPlayerAsAvailableC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(MARK_PLAYER_AS_UNAVAILABLE_ID, MarkPlayerAsUnavailableC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(AVAILABLE_PLAYERS_ID, AvailablePlayersDataS2CPacket::receive);
    }
}
