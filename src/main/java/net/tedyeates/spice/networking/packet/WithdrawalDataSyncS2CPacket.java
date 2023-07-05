package net.tedyeates.spice.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.tedyeates.spice.client.ClientWithdrawalData;

import java.util.function.Supplier;

public class WithdrawalDataSyncS2CPacket {
  private final int withdrawal;

  public WithdrawalDataSyncS2CPacket(int withdrawal) {
    this.withdrawal = withdrawal;
  }

  public WithdrawalDataSyncS2CPacket(FriendlyByteBuf buf) {
    this.withdrawal = buf.readInt();
  }

  public void toBytes(FriendlyByteBuf buf) {
    buf.writeInt(withdrawal);

  }

  public boolean handle(Supplier<NetworkEvent.Context> supplier) {
    NetworkEvent.Context context = supplier.get();
    context.enqueueWork(() -> {
      // ON CLIENT
      ClientWithdrawalData.set(withdrawal);
    });
    return true;
  }
}