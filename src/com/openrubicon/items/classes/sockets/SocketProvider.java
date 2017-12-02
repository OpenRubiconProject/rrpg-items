package com.openrubicon.items.classes.sockets;

import com.openrubicon.core.api.services.interfaces.Service;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class SocketProvider implements Service {

    protected static LinkedHashMap<String, Socket> sockets = new LinkedHashMap<>();

    public static LinkedHashMap<String, Socket> getSockets()
    {
        return SocketProvider.sockets;
    }

    public void add(Socket socket)
    {
        if(!SocketProvider.sockets.containsKey(socket.getKey()))
        {
            SocketProvider.sockets.put(socket.getKey(), socket);
        } else {
            Bukkit.getLogger().info("Failed to inject Socket " + socket.getName() + " because key " + socket.getKey() + " already exists.");
        }
    }

    public void addAll(HashSet<Socket> sockets)
    {
        for(Socket socket : sockets)
        {
            this.add(socket);
        }
    }

    public void remove(Socket socket)
    {
        if(SocketProvider.getSockets().containsKey(socket.getKey()))
            SocketProvider.getSockets().remove(socket.getKey());
    }

    public Socket get(int index)
    {
        if((index + 1) > SocketProvider.getSockets().size())
            return null;

        ArrayList<Socket> sockets = new ArrayList<>();
        sockets.addAll(SocketProvider.getSockets().values());

        return sockets.get(index);
    }

    public void clear()
    {
        SocketProvider.sockets.clear();
    }

    public ArrayList<Socket> getValidSockets(Material material)
    {
        ArrayList<Socket> validSockets = new ArrayList<>();
        for(Socket socket : SocketProvider.getSockets().values())
        {
            if(socket.getMaterials().size() < 1)
                validSockets.add(socket);

            if(socket.getMaterials().contains(material))
                validSockets.add(socket);
        }
        return validSockets;
    }

    @Override
    public ArrayList<String> getObservation() {
        ArrayList<String> observation = new ArrayList<>();
        for(Socket socket : SocketProvider.getSockets().values())
        {
            observation.add(socket.getName());
        }
        return observation;
    }

}
