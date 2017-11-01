package com.openrubicon.items.classes.scoreboard;

import com.openrubicon.core.api.enums.Priority;
import com.openrubicon.core.api.scoreboard.interfaces.ScoreboardSection;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.items.classes.sockets.CooldownSocket;
import com.openrubicon.items.classes.sockets.cooldowns.SocketCooldownManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Cooldowns implements ScoreboardSection {
    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }

    @Override
    public String getTitle() {
        return "Socket Cooldowns";
    }



    @Override
    public ArrayList<String> getLines(Player player) {
        ArrayList<String> cooldowns = new ArrayList<>();

        if(SocketCooldownManager.getEntitiesSocketCooldowns().get(player) == null)
            return cooldowns;

        for(CooldownSocket socket : SocketCooldownManager.getEntitiesSocketCooldowns().get(player).getSockets())
        {
            if(socket.getCooldown().isOnCooldown())
            {
                cooldowns.add(Constants.BOLD + Constants.HEADING_COLOR + socket.getName() + ": " + Constants.RESET_FORMAT + Constants.RED + socket.getCooldown().getCooldownSeconds() + "s");
            }
        }
        return cooldowns;
    }
}
