package gd.rf.acro.ace.spells;

import gd.rf.acro.ace.ACE;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

public class WaterTrapSpell extends SpellACE {
    @Override
    public String getCastingType() {
        return "tap";
    }

    @Override
    public Element getElement() {
        return "water";
    }

    @Override
    public int getTier() {
        return 0;
    }

    @Override
    public int getManaCost() {
        return 10;
    }

    @Override
    public void onTapBlock(LivingEntity caster, BlockPos tapped) {
        if(caster.world.getBlockState(tapped.up()).isAir())
        {
            caster.world.setBlockState(tapped.up(), ACE.WATER_TRAP_BLOCK.getDefaultState());
        }
    }
}
