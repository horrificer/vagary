package com.horrific.sql.statement.function;

import com.horrific.sql.statement.Select;

public class Min extends AggregateColumn {

	public Min(String name) {
		super("MIN", name);
	}

	public Min(String table, String name) {
		super("MIN", table, name);
	}

	public Min(Select select, String tableAlias, String name) {
		super("MIN", select, tableAlias, name);
	}

}