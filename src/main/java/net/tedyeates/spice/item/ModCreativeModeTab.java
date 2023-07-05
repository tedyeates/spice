package net.tedyeates.spice.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab SPICE_TAB = new CreativeModeTab("spice") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SPICE.get());
        }
    };
}
