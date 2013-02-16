package net.md_5.gauge.monitor;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class EntitiesMonitor implements Runnable
{

    @Override
    public void run()
    {
        for ( World world : Bukkit.getServer().getWorlds() )
        {
            List<Entity> entities = world.getEntities();
        }
    }
}
