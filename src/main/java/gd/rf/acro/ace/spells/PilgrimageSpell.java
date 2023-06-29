package gd.rf.acro.ace.spells;

import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.StructureTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PilgrimageSpell extends SpellACE {
    @Override
    public String getCastingType() {
        return "tap";
    }

    @Override
    public Element getElement() {
        return EARTH
    }

    @Override
    public int getTier() {
        return 1;
    }

    @Override
    public int getManaCost() {
        return 5;
    }

    @Override
    public void onTapBlock(LivingEntity caster, BlockPos tapped) {
        super.onTapBlock(caster, tapped);
        if(!caster.world.isClient)
        {
            ServerWorld world = (ServerWorld) caster.getEntityWorld();
            BlockPos pos = world.locateStructure(StructureTags.RUINED_PORTAL,tapped,1000,false);

            if(pos!=null)
            {
                caster.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, Vec3d.ofCenter(pos));
            }

        }
    }
}
