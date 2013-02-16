package net.md_5.gauge;

import java.util.HashMap;
import java.util.Map;

public class ValueCounter<E>
{

    private final Map<E, Integer> backing = new HashMap<>();

    public void add(E element)
    {
        backing.put( element, get( element ) + 1 );
    }

    public void remove(E element)
    {
        backing.put( element, get( element ) - 1 );
    }

    public int get(E key)
    {
        Integer val = backing.get( key );
        return ( val == null ) ? 0 : val;
    }
}
