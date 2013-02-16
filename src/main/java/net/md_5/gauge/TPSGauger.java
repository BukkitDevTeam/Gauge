package net.md_5.gauge;

import java.util.ArrayList;
import java.util.List;

public class TPSGauger implements Runnable
{

    private long lastRun;
    private final List<Long> deltas = new ArrayList<>();

    @Override
    public void run()
    {
        long currentTime = System.currentTimeMillis();
        if ( lastRun == 0 )
        {
            lastRun = currentTime;
            return;
        }

        long delta = currentTime - lastRun;
        deltas.add( delta );
        lastRun = currentTime;
    }

    public void reset()
    {
        deltas.clear();
    }

    public double getTps()
    {
        int sum = 0;
        for ( long delta : deltas )
        {
            sum += delta;
        }
        return ( deltas.size() == 0 ) ? 0 : (double) sum / (double) deltas.size();
    }
}
