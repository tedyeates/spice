package net.tedyeates.spice.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.tedyeates.spice.Spice;
import net.tedyeates.spice.block.ModBlocks;
import net.tedyeates.spice.withdrawal.PlayerWithdrawalProvider;


@Mod.EventBusSubscriber(modid = Spice.MOD_ID)
public class ModEvents {

  @SubscribeEvent
  public static void onSoakEnrichedSand(PlayerInteractEvent event) {
    Level level = event.getLevel();
    BlockPos blockpos = event.getPos();
    Player player = event.getEntity();
    ItemStack itemstack = event.getItemStack();
    BlockState blockstate = level.getBlockState(blockpos);

    if (
      event.getFace() != Direction.DOWN && 
      blockstate.is(ModBlocks.ENRICHED_SAND.get()) &&
      PotionUtils.getPotion(itemstack) == Potions.WATER
    ) {
      level.playSound(
        (Player)null, 
        blockpos, 
        SoundEvents.GENERIC_SPLASH, 
        SoundSource.PLAYERS, 
        1.0F, 
        1.0F
      );

      player.setItemInHand(
        event.getHand(), 
        ItemUtils.createFilledResult(
          itemstack, 
          player, 
          new ItemStack(Items.GLASS_BOTTLE)
        )
      ); // Empty water bottle

      player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
      
      if (!level.isClientSide) {
        ServerLevel serverlevel = (ServerLevel)level;

        for(int i = 0; i < 5; ++i) {
            serverlevel.sendParticles(ParticleTypes.SPLASH, (double)blockpos.getX() + level.random.nextDouble(), (double)(blockpos.getY() + 1), (double)blockpos.getZ() + level.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
        }
      }

      level.playSound(
        (Player)null, 
        blockpos, 
        SoundEvents.BOTTLE_EMPTY, 
        SoundSource.BLOCKS, 
        1.0F, 
        1.0F
      );
      
      level.gameEvent((Entity)null, GameEvent.FLUID_PLACE, blockpos);
      level.setBlockAndUpdate(
        blockpos, 
        ModBlocks.ENRICHED_MUD.get().defaultBlockState()
      );
    }
  }


  @SubscribeEvent
  public static void onAttachCapabilitiesPlayer(
    AttachCapabilitiesEvent<Entity> event
  ) {
    if(event.getObject() instanceof Player) {
      Boolean hasPlayerWithdrawalCap = event.getObject().getCapability(
        PlayerWithdrawalProvider.PLAYER_WITHDRAWAL
      ).isPresent();

      if(!hasPlayerWithdrawalCap) {
        ResourceLocation resourceLocation = new ResourceLocation(
          Spice.MOD_ID, 
          "properties"
        );
        
        event.addCapability(resourceLocation, new PlayerWithdrawalProvider());
      }
    }
  }

  @SubscribeEvent
  public static void onPlayerCloned(PlayerEvent.Clone event) {
    if(event.isWasDeath()) {
      event.getOriginal().getCapability(PlayerWithdrawalProvider.PLAYER_WITHDRAWAL).ifPresent(oldStore -> {
        event.getOriginal().getCapability(PlayerWithdrawalProvider.PLAYER_WITHDRAWAL).ifPresent(newStore -> {
          newStore.copyFrom(oldStore);
          newStore.resetAddiction(event.getEntity());
        });
      });
    }
  }


  @SubscribeEvent
  public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
    if(event.side != LogicalSide.SERVER) return;

    // Server Side
    event.player.getCapability(
      PlayerWithdrawalProvider.PLAYER_WITHDRAWAL
    ).ifPresent(withdrawal -> {
      withdrawal.updateWithdrawal(1, event.player);
    });
  }

  @SubscribeEvent
  public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
    if(!event.getLevel().isClientSide()) {
      Entity player = event.getEntity();
      if(player instanceof ServerPlayer) {
        player.getCapability(
          PlayerWithdrawalProvider.PLAYER_WITHDRAWAL
        ).ifPresent(withdrawal -> {
          withdrawal.resetAddiction(player);
        });
      }
    }
  }
}