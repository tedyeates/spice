package net.tedyeates.spice.client;

public class ClientWithdrawalData {
  private static int playerWithdrawal;

  public static void set(int withdrawal) {
    ClientWithdrawalData.playerWithdrawal = withdrawal;
  }

  public static int getPlayerWithdrawal() {
    return playerWithdrawal;
  }
}