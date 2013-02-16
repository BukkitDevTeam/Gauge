package net.md_5.gauge.monitor;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayersMonitor implements Runnable
{

    private final Set<String> seen = new HashSet<>();

    @Override
    public void run()
    {
        Player[] players = Bukkit.getServer().getOnlinePlayers();
        Set<String> joined = new HashSet<>();
        Set<String> left = new HashSet<>( seen );

        for ( Player player : players )
        {
            String name = player.getName();
            if ( !seen.contains( name ) )
            {
                joined.add( name );
            }
            left.remove( name );
        }

        int joinedCount = joined.size();
        int leftCount = left.size();
        int onlineCount = players.length;
    }
}
