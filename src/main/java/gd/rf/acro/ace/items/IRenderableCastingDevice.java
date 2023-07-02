package gd.rf.acro.ace.items;

import dev.louis.nebula.spell.SpellType;
import gd.rf.acro.ace.spells.SpellACE;
import gd.rf.acro.ace.spells.SpellTypeACE;
import net.minecraft.item.ItemStack;

public interface IRenderableCastingDevice {
    SpellType<?> getEquipped(ItemStack stack);
    void scrollMinus(ItemStack stack);
    void scrollPlus(ItemStack stack);
    void addSpell(ItemStack stack, SpellType<?>... spell);
    void removeSpell(ItemStack stack, SpellType<?> spell);
}
