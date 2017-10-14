package com.openrubicon.items.recipes.anvil;

import com.openrubicon.core.api.costs.Cost;
import com.openrubicon.core.api.costs.XPCost;
import com.openrubicon.core.api.inventory.Inventory;
import com.openrubicon.core.api.inventory.blocks.AnvilInventory;
import com.openrubicon.core.api.inventory.blocks.enums.AnvilSlotType;
import com.openrubicon.core.api.recipes.interfaces.AnvilRecipe;
import com.openrubicon.items.classes.durability.Durability;
import com.openrubicon.items.classes.items.orbs.OrbItem;
import com.openrubicon.items.classes.items.orbs.types.SocketOrb;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SocketOrbToItem implements AnvilRecipe {

    private AnvilInventory inventory;

    private ItemStack left;
    private ItemStack right;

    @Override
    public Cost getCost() {
        OrbItem orbItem = new OrbItem(left);
        UniqueItem uniqueItem = new UniqueItem(right);

        XPCost cost = new XPCost(orbItem.getCostModifier() * uniqueItem.getCostModifier());

        return cost;
    }

    @Override
    public void setInventory(Inventory inventory) {
        if(inventory instanceof AnvilInventory)
            this.inventory = (AnvilInventory)inventory;

        left = inventory.getItemInSlot(AnvilSlotType.LEFT_INGREDIENT);
        right = inventory.getItemInSlot(AnvilSlotType.RIGHT_INGREDIENT);
    }

    @Override
    public boolean isRecipe() {
        if(right == null || left == null)
            return false;

        OrbItem orbItem = new OrbItem(left);
        UniqueItem uniqueItem = new UniqueItem(right);

        if(!orbItem.isValid() || !orbItem.isSpecialItem())
            return false;

        if(!uniqueItem.isValid() || !uniqueItem.isSpecialItem())
            return false;

        if(!orbItem.isRightItemType() || !uniqueItem.isRightItemType())
            return false;

        if(!(orbItem.getOrb() instanceof SocketOrb))
            return false;

        SocketOrb socketOrb = (SocketOrb)orbItem.getOrb();

        if(socketOrb.getSocketHandler().getSockets().entrySet().iterator().next().getValue().isObfuscated())
            return false;

        if(uniqueItem.getSocketHandler().isPartiallyObfuscated())
            return false;

        if(uniqueItem.getSocketHandler().getSockets().size() >= uniqueItem.getItemSpecs().getSockets())
            return false;

        boolean isSocketsValid = false;
        for(Socket socket : socketOrb.getSocketHandler().getSockets().values())
        {
            if(socket.getMaterials().contains(uniqueItem.getItem().getType()))
                isSocketsValid = true;
        }
        if(!isSocketsValid)
            return false;

        return true;
    }

    @Override
    public ItemStack getResult() {

        OrbItem orbItem = new OrbItem(left);
        UniqueItem uniqueItem = new UniqueItem(right);

        SocketOrb socketOrb = (SocketOrb)orbItem.getOrb();

        UniqueItem result = new UniqueItem(uniqueItem);
        for(Socket socket : socketOrb.getSocketHandler().getSockets().values())
        {
            if(socket.getMaterials().contains(result.getItem().getType()))
                result.getSocketHandler().add(socket);
        }
        result.save();

        ItemStack resultItem = result.getItem();

        Durability durability = new Durability(resultItem);
        durability.adjustDurability(0);
        resultItem = durability.getItem();

        ItemMeta meta = resultItem.getItemMeta();
        meta.setLore(result.getLore());
        resultItem.setItemMeta(meta);

        return resultItem;
    }

    @Override
    public ItemStack takeResult(Player player) {
        ItemStack result = this.getResult();
        player.setItemOnCursor(result);
        inventory.clear();
        return result;
    }
}
