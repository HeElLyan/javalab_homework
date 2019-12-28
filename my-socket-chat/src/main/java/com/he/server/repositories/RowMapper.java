package com.he.server.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {

    T rowMap(ResultSet set) throws SQLException;

}
