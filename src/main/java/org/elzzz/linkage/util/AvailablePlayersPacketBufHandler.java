package org.elzzz.linkage.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import org.elzzz.linkage.data.PlayerData;

import java.util.HashMap;
import java.util.UUID;

public class AvailablePlayersPacketBufHandler {

    public static PacketByteBuf getBuf(HashMap<String, PlayerEntity> availablePlayers) {
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeVarInt(availablePlayers.size());
        availablePlayers.forEach((key, value) -> {

            buf.writeUuid(value.getUuid());
            buf.writeString(value.getWorld().getDimensionKey().getValue().toString());
            buf.writeDouble(value.getPos().x);
            buf.writeDouble(value.getPos().y);
            buf.writeDouble(value.getPos().z);
        });
        return buf;
    }

    public static HashMap<UUID, PlayerData> readBuf(PacketByteBuf buf) {
        HashMap<UUID, PlayerData> availablePlayersData = new HashMap<>();

        int size = buf.readVarInt();
        for(int i = 0; i < size; i++) {
            UUID currentUUID = buf.readUuid();
            String dimension = buf.readString();
            Vec3d pos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
            availablePlayersData.put(currentUUID, new PlayerData(dimension, pos));
        }

        return availablePlayersData;
    }
}
