package com.openrubicon.items.classes.sockets;

import com.openrubicon.core.api.interfaces.Generatable;
import com.openrubicon.core.api.interfaces.Loreable;
import com.openrubicon.core.api.interfaces.Observeable;
import com.openrubicon.core.api.interfaces.Persistable;
import com.openrubicon.core.api.nbt.Compound;
import com.openrubicon.core.api.nbt.NBT;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.items.classes.items.interfaces.Socketable;
import com.openrubicon.items.classes.items.specs.ItemSpecs;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

public class SocketHandler implements Persistable, Loreable, Observeable, Generatable{

    private LinkedHashMap<String, Socket> sockets = new LinkedHashMap<>();

    private ItemSpecs itemSpecs;

    private ItemStack item;

    private NBT nbt;

    public LinkedHashMap<String, Socket> getSockets() {
        return sockets;
    }

    public ItemSpecs getItemSpecs() {
        return itemSpecs;
    }

    public void setItemSpecs(ItemSpecs itemSpecs) {
        this.itemSpecs = itemSpecs;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public NBT getNbt() {
        return nbt;
    }

    public void setNbt(NBT nbt) {
        this.nbt = nbt;
    }

    @Override
    public boolean generate()
    {
        for(int z = 0; z < this.getItemSpecs().getSockets(); z++)
        {
            Socket socket = SocketFactory.random(this.item.getType());
            if(socket == null)
                continue;
            if(!socket.generate())
                return false;
            this.add(socket);
        }
        return true;
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        for(Socket socket : sockets.values())
        {
            lore.addAll(socket.getLore());
        }
        return Helpers.colorize(lore);
    }

    @Override
    public ArrayList<String> getObservation() {
        ArrayList<String> view = new ArrayList<>();
        int count = 1;
        for(Socket socket : sockets.values())
        {
            view.add("&6Socket "+count);
            view.addAll(socket.getObservation());
            view.add("");
            count++;
        }
        return Helpers.colorize(view);
    }

    @Override
    public boolean save()
    {
        Compound socketComp = this.nbt.addCompound(Socketable.SOCKETS);

        int count = 0;
        for(Socket socket : sockets.values())
        {
            if(!socket.save())
                return false;
            socketComp.setString(count+Socketable.SEPERATOR+socket.getKey(), socket.getSocketProperties().getPersistenceString());
            count++;
        }
        return true;
    }

    @Override
    public boolean load() {
        Compound socketComp = this.nbt.getCompound(Socketable.SOCKETS);

        Set<String> sockets = socketComp.getKeys();

        if (sockets.size() == 0)
            return true;

        ArrayList<Socket> socketOrder = new ArrayList<>();
        while(socketOrder.size() < 11) socketOrder.add(null);

        for (String s : sockets) {
            if (!socketComp.hasKey(s))
                continue;

            String[] idAndKey = s.split(Socketable.SEPERATOR);
            String key = idAndKey[1];
            int id = Integer.parseInt(idAndKey[0]);

            String loadStr = socketComp.getString(s);

            Socket socketClass = SocketFactory.keyToSocket(key);
            if (socketClass == null)
                continue;

            socketClass.getSocketProperties().setPersistenceString(loadStr);

            socketClass.load();

            socketOrder.set(id, socketClass);
        }

        socketOrder.trimToSize();

        for(Socket socket : socketOrder)
        {
            if(socket != null)
                this.add(socket);
        }

        return true;
    }

    public void removeAll()
    {
        this.sockets.clear();
    }

    public void remove(String socket)
    {
        this.sockets.remove(socket);
    }

    public void remove(Socket socket)
    {
        this.sockets.remove(socket.getKey());
    }

    public void add(Socket socket)
    {
        this.sockets.put(socket.getKey(), socket);
    }

    public Socket get(String socket)
    {
        return this.sockets.get(socket);
    }

    public Socket get(Socket socket)
    {
        return this.get(socket.getKey());
    }

    public boolean has(String socket)
    {
        return this.sockets.containsKey(socket);
    }

    public boolean has(Socket socket)
    {
        return this.has(socket.getKey());
    }

    public boolean isPartiallyObfuscated()
    {
        for(Socket socket : sockets.values())
        {
            if(socket.isObfuscated())
                return true;
        }

        return false;
    }
}
