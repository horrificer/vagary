package com.horrific.sqlbuilder.select;


import com.horrific.sqlbuilder.Context;

class RightOuterJoin extends Join {

	RightOuterJoin(Context context, String condition) {
		super(context, condition);
	}

	RightOuterJoin(Context context) {
		super(context);
	}

	@Override
	protected String expression() {
		return "RIGHT OUTER JOIN";
	}

}