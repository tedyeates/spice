package net.tedyeates.spice.withdrawal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerWithdrawalProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

  public static Capability<PlayerWithdrawal> PLAYER_WITHDRAWAL = 
    CapabilityManager.get(new CapabilityToken<PlayerWithdrawal>() {});

  private PlayerWithdrawal addiction = null;
  private final LazyOptional<PlayerWithdrawal> optional = LazyOptional
    .of(this::createPlayerWithdrawal);

  private PlayerWithdrawal createPlayerWithdrawal(){
    if (this.addiction == null) {
      this.addiction = new PlayerWithdrawal();
    }

    return this.addiction;
  }

  @Override
  public CompoundTag serializeNBT() {
    CompoundTag nbt = new CompoundTag();
    createPlayerWithdrawal().saveNBTData(nbt);
    
    return nbt;
  }

  @Override
  public void deserializeNBT(CompoundTag nbt) {
    createPlayerWithdrawal().loadNBTData(nbt);
  }

  @Override
  public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
    if(cap == PLAYER_WITHDRAWAL) {
      return optional.cast();
    }

    return LazyOptional.empty();
  }
  
}
