package net.tedyeates.spice.world.feature;

import java.util.List;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tedyeates.spice.Spice;
import net.tedyeates.spice.block.ModBlocks;

public class ModConfiguredFeatures {
  public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = 
    DeferredRegister.create(
      Registry.CONFIGURED_FEATURE_REGISTRY, 
      Spice.MOD_ID
    );

  public static final Supplier<List<OreConfiguration.TargetBlockState>> ENRICHED_ORES =
    Suppliers.memoize(() -> List.of(
      OreConfiguration.target(
        new BlockMatchTest(Blocks.SAND), 
        ModBlocks.ENRICHED_SAND.get().defaultBlockState()
      ),
      OreConfiguration.target(
        new BlockMatchTest(Blocks.MUD), 
        ModBlocks.ENRICHED_MUD.get().defaultBlockState()
      )
    ));
  
    public static final RegistryObject<ConfiguredFeature<?, ?>> ENRICHED_ORE = 
      CONFIGURED_FEATURES.register(
        "enriched_ore",
        () -> new ConfiguredFeature<>(
          Feature.ORE, 
          new OreConfiguration(ENRICHED_ORES.get(),10)
        )
      );


  public static void register(IEventBus eventBus) {
    CONFIGURED_FEATURES.register(eventBus);
  }
}
