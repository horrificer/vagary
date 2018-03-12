package com.horrific.sql.statement.function;

import com.horrific.sql.statement.Select;

public class Count extends AggregateColumn {

	public Count(String name) {
		super("COUNT", name);
	}

	public Count(String table, String name) {
		super("COUNT", table, name);
	}

	public Count(Select select, String tableAlias, String name) {
		super("COUNT", select, tableAlias, name);
	}

}
