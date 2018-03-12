package com.horrific.sqlbuilder;


import com.horrific.sqlbuilder.select.RowMapper;

import java.sql.SQLException;
import java.util.List;

public interface TerminalExpression {

	<E> List<E> list(RowMapper<E> rowMapper) throws SQLException;

	<E> E single(RowMapper<E> rowMapper) throws SQLException;
}