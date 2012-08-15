package com.nastev.web3.client;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.bradrydzewski.gwt.calendar.client.event.CreateEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TextBox;

public class MyDialogBox1 extends DialogBox {

	private static MyDialogBox1UiBinder uiBinder = GWT
			.create(MyDialogBox1UiBinder.class);
	@UiField
	TextBox myTextBox1;
	@UiField
	Button buttonOK;
	@UiField
	Button buttonCancel;

	CreateEvent<Appointment> event;
	Calendar cal;

	interface MyDialogBox1UiBinder extends UiBinder<Widget, MyDialogBox1> {
	}

	public MyDialogBox1() {
		setWidget(uiBinder.createAndBindUi(this));

	}

	public MyDialogBox1(String firstName) {
		setWidget(uiBinder.createAndBindUi(this));
	}

	public MyDialogBox1(Calendar cal, CreateEvent<Appointment> event) {
		this.cal = cal;
		this.event = event;
		this.center();
		setWidget(uiBinder.createAndBindUi(this));

	}

	@UiHandler("buttonOK")
	void onButtonOKClick(ClickEvent event) {
		Appointment app = this.event.getTarget();
		app.setTitle(myTextBox1.getText());
		app.setStart(this.event.getTarget().getStart());
		app.setEnd(this.event.getTarget().getEnd());
		this.cal.addAppointment(app);
		this.hide();
	}

	@UiHandler("buttonCancel")
	void onButtonCancelClick(ClickEvent event) {
		this.event.setCancelled(true);
		this.cal.doLayout();
		this.hide();
	}
}
