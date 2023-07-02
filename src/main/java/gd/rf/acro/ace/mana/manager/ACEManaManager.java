package gd.rf.acro.ace.mana.manager;

import dev.louis.nebula.api.NebulaPlayer;
import dev.louis.nebula.mana.manager.ManaManager;
import dev.louis.nebula.networking.SynchronizeManaAmountS2CPacket;
import gd.rf.acro.ace.ACE;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class ACEManaManager implements dev.louis.nebula.mana.manager.ManaManager {
    PlayerEntity player;
    private int mana = 0;
    public ACEManaManager(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void tick() {
        //Implement mana regeneration
        //Should it draw Food?
    }

    @Override
    public void setMana(int mana) {
        setMana(mana, true);
    }

    public void setMana(int mana, boolean sendToPlayer) {
        this.mana = Math.max(Math.min(mana, getPlayerMaxMana()), 0);
        if(sendToPlayer) {
            sendSync();
        }
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public void addMana(int mana) {
        setMana(this.mana + mana);
    }

    @Override
    public void drainMana(int mana) {
        setMana(this.mana - mana);
    }

    @Override
    public int getPlayerMaxMana() {
        //TODO: Implement Spell Mastery system
        return player.getMaxMana();
    }

    //Should this be implemented?
    @Override
    public void setPlayerMaxMana(int mana) {
        return;
    }

    @Override
    public boolean sendSync() {
        if(this.player.getWorld().isClient() || ((ServerPlayerEntity)player).networkHandler == null)return false;
        var buf = PacketByteBufs.create();
        new SynchronizeManaAmountS2CPacket((NebulaPlayer.access(player)).getManaManager().getMana()).write(buf);

        ServerPlayNetworking.send(
                (ServerPlayerEntity) this.player,
                SynchronizeManaAmountS2CPacket.getId(),
                buf
        );
        return true;
    }

    @Override
    public boolean receiveSync(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        var packet = new SynchronizeManaAmountS2CPacket(buf);
        NebulaPlayer.access(player).getManaManager().setMana(packet.mana());
        return true;
    }


    @Override
    public void writeNbt(NbtCompound nbt) {
        NbtCompound ACENbt = nbt.getCompound(ACE.MOD_ID);
        ACENbt.putInt("Mana", this.getMana());
        nbt.put(ACE.MOD_ID, ACENbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        NbtCompound ACENbt = nbt.getCompound(ACE.MOD_ID);
        this.setMana(ACENbt.getInt("Mana"));
    }

    @Override
    public void copyFrom(PlayerEntity oldPlayer, boolean alive) {
        if(alive) {
            ManaManager oldManaManager = NebulaPlayer.access(oldPlayer).getManaManager();
            this.setMana(oldManaManager.getMana());
            //Should we support changing Max mana?
            this.setPlayerMaxMana(oldManaManager.getPlayerMaxMana());
        }
    }
}
