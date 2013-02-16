package net.md_5.gauge;

import net.md_5.gauge.monitor.TPSMonitor;
import net.md_5.gauge.monitor.ChunksMonitor;
import net.md_5.gauge.monitor.PlayersMonitor;
import net.md_5.gauge.monitor.EntitiesMonitor;
import net.md_5.gauge.monitor.MemoryMonitor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Server;
import org.hyperic.sigar.Sigar;
import org.mcstats.Metrics;

public final class Gauge extends JavaPlugin
{

    @Getter
    private static Gauge instance;
    private static final int TICKS_SECOND = 20;
    private static final int TICKS_MINUTE = 20 * 60;
    /*========================================================================*/
    private Server server;
    @Getter
    private final Sigar sigar = new Sigar();
    @Getter
    private final long pid = sigar.getPid();
    /*========================================================================*/
    private final ChunksMonitor chunksMonitor = new ChunksMonitor();
    private final EntitiesMonitor entitiesMonitor = new EntitiesMonitor();
    private final MemoryMonitor memoryMonitor = new MemoryMonitor();
    private final PlayersMonitor playersMonitor = new PlayersMonitor();
    private final TPSMonitor tpsMonitor = new TPSMonitor();
    @Getter
    private final TPSGauger tpsGauger = new TPSGauger();

    public <T> T sql(SQLQuery<T> query) throws SQLException
    {
        try ( Connection con = DriverManager.getConnection( "jdbc:sqlite:plugins/Gauge/database.sqlite" ) )
        {
            try ( PreparedStatement ps = con.prepareStatement( query.sql() ) )
            {
                query.prepare( ps );
                try ( ResultSet rs = ps.executeQuery() )
                {
                    return query.invoke( rs );
                }
            }
        }
    }

    @Override
    public void onLoad()
    {
        instance = this;
    }

    @Override
    public void onEnable()
    {
        try
        {
            Class.forName( "org.sqlite.JDBC" );
        } catch ( ClassNotFoundException ex )
        {
            getLogger().log( Level.SEVERE, "Could not connect to SQLite database", ex );
            getServer().getPluginManager().disablePlugin( this );
            return;
        }

        System.setProperty( "org.eclipse.jetty.LEVEL", "OFF" );
        InetSocketAddress bind = new InetSocketAddress( 5555 );
        server = new Server( bind );
        try
        {
            server.start();
        } catch ( Exception ex )
        {
            getLogger().log( Level.SEVERE, "Could not start intergrated web server", ex );
        }

        try
        {
            Metrics metrics = new Metrics( this );
            metrics.start();
        } catch ( IOException ex )
        {
        }

        getServer().getScheduler().runTaskTimer( this, chunksMonitor, TICKS_MINUTE, TICKS_MINUTE );
        getServer().getScheduler().runTaskTimer( this, entitiesMonitor, TICKS_MINUTE, TICKS_MINUTE );
        getServer().getScheduler().runTaskTimer( this, memoryMonitor, TICKS_MINUTE, TICKS_MINUTE );
        getServer().getScheduler().runTaskTimer( this, playersMonitor, TICKS_MINUTE, TICKS_MINUTE );
        getServer().getScheduler().runTaskTimer( this, tpsMonitor, TICKS_MINUTE, TICKS_MINUTE );

        getServer().getScheduler().runTaskTimer( this, tpsGauger, TICKS_SECOND, TICKS_SECOND );
    }

    @Override
    public void onDisable()
    {
        try
        {
            server.stop();
        } catch ( Exception ex )
        {
            getLogger().log( Level.SEVERE, "Could not stop intergrated web server", ex );
        }

        instance = null;
    }
}
