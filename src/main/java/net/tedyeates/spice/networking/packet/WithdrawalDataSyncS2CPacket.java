package net.tedyeates.spice.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.tedyeates.spice.client.ClientWithdrawalData;

import java.util.function.Supplier;

public class WithdrawalDataSyncS2CPacket {
  private final int withdrawal;
  private final int addictionLevel;

  public WithdrawalDataSyncS2CPacket(int withdrawal, int addictionLevel) {
    this.withdrawal = withdrawal;
    this.addictionLevel = addictionLevel;
  }

  public WithdrawalDataSyncS2CPacket(FriendlyByteBuf buf) {
    this.withdrawal = buf.readInt();
    this.addictionLevel = buf.readInt();
  }

  public void toBytes(FriendlyByteBuf buf) {
    buf.writeInt(withdrawal);
    buf.writeInt(addictionLevel);
  }

  public boolean handle(Supplier<NetworkEvent.Context> supplier) {
    NetworkEvent.Context context = supplier.get();
    context.enqueueWork(() -> {
      // ON CLIENT
      ClientWithdrawalData.set(withdrawal, addictionLevel);
    });
    return true;
  }
}