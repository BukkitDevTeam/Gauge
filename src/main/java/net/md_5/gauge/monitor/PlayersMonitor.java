package net.md_5.gauge.monitor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayersMonitor implements Runnable
{

    @Override
    public void run()
    {
        Player[] players = Bukkit.getServer().getOnlinePlayers();
    }
}
