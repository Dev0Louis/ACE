package gd.rf.acro.ace.spells.manager;

import dev.louis.nebula.spell.Spell;
import dev.louis.nebula.spell.SpellType;
import dev.louis.nebula.spell.manager.SpellManager;
import gd.rf.acro.ace.items.IRenderableCastingDevice;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class ACESpellManager implements SpellManager {
    LivingEntity entity;
    public ACESpellManager(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean addSpell(SpellType<? extends Spell> spellType) {
        final ItemStack itemStack = entity.getActiveItem();
        if(itemStack.getItem() instanceof IRenderableCastingDevice renderableCastingDevice) {
            renderableCastingDevice.addSpell(itemStack, spellType);
            return true;
        };
    }

    @Override
    public boolean removeSpell(SpellType<? extends Spell> spellType) {
        final ItemStack itemStack = entity.getActiveItem();
        if(itemStack.getItem() instanceof IRenderableCastingDevice renderableCastingDevice) {
            renderableCastingDevice.removeSpell(itemStack, spellType);
            return true;
        }
    }

    @Override
    public void cast(PlayerEntity player, SpellType spellType) {
        cast(spellType.create(player));
    }

    @Override
    public void cast(Spell spell) {
        spell.cast();
    }

    @Override
    public void copyFrom(ServerPlayerEntity serverPlayerEntity, boolean alive) {
        //We do not need this right?
    }

    @Override
    public boolean canCast(SpellType<? extends Spell> spellType) {
        final ItemStack itemStack = entity.getActiveItem();
        if(itemStack.getItem() instanceof IRenderableCastingDevice renderableCastingDevice) {
            return renderableCastingDevice.getEquipped(itemStack) == spellType;
        }
    }

    @Override
    public boolean sendSync() {
        return false;
    }

    @Override
    public boolean receiveSync(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        return false;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbtCompound) {
        return null;
    }

    @Override
    public void readNbt(NbtCompound nbtCompound) {

    }
}
