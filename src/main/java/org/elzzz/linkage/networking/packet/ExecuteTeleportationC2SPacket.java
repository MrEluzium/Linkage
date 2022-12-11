package org.elzzz.linkage.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import org.elzzz.linkage.LinkageMod;

public class ExecuteTeleportationC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity serverPlayerEntity,
                                   ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {

        PlayerEntity destination_player = LinkageMod.availablePlayers.get(buf.readUuid().toString());
        if (destination_player == null){
            LinkageMod.LOGGER.error(String.format("%s requested teleportation to %s, but target not in available players list",
                    serverPlayerEntity.getName().toString(),
                    destination_player.getName().toString()));
            return;
        }

        /*Random random = Random.create();
        for(int i = 0; i < 32; ++i) {
            serverPlayerEntity.world.addParticle(
                    ParticleTypes.PORTAL,
                    serverPlayerEntity.getX(),
                    serverPlayerEntity.getY() + random.nextDouble() * 2.0,
                    serverPlayerEntity.getZ(), random.nextGaussian(),
                    0.0,
                    random.nextGaussian());
        }*/

        serverPlayerEntity.teleport(
                (ServerWorld) destination_player.world,
                destination_player.getX(),
                destination_player.getY(),
                destination_player.getZ(),
                destination_player.headYaw,
                destination_player.prevPitch
        );

        LinkageMod.LOGGER.info(String.format("Teleported %s(%s) to %s(%s)",
                serverPlayerEntity.getName().getString(),
                serverPlayerEntity.world.getDimensionKey().getValue().toString(),
                destination_player.getName().getString(),
                destination_player.world.getDimensionKey().getValue().toString()));
    }
}
