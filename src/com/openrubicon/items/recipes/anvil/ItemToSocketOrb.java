package com.openrubicon.items.recipes.anvil;

import com.openrubicon.core.api.costs.Cost;
import com.openrubicon.core.api.costs.XPCost;
import com.openrubicon.core.api.inventory.Inventory;
import com.openrubicon.core.api.inventory.blocks.AnvilInventory;
import com.openrubicon.core.api.inventory.blocks.enums.AnvilSlotType;
import com.openrubicon.core.api.recipes.interfaces.AnvilRecipe;
import com.openrubicon.items.classes.durability.Durability;
import com.openrubicon.items.classes.items.orbs.OrbFactory;
import com.openrubicon.items.classes.items.orbs.OrbItem;
import com.openrubicon.items.classes.items.orbs.types.Orb;
import com.openrubicon.items.classes.items.orbs.types.SocketOrb;
import com.openrubicon.items.classes.items.specs.ItemSpecs;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class ItemToSocketOrb implements AnvilRecipe {

    private AnvilInventory inventory;

    private ItemStack left;
    private ItemStack right;

    @Override
    public Cost getCost() {
        UniqueItem uniqueItem = new UniqueItem(left);

        XPCost cost = new XPCost(uniqueItem.getCostModifier() * uniqueItem.getCostModifier());

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

        UniqueItem uniqueItem = new UniqueItem(left);

        if(!uniqueItem.isValid() || !uniqueItem.isSpecialItem())
            return false;

        if(!uniqueItem.isRightItemType())
            return false;

        if(uniqueItem.getSocketHandler().getSockets().size() < 1)
            return false;

        OrbItem orbItem = new OrbItem();
        orbItem.setOrb(new SocketOrb());

        if(!orbItem.getOrb().getMaterials().contains(right.getType()))
            return false;

        return true;
    }

    @Override
    public ItemStack getResult() {

        UniqueItem uniqueItem = new UniqueItem(left);

        Map.Entry<String, Socket> entry = uniqueItem.getSocketHandler().getSockets().entrySet().iterator().next();
        Socket socket = entry.getValue();
        ItemSpecs specs = uniqueItem.getItemSpecs();

        SocketOrb socketOrb = new SocketOrb();
        socketOrb.setItem(new ItemStack(right.getType()));
        socketOrb.setItemSpecs(specs);
        //((Orb)socketOrb).generate();
        socketOrb.generateUUID();

        socketOrb.getSocketHandler().add(socket);

        OrbItem orbItem = new OrbItem(socketOrb);

        orbItem.save();

        ItemStack orb = orbItem.getItem();

        ItemMeta meta = orb.getItemMeta();
        meta.setLore(orbItem.getLore());
        meta.setDisplayName(orbItem.getRarity().getColoredName() + " Socket Orb");
        orb.setItemMeta(meta);

        return orb;
    }

    @Override
    public ItemStack takeResult(Player player) {
        UniqueItem uniqueItem = new UniqueItem(left);

        ItemStack result = this.getResult();

        player.setItemOnCursor(result);

        Map.Entry<String, Socket> entry = uniqueItem.getSocketHandler().getSockets().entrySet().iterator().next();
        uniqueItem.getSocketHandler().remove(entry.getValue());
        uniqueItem.save();

        ItemStack originalItem = uniqueItem.getItem();

        Durability durability = new Durability(originalItem);
        durability.adjustDurability(0);

        originalItem = durability.getItem();

        ItemMeta meta = originalItem.getItemMeta();
        meta.setLore(uniqueItem.getLore());
        originalItem.setItemMeta(meta);

        if(right.getAmount() > 1)
        {
            right.setAmount(right.getAmount() - 1);
        } else {
            right = null;
        }

        ItemStack itemStack = null;

        if(right != null)
        {
            itemStack = new ItemStack(right.getType());
            itemStack.setAmount(right.getAmount());
        }


        inventory.clear();

        inventory.setSlotItem(AnvilSlotType.LEFT_INGREDIENT, originalItem);

        if(itemStack != null)
        {
            inventory.setSlotItem(AnvilSlotType.RIGHT_INGREDIENT, itemStack);
        }

        return result;
    }
}
