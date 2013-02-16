package net.md_5.gauge.monitor;

import java.util.List;
import net.md_5.gauge.ValueCounter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntitiesMonitor implements Runnable
{

    @Override
    public void run()
    {
        for ( World world : Bukkit.getServer().getWorlds() )
        {
            List<Entity> entities = world.getEntities();
            ValueCounter<EntityType> types = new ValueCounter<>();
            for ( Entity entity : entities )
            {
                types.add( entity.getType() );
            }
        }
    }
}
