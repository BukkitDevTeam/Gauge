package net.md_5.gauge.monitor;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

public class ChunksMonitor implements Runnable
{

    @Override
    public void run()
    {
        for ( World world : Bukkit.getServer().getWorlds() )
        {
            Chunk[] chunks = world.getLoadedChunks();
        }
    }
}
