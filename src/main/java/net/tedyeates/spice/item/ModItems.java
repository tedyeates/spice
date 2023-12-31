package net.tedyeates.spice.item;

import com.google.common.base.Suppliers;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tedyeates.spice.Spice;
import net.tedyeates.spice.effect.ModEffects;
import net.tedyeates.spice.item.custom.SpiceItem;

public class ModItems {
  public static final DeferredRegister<Item> ITEMS = 
    DeferredRegister.create(ForgeRegistries.ITEMS, Spice.MOD_ID);

  private static FoodProperties spiceFoodProperties = new FoodProperties
    .Builder()
    .nutrition(1)
    .saturationMod(1f)
    .alwaysEat() // Can eat even if not hungry
    .effect(Suppliers.memoize(() -> new MobEffectInstance(
      MobEffects.REGENERATION, 
      Spice.SPICE_REGEN_TIME,
      Spice.SPICE_REGEN_LEVEL
    )), 1.0F)
    .effect(Suppliers.memoize(() -> new MobEffectInstance(
      MobEffects.ABSORPTION, 
      Spice.SPICE_ABSORB_TIME,
      Spice.SPICE_ABSORB_LEVEL
    )), 1.0F)
    .effect(Suppliers.memoize(() -> new MobEffectInstance(
      ModEffects.FOCUS.get(), 
      Spice.SPICE_FOCUS_TIME,
      Spice.SPICE_FOCUS_LEVEL
    )), 1.0F)
    .build();

  public static final RegistryObject<Item> SPICE = ITEMS.register(
    "spice", 
    () -> new SpiceItem(new Item.Properties()
      .tab(ModCreativeModeTab.SPICE_TAB)
      .food(spiceFoodProperties)
    )
  );

  public static final RegistryObject<Item> UNREFINED_SPICE = ITEMS.register(
    "unrefined_spice", 
    () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SPICE_TAB))
  );


  public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }
}
