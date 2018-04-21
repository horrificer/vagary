package com.horrific.sqlbuilder.select;


import com.horrific.sqlbuilder.Context;
import com.horrific.sqlbuilder.Database;
import com.horrific.sqlbuilder.TerminalExpression;
import org.omg.PortableInterceptor.INACTIVE;

import java.sql.SQLException;
import java.util.List;

public class Limit implements TerminalExpression {

	private final Context context;

	public Limit(Context context, Integer start, Integer size) {
		this.context = limit(context, start, size);
	}

	@Override
	public <E> List<E> list(RowMapper<E> rowMapper) throws SQLException {
		return context.list(rowMapper);
	}

	@Override
	public <E> E single(RowMapper<E> rowMapper) throws SQLException {
		return context.single(rowMapper);
	}

	private Context limit(Context context, Integer start, Integer size) {
		return new LimiterFactory().create(context.getDatabase()).limit(context, start, size);
	}

	@Override
	public String toString() {
		return context.toString();
	}
}

interface Limiter {
	Context limit(Context context, Integer start, Integer size);
}

class HSQLDBLimiter implements Limiter {

	@Override
	public Context limit(Context context, Integer start, Integer size) {
		if (size != null) {
			context.appendLine(" LIMIT " + size);
			context.addParameters(size);
		}
		if (start != null) {
			context.appendLine(" OFFSET " + start);
			context.addParameters(start);
		}

		return context;
	}

}

class OracleLimiter implements Limiter {

	@Override
	public Context limit(Context context, Integer start, Integer size) {
		Context c = new Context(context);
		c.appendLine("SELECT");
		c.appendLine("data.*");
		c.appendLine("FROM");
		c.appendLine("(");
		c.appendLine("SELECT");
		c.appendLine("ord_data.*,");
		c.appendLine("rownum AS rnum");
		c.appendLine("FROM");
		c.appendLine("(");
		c.appendLine(context.toString());
		c.appendLine(")");
		c.appendLine("ord_data");
		c.appendLine(")");
		c.appendLine("data");
		c.appendLine("WHERE");
		c.appendLine("rnum BETWEEN ? AND ?");
		c.addParameters(start);
		c.addParameters(start + size);
		return c;
	}

}

class DefaultLimiter implements Limiter {
	@Override
	public Context limit(Context context, Integer start, Integer size) {
		return context;
	}
}

class LimiterFactory {
	Limiter create(Database database) {
		/*switch (database) {
			case HSQLDB:
				return new HSQLDBLimiter();
			case ORACLE:
				return new OracleLimiter();
			default:
				return new DefaultLimiter();
		}*/
		return new HSQLDBLimiter();
	}
}