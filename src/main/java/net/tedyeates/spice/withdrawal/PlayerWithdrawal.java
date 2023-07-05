package net.tedyeates.spice.withdrawal;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.tedyeates.spice.Spice;
import net.tedyeates.spice.networking.ModMessages;
import net.tedyeates.spice.networking.packet.WithdrawalDataSyncS2CPacket;

@AutoRegisterCapability
public class PlayerWithdrawal {
  private final int MIN_WITHDRAWAL = 0;
  private final int MAX_WITHDRAWAL = 20;
  
  private int maxWithdrawal = MAX_WITHDRAWAL;
  private int timerCap = 60 * Spice.SECOND_TICKS;
  private int withdrawal = maxWithdrawal;
  private int withdrawalTimer = 0;

  private boolean isAddicted = false;

  
  public int getWithdrawal() {
    return this.withdrawal / 2;
  }


  public void updateWithdrawal(int sub, Player player) {
    if (!this.isAddicted) return;

    applyWithdrawalEffect(withdrawal, player);
    if (this.withdrawal <= 0) return;

    // Only decrease withdrawal every timer_cap number of seconds
    if (this.withdrawalTimer < this.timerCap) {
      this.withdrawalTimer++;
      return;
    }
    
    this.withdrawal = Math.max(this.withdrawal - sub, MIN_WITHDRAWAL);
    this.withdrawalTimer = 0;
    
    player.sendSystemMessage(Component.literal(
      "Subtracted Addiction"
    ));
    player.sendSystemMessage(Component.literal(
      "Current addiction " + this.withdrawal
    ));

    updateClient((ServerPlayer)player);
  }


  public void satisfyAddiction(LivingEntity player) {
    this.withdrawal = maxWithdrawal;
    this.withdrawalTimer = 0;
    player.sendSystemMessage(Component.literal(
      "Current Withdrawal " + this.withdrawal
    ).withStyle(ChatFormatting.RED));

    if(
      !this.isAddicted && 
      player.level.random.nextFloat() <= Spice.ADDICTION_CHANCE
    ) {
      this.isAddicted = true;
    }

    updateClient((ServerPlayer)player);
  }


  public void resetAddiction(Entity player) {
    this.isAddicted = false;
    this.withdrawal = maxWithdrawal;
    withdrawalTimer = 0;
    updateClient((ServerPlayer)player);
  }


  public void setWithdrawalMax() {
    this.withdrawal = maxWithdrawal;
  }

  
  private void updateClient(ServerPlayer player){
    // Updates HUD bar
    ModMessages.sendToPlayer(
      new WithdrawalDataSyncS2CPacket(withdrawal), 
      player
    );
  }

  private static void applyEffect(Player player, MobEffect effect, int level) {
    MobEffectInstance effectInstance = new MobEffectInstance(
      effect, 
      2 * Spice.MINUTE_SECONDS * Spice.SECOND_TICKS,
      level
    );
    
    player.addEffect(effectInstance);
  }
  
  private static void applyWithdrawalEffect(
    int withdrawal, 
    Player player
  ) {
    switch(withdrawal) {
      case 0:
      case 1:
      applyEffect(player, MobEffects.WITHER,  3);
        break;
        case 2:
        applyEffect(player, MobEffects.BLINDNESS, 0);
      case 4:
        applyEffect(player, MobEffects.CONFUSION, 0);
        case 6:
        applyEffect(player, MobEffects.HUNGER, 0);
        break;
        default:
        break;
      }
    }

    
  public void copyFrom(PlayerWithdrawal source) {
      this.withdrawal = source.withdrawal;
    }
    
    public void saveNBTData(CompoundTag nbt) {
      nbt.putInt("addiction", withdrawal);
    }
    
    public void loadNBTData(CompoundTag nbt) {
      withdrawal = nbt.getInt("addiction");
    }
}
