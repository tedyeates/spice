package net.tedyeates.spice.client;

public class ClientWithdrawalData {
  private static int playerWithdrawal;
  private static int playerAddictionLevel;

  public static void set(int withdrawal, int addictionLevel) {
    ClientWithdrawalData.playerWithdrawal = withdrawal;
    ClientWithdrawalData.playerAddictionLevel = addictionLevel;
  }

  public static int getPlayerWithdrawal() {
    return playerWithdrawal;
  }

  public static int getPlayerAddictionLevel() {
    return playerAddictionLevel;
  }
}