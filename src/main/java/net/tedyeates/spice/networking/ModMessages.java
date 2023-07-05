package net.tedyeates.spice.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.tedyeates.spice.Spice;
import net.tedyeates.spice.networking.packet.WithdrawalDataSyncS2CPacket;

public class ModMessages {
  private static SimpleChannel INSTANCE;

  private static int packetId = 0;
  private static int id() {
    return packetId++;
  }

  public static void register() {
    SimpleChannel net = NetworkRegistry.ChannelBuilder
      .named(new ResourceLocation(Spice.MOD_ID, "messages"))
      .networkProtocolVersion(() -> "1.0")
      .clientAcceptedVersions(s -> true)
      .serverAcceptedVersions(s -> true)
      .simpleChannel();

    INSTANCE = net;

    net.messageBuilder(WithdrawalDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
      .decoder(WithdrawalDataSyncS2CPacket::new)
      .encoder(WithdrawalDataSyncS2CPacket::toBytes)
      .consumerMainThread(WithdrawalDataSyncS2CPacket::handle)
      .add();
  }

  public static <MSG> void sendToServer(MSG message) {
    INSTANCE.sendToServer(message);
  }

  public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
    INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
  }
}
