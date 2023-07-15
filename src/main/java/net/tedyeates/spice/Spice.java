package net.tedyeates.spice;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tedyeates.spice.block.ModBlocks;
import net.tedyeates.spice.item.ModItems;
import net.tedyeates.spice.effect.ModEffects;
import net.tedyeates.spice.networking.ModMessages;
import net.tedyeates.spice.world.feature.ModConfiguredFeatures;
import net.tedyeates.spice.world.feature.ModPlaceFeatures;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Spice.MOD_ID)
public class Spice
{
  // Define mod id in a common place for everything to reference
  public static final String MOD_ID = "spice";

  // Configurable
  public static final int SPICE_FOCUS_MINUTES = 3;
  public static final int SPICE_ABSORB_MINUTES = 2;
  public static final int SPICE_ABSORB_SECONDS = 30;
  public static final int SPICE_REGEN_SECONDS = 5;
  
  public static final int SPICE_REGEN_LEVEL = 1;
  public static final int SPICE_ABSORB_LEVEL = 1;
  
  public static final float ADDICTION_CHANCE = 0.6F;

  public static final int SPICE_FOCUS_RADIUS = 10;

  public static final int SECOND_TICKS = 20;
  public static final int MINUTE_SECONDS = 60;

  public static final int SPICE_FOCUS_TIME = 
    SPICE_FOCUS_MINUTES * MINUTE_SECONDS *  SECOND_TICKS;
  public static final int SPICE_REGEN_TIME = 
    SPICE_REGEN_SECONDS * SECOND_TICKS;
  public static final int SPICE_ABSORB_TIME = 
    SPICE_ABSORB_MINUTES * MINUTE_SECONDS * SECOND_TICKS + 
    SPICE_ABSORB_SECONDS * SECOND_TICKS;

  // Directly reference a slf4j logger
  private static final Logger LOGGER = LogUtils.getLogger();

  public Spice()
  {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    ModItems.register(modEventBus);
    ModBlocks.register(modEventBus);

    ModEffects.register(modEventBus);

    ModConfiguredFeatures.register(modEventBus);
    ModPlaceFeatures.register(modEventBus);

    // Register the commonSetup method for modloading
    modEventBus.addListener(this::commonSetup);
    

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);
  }

  private void commonSetup(final FMLCommonSetupEvent event)
  {
    event.enqueueWork(() -> {
      ModMessages.register();
    });
  }

  // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
  @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
  public static class ClientModEvents
  {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {

    }
  }
}
