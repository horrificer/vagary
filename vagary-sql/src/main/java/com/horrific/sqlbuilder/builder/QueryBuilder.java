package com.horrific.sqlbuilder.builder;

import com.horrific.sqlbuilder.Context;
import com.horrific.sqlbuilder.Database;
import com.horrific.sqlbuilder.delete.Delete;
import com.horrific.sqlbuilder.insert.Insert;
import com.horrific.sqlbuilder.select.Select;
import com.horrific.sqlbuilder.update.Update;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class QueryBuilder {

	private Context context;

	public QueryBuilder() {
		this.context = new Context(null, null);
	}

	public QueryBuilder(Database database, DataSource dataSource) throws SQLException {
		this(database, dataSource.getConnection());
	}

	public QueryBuilder(Database database, Connection connection) {
		this.context = new Context(database, connection);
	}

	public Select select() {
		return new Select(context);
	}

	public Update update() {
		return new Update(context);
	}

	public Update update(String table) {
		return new Update(context, table);
	}

	public Delete delete() {
		return new Delete(context);
	}

	public Delete delete(String table) {
		return new Delete(context, table);
	}

	public Insert insert() {
		return new Insert(context);
	}

	public Insert insert(String table) {
		return new Insert(context, table);
	}

	@Override
	public String toString() {
		return context.toString();
	}
}