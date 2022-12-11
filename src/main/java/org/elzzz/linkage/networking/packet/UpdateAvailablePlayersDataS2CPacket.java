package org.elzzz.linkage.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import org.elzzz.linkage.client.LinkageClient;
import org.elzzz.linkage.client.gui.screen.LinkageTeleportScreen;
import org.elzzz.linkage.util.AvailablePlayersPacketBufHandler;

public class UpdateAvailablePlayersDataS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender sender) {
        LinkageClient.clientAvailablePlayers = AvailablePlayersPacketBufHandler.readBuf(buf);
        if (client.currentScreen instanceof LinkageTeleportScreen) {
            ((LinkageTeleportScreen) client.currentScreen).refresh();
        }
    }
}
