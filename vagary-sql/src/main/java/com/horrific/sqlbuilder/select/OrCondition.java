package com.horrific.sqlbuilder.select;


import com.horrific.sqlbuilder.Context;

class OrCondition extends Condition {

	OrCondition(Context context) {
		super(context);
	}

	@Override
	protected String getPrefix() {
		return "OR";
	}

}