package net.md_5.gauge;

public class TPSGauger implements Runnable
{

    private long lastRun;

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
    }

    public void reset()
    {
    }

    public double getTps()
    {
        return 20;
    }
}
