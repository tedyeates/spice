package net.tedyeates.spice.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tedyeates.spice.Spice;
import net.tedyeates.spice.client.WithdrawalHudOverlay;

public class ClientEvents {
  @Mod.EventBusSubscriber(modid = Spice.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModBusEvents {
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
      event.registerAboveAll("withdrawal", WithdrawalHudOverlay.HUD_WITHDRAWAL);
    }
  }
}
