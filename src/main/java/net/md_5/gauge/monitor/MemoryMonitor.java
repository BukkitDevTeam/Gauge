package net.md_5.gauge.monitor;

public class MemoryMonitor implements Runnable
{

    @Override
    public void run()
    {
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
    }
}
