package com.nastev.web3.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class MyDialogBox2 extends DialogBox {

	private static MyDialogBox2UiBinder uiBinder = GWT
			.create(MyDialogBox2UiBinder.class);
	@UiField AbsolutePanel myAbsolutePanel;
	

	interface MyDialogBox2UiBinder extends UiBinder<Widget, MyDialogBox2> {
	}

	public MyDialogBox2() {
		setWidget(uiBinder.createAndBindUi(this));
		myAbsolutePanel.setWidth("400px");
	}

}
