package com.horrific.sqlbuilder.select;


import com.horrific.sqlbuilder.Context;

class AndCondition extends Condition {

	AndCondition(Context context) {
		super(context);
	}

	@Override
	protected String getPrefix() {
		return "AND";
	}

}