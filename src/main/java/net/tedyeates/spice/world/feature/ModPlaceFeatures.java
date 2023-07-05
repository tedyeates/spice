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
  public static final DeferredRegister<PlacedFeature> PLACED_FEATURE =
    DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Spice.MOD_ID);

  public static final RegistryObject<PlacedFeature> ENRICHED_ORE_PLACED_HIGH =
    PLACED_FEATURE.register("enriched_ore_placed_high", ()-> new PlacedFeature(
      ModConfiguredFeatures.ENRICHED_ORE.getHolder().get(),
      commonOrePlacement(2, HeightRangePlacement.triangle(
        VerticalAnchor.absolute(70), VerticalAnchor.absolute(120)
      ))
    ));


  public static final RegistryObject<PlacedFeature> ENRICHED_ORE_PLACED =
    PLACED_FEATURE.register("enriched_ore_placed", ()-> new PlacedFeature(
      ModConfiguredFeatures.ENRICHED_ORE.getHolder().get(),
      commonOrePlacement(30, HeightRangePlacement.triangle(
        VerticalAnchor.absolute(-64), VerticalAnchor.absolute(56)
      ))
    ));


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
    PLACED_FEATURE.register(eventBus);
  }
}