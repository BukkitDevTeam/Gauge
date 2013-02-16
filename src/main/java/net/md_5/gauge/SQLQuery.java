package net.md_5.gauge;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface SQLQuery<T>
{

    public String sql();

    public void prepare(PreparedStatement ps);

    public T invoke(ResultSet rs);
}
