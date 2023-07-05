package net.tedyeates.spice.block;

import java.util.function.Supplier;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tedyeates.spice.Spice;
import net.tedyeates.spice.block.custom.EnrichedSand;
import net.tedyeates.spice.item.ModCreativeModeTab;
import net.tedyeates.spice.item.ModItems;

public class ModBlocks {
  public static final DeferredRegister<Block> BLOCKS = 
      DeferredRegister.create(ForgeRegistries.BLOCKS, Spice.MOD_ID);


  public static final RegistryObject<Block> ENRICHED_SAND = registerBlock(
    "enriched_sand",
    () -> new EnrichedSand(
      BlockBehaviour.Properties.of(Material.SAND)
        .strength(1f).requiresCorrectToolForDrops()
        .sound(SoundType.SAND)
    ), 
    ModCreativeModeTab.SPICE_TAB
  );

  public static final RegistryObject<Block> ENRICHED_MUD = registerBlock(
    "enriched_mud",
    () -> new Block(
      BlockBehaviour.Properties.of(Material.CLAY)
        .strength(1f).requiresCorrectToolForDrops()
        .sound(SoundType.MUD)
    ), 
    ModCreativeModeTab.SPICE_TAB
  );

  public static final RegistryObject<Block> SPICE_BLOCK = registerBlock(
    "spice_block",
    () -> new Block(
      BlockBehaviour.Properties.of(Material.SAND)
        .strength(0.5f)
        .requiresCorrectToolForDrops()
        .sound(SoundType.SAND)
    ), 
    ModCreativeModeTab.SPICE_TAB
  );


  private static <T extends Block> RegistryObject<T> registerBlock(
    String name, 
    Supplier<T> block, 
    CreativeModeTab tab
  ) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn, tab);
    return toReturn;
  }

  private static <T extends Block> RegistryObject<BlockItem> registerBlockItem (
    String name, 
    RegistryObject<T> block, 
    CreativeModeTab tab
  ) {
    return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
  }
  
  public static void register(IEventBus eventBus) {
    BLOCKS.register(eventBus);
  }
}
