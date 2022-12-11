package org.elzzz.linkage.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

import java.util.HashMap;
import java.util.UUID;

public class AvailablePlayersPacketBufHandler {

    public static PacketByteBuf getBuf(HashMap<String, PlayerEntity> availablePlayers) {
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeVarInt(availablePlayers.size());
        availablePlayers.forEach((key, value) -> {
            buf.writeUuid(value.getUuid());
            buf.writeString(value.getWorld().getDimensionKey().getValue().toString());
        });

        return buf;
    }

    public static HashMap<UUID, String> readBuf(PacketByteBuf buf) {
        HashMap<UUID, String> availablePlayersData = new HashMap<>();

        int size = buf.readVarInt();
        for(int i = 0; i < size; i++) {
            UUID currentUUID = buf.readUuid();
            String dimension = buf.readString();
            availablePlayersData.put(currentUUID, dimension);
        }

        return availablePlayersData;
    }
}
