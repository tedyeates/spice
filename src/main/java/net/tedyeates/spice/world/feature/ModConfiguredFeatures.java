package net.tedyeates.spice.world.feature;

import java.util.List;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
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
          new OreConfiguration(ENRICHED_ORES.get(),6)
        )
      );

  public static final RegistryObject<ConfiguredFeature<?, ?>> SPICE_MUD_GEODE = 
    CONFIGURED_FEATURES.register(
      "spice_mud_geode",
      () -> new ConfiguredFeature<>(
        Feature.GEODE,
        new GeodeConfiguration(
          new GeodeBlockSettings(
            BlockStateProvider.simple(Blocks.WATER),
            BlockStateProvider.simple(Blocks.MUD),
            BlockStateProvider.simple(ModBlocks.ENRICHED_MUD.get()),
            BlockStateProvider.simple(Blocks.SAND),
            BlockStateProvider.simple(Blocks.SAND),
            List.of(
              ModBlocks.IRON_MUD_ORE.get().defaultBlockState()
            ),
            BlockTags.FEATURES_CANNOT_REPLACE, 
            BlockTags.GEODE_INVALID_BLOCKS
          ),
          new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 6.2D),
          new GeodeCrackSettings(0.30D, 2.0D, 2), 
          0.25D, // Placement Chance
          0.033D, // Alternate layer chance
          true, // Placement Requires Layer Alternate
          UniformInt.of(3, 6), // Outer Wall Distance
          UniformInt.of(3, 4), // Distribution points
          UniformInt.of(1, 2), // Point Offset
          -18, // Minimum Generation Offset
          18, // Maximum Generation Offset
          0.055D, // Noise Multiplier
          1 // Invalid Blocks Threshold
        )
      )
    );

  public static final RegistryObject<ConfiguredFeature<?, ?>> SPICE_GEODE = 
    CONFIGURED_FEATURES.register(
      "spice_geode",
      () -> new ConfiguredFeature<>(
        Feature.GEODE,
        new GeodeConfiguration(
          new GeodeBlockSettings(
            BlockStateProvider.simple(Blocks.AIR),
            BlockStateProvider.simple(Blocks.SANDSTONE),
            BlockStateProvider.simple(ModBlocks.ENRICHED_SAND.get()),
            BlockStateProvider.simple(Blocks.SAND),
            BlockStateProvider.simple(Blocks.SAND),
            List.of(
              ModBlocks.IRON_SAND_ORE.get().defaultBlockState()
            ),
            BlockTags.FEATURES_CANNOT_REPLACE, 
            BlockTags.GEODE_INVALID_BLOCKS
          ),
          new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 6.2D),
          new GeodeCrackSettings(0.30D, 2.0D, 2), 
          0.25D, // Placement Chance
          0.033D, // Alternate layer chance
          true, // Placement Requires Layer Alternate
          UniformInt.of(3, 6), // Outer Wall Distance
          UniformInt.of(3, 4), // Distribution points
          UniformInt.of(1, 2), // Point Offset
          -18, // Minimum Generation Offset
          18, // Maximum Generation Offset
          0.055D, // Noise Multiplier
          1 // Invalid Blocks Threshold
        )
      )
    );

  public static void register(IEventBus eventBus) {
    CONFIGURED_FEATURES.register(eventBus);
  }
}
