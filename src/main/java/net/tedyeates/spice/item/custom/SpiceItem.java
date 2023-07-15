package net.tedyeates.spice.item.custom;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tedyeates.spice.Spice;
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
      ItemStack milkStack = new ItemStack(Items.MILK_BUCKET);
      pLivingEntity.curePotionEffects(milkStack);
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
        .translatable("description.spice.focus")
        .withStyle(ChatFormatting.BLUE)
      );
    } else {
      components.add(Component
        .translatable("description.spice.more_info")
        .withStyle(ChatFormatting.DARK_RED)
        .withStyle(ChatFormatting.ITALIC)
      );

      components.add(Component
        .translatable("effect.minecraft.regeneration")
        .withStyle(ChatFormatting.BLUE)
        .append(
          Component
            .literal(" (0:" + Spice.SPICE_REGEN_SECONDS + ")")
            .withStyle(ChatFormatting.BLUE)
        )
      );

      components.add(Component
        .translatable("effect.minecraft.absorption")
        .withStyle(ChatFormatting.BLUE)
        .append(
          Component
            .literal(" (" + Spice.SPICE_ABSORB_MINUTES + ":" + Spice.SPICE_ABSORB_SECONDS + ")")
            .withStyle(ChatFormatting.BLUE)
        )
      );

      components.add(Component
        .translatable("effects.spice.focus")
        .withStyle(ChatFormatting.BLUE)
        .append(
          Component
            .literal(" (" + Spice.SPICE_FOCUS_MINUTES + ":00)")
            .withStyle(ChatFormatting.BLUE)
        )
      );
    }

    super.appendHoverText(stack, level, components, tooltipFlag);
  }
}
