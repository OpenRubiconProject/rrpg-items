package com.openrubicon.items.classes.items.interfaces;

import com.openrubicon.items.classes.sockets.SocketHandler;

public interface Socketable {

    String SOCKETS = "RubiconItemSockets";
    String SEPERATOR = "SEPERATOR";

    SocketHandler getSocketHandler();

}
