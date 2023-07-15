package net.tedyeates.spice.client;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.tedyeates.spice.Spice;

public class WithdrawalHudOverlay {
  private static final ResourceLocation EMPTY_WITHDRAWAL = new ResourceLocation(
    Spice.MOD_ID,
    "textures/withdrawal/empty_withdrawal.png"
  );

  private static final ResourceLocation FILLED_WITHDRAWAL = new ResourceLocation(
    Spice.MOD_ID,
    "textures/withdrawal/filled_withdrawal.png"
  );

  private static int wave(int withdrawal, int top, Player player, ForgeGui gui){
    if (gui.getGuiTicks() % (withdrawal * 3 + 1) == 0) {
      return top + (player.getRandom().nextInt(3) - 1);
    }

    return top;
  } 

  public static final IGuiOverlay HUD_WITHDRAWAL = ((
    gui, poseStack, partialTick, width, height
  ) -> {
    Minecraft minecraft = gui.getMinecraft();
    Player player = (Player) minecraft.getCameraEntity();

    int withdrawal = ClientWithdrawalData.getPlayerWithdrawal();
    int addictionLevel = ClientWithdrawalData.getPlayerAddictionLevel();
    boolean hideGui = minecraft.options.hideGui;
    if (!hideGui && gui.shouldDrawSurvivalElements() && addictionLevel > 0) {
      minecraft.getProfiler().push("withdrawal");
      RenderSystem.enableBlend();
      int left = width / 2 + 80;
      int top = height - gui.rightHeight;
      int y = top;
      gui.rightHeight += 10;

      gui.setupOverlayRenderState(true, false, EMPTY_WITHDRAWAL);
      for(int i = 0; i < 10; i++) {
        y = wave(withdrawal, top, player, gui);
        GuiComponent.blit(poseStack,left - (i * 9), y,0,0,12,12,
                12,12);
      }

      RenderSystem.setShaderTexture(0, FILLED_WITHDRAWAL);
      for(int i = 0; i < 10; i++) {
        if(ClientWithdrawalData.getPlayerWithdrawal() / 2 > i) {
          y = wave(withdrawal, top, player, gui);
          GuiComponent.blit(poseStack,left - (i * 9), y,0,0,12,12,
                  12,12);
        } else {
          break;
        }
      }
      RenderSystem.disableBlend();
      minecraft.getProfiler().pop();
    }
  });

  
}

