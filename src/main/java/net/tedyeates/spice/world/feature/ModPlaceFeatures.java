package net.tedyeates.spice.world.feature;

import java.util.List;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tedyeates.spice.Spice;

public class ModPlaceFeatures {
  public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
    DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Spice.MOD_ID);


  public static final RegistryObject<PlacedFeature> ENRICHED_ORE_PLACED =
    PLACED_FEATURES.register("enriched_ore_placed", ()-> new PlacedFeature(
      ModConfiguredFeatures.ENRICHED_ORE.getHolder().get(),
      commonOrePlacement(10, HeightRangePlacement.triangle(
        VerticalAnchor.absolute(-64), VerticalAnchor.absolute(50)
      ))
    ));

  public static final RegistryObject<PlacedFeature> SPICE_MUD_GEODE_PLACED = 
    PLACED_FEATURES.register(
      "spice_mud_geode_placed",
      () -> new PlacedFeature(
        ModConfiguredFeatures.SPICE_MUD_GEODE.getHolder().get(), 
        List.of(
          RarityFilter.onAverageOnceEvery(20), // Chance of geode placed
          InSquarePlacement.spread(),
          HeightRangePlacement.uniform(
            VerticalAnchor.aboveBottom(6), 
            VerticalAnchor.absolute(20)
          ),
          BiomeFilter.biome()
        )
      )
    );

  public static final RegistryObject<PlacedFeature> SPICE_GEODE_PLACED = 
    PLACED_FEATURES.register(
      "spice_geode_placed",
      () -> new PlacedFeature(
        ModConfiguredFeatures.SPICE_GEODE.getHolder().get(), 
        List.of(
          RarityFilter.onAverageOnceEvery(12), // Chance of geode placed
          InSquarePlacement.spread(),
          HeightRangePlacement.uniform(
            VerticalAnchor.aboveBottom(20), 
            VerticalAnchor.absolute(40)
          ),
          BiomeFilter.biome()
        )
      )
    );


  private static List<PlacementModifier> orePlacement(
    PlacementModifier p_195347_, 
    PlacementModifier p_195348_
  ) {
    return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
  }

  private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
    return orePlacement(CountPlacement.of(p_195344_), p_195345_);
  }

  public static void register(IEventBus eventBus) {
    PLACED_FEATURES.register(eventBus);
  }
}