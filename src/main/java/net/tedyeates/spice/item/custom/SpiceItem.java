package net.tedyeates.spice.item.custom;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tedyeates.spice.withdrawal.PlayerWithdrawalProvider;

public class SpiceItem extends Item {

  public SpiceItem(Properties properties) {
    super(properties);
  }

  @Override
  public ItemStack finishUsingItem(
    ItemStack itemStack, 
    Level level, 
    LivingEntity pLivingEntity
  ) {
    if (!pLivingEntity.level.isClientSide()) {
      pLivingEntity.getCapability(
        PlayerWithdrawalProvider.PLAYER_WITHDRAWAL
      ).ifPresent(withdrawal -> {
        withdrawal.satisfyAddiction(pLivingEntity);
      });
    }

    return super.finishUsingItem(itemStack, level, pLivingEntity);
  }

  @Override
  public void appendHoverText(
    ItemStack stack, 
    Level level, 
    List<Component> components, 
    TooltipFlag tooltipFlag
  ) {
    if(Screen.hasShiftDown()) {
      components.add(Component
        .translatable("description.spice.sight")
        .withStyle(ChatFormatting.BLUE)
      );
    } else {
      components.add(Component
        .translatable("description.spice.more_info")
        .withStyle(ChatFormatting.DARK_RED)
        .withStyle(ChatFormatting.ITALIC)
      );
      components.add(Component
        .translatable("effect.spice.regeneration")
        .withStyle(ChatFormatting.BLUE)
      );
      components.add(Component
        .translatable("effect.spice.sight")
        .withStyle(ChatFormatting.BLUE)
      );
    }

    super.appendHoverText(stack, level, components, tooltipFlag);
  }
}
