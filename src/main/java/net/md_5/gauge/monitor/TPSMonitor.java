package net.md_5.gauge.monitor;

import net.md_5.gauge.Gauge;

public class TPSMonitor implements Runnable
{

    @Override
    public void run()
    {
        double tps = Gauge.getInstance().getTpsGauger().getTps();
        Gauge.getInstance().getTpsGauger().reset();
    }
}
