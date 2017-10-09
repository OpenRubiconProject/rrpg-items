package com.openrubicon.items.classes.sockets;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class SocketProvider {

    protected static LinkedHashMap<String, Socket> sockets = new LinkedHashMap<>();

    public static LinkedHashMap<String, Socket> getSockets()
    {
        return SocketProvider.sockets;
    }

    public static void add(Socket socket)
    {
        if(!SocketProvider.sockets.containsKey(socket.getKey()))
        {
            SocketProvider.sockets.put(socket.getKey(), socket);
        } else {
            Bukkit.getLogger().info("Failed to inject Socket " + socket.getName() + " because key " + socket.getKey() + " already exists.");
        }
    }

    public static void addAll(HashSet<Socket> sockets)
    {
        for(Socket socket : sockets)
        {
            SocketProvider.add(socket);
        }
    }

    public static void remove(Socket socket)
    {
        if(SocketProvider.getSockets().containsKey(socket.getKey()))
            SocketProvider.getSockets().remove(socket.getKey());
    }

    public static Socket get(int index)
    {
        if((index + 1) > SocketProvider.getSockets().size())
            return null;

        ArrayList<Socket> sockets = new ArrayList<>();
        sockets.addAll(SocketProvider.getSockets().values());

        return sockets.get(index);
    }

    public static void clear()
    {
        SocketProvider.sockets.clear();
    }

    public static ArrayList<Socket> getValidSockets(Material material)
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

}
