package com.nastev.web3.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MyTest1 extends Composite {

	private static MyTest1UiBinder uiBinder = GWT.create(MyTest1UiBinder.class);

	interface MyTest1UiBinder extends UiBinder<Widget, MyTest1> {
	}

	public MyTest1() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
