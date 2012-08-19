package com.nastev.web3.client;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.bradrydzewski.gwt.calendar.client.event.CreateEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ListBox;
import com.nastev.web3.client.GreetingService;
import com.nastev.web3.client.GreetingServiceAsync;
//import com.nastev.web3.shared.MyAppointment;
import com.google.gwt.user.client.ui.TextArea;

public class MyDialogBox1 extends DialogBox {

	private static MyDialogBox1UiBinder uiBinder = GWT
			.create(MyDialogBox1UiBinder.class);
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	@UiField
	TextBox myBeschreibung;
	@UiField
	Button buttonOK;
	@UiField
	Button buttonCancel;
	@UiField
	TextBox myOrt;
	@UiField
	DateBox myVon;
	@UiField
	DateBox myBis;
	@UiField
	AbsolutePanel myAbsolutePanel;
	@UiField
	ListBox myList;
	@UiField
	TextArea myBeschreibungLang;
	@UiField Button buttonDelete;

	CreateEvent<Appointment> createEvent;
	//OpenEvent<Appointment> openEvent;
	Calendar cal;
	Appointment appt;
	Boolean isUPDATE = false;  //Ein Schalter um zu unterscheiden ob es un ein SQL-Update, oder ein SQL-Insert handelt

	interface MyDialogBox1UiBinder extends UiBinder<Widget, MyDialogBox1> {
	}

	public MyDialogBox1() {
		setWidget(uiBinder.createAndBindUi(this));

	}

	public MyDialogBox1(String firstName) {
		setWidget(uiBinder.createAndBindUi(this));
	}

	public MyDialogBox1(Calendar cal, CreateEvent<Appointment> event) {
		this.isUPDATE = false;
		this.cal = cal;
		this.createEvent = event;
		setWidget(uiBinder.createAndBindUi(this));
		myAbsolutePanel.setWidth("603px");
		this.center();
		this.myVon.setValue(this.createEvent.getTarget().getStart());
		this.myBis.setValue(this.createEvent.getTarget().getEnd());

		this.myList.addItem("Training");
		this.myList.addItem("Spielen");
		this.myList.addItem("Sex");
		this.buttonDelete.setEnabled(false);
		this.buttonDelete.setVisible(false);

	}

	//public MyDialogBox1(Calendar cal, Appointment appt, OpenEvent<Appointment> event) {
	public MyDialogBox1(Calendar cal, Appointment appt) {
		this.isUPDATE = true;
		this.cal = cal;
		this.appt = appt;
		//this.openEvent = event;
		setWidget(uiBinder.createAndBindUi(this));
		myAbsolutePanel.setWidth("603px");
		this.center();
		this.myBeschreibung.setValue(this.appt.getTitle());
		this.myBeschreibungLang.setValue(this.appt.getDescription());
		this.myVon.setValue(this.appt.getStart());
		this.myBis.setValue(this.appt.getEnd());

		this.myList.addItem("Training");
		this.myList.addItem("Spielen");
		this.myList.addItem("Sex");
		this.buttonOK.setText("Termin aktualisieren");

	}

	@UiHandler("buttonOK")
	void onButtonOKClick(ClickEvent event) {
		// Appointment app = this.event.getTarget();
		// app.setTitle(myBeschreibung.getText());
		// // app.setStart(this.event.getTarget().getStart());
		// // app.setEnd(this.event.getTarget().getEnd());
		// app.setStart(this.myVon.getValue());
		// app.setEnd(this.myBis.getValue());
		//
		// String listType = this.myList.getItemText(this.myList
		// .getSelectedIndex());
		// System.out.println(listType);
		// if (this.myList.getSelectedIndex() == 0) {
		// app.setStyle(AppointmentStyle.BLUE);
		//
		// }
		// if (this.myList.getSelectedIndex() == 1) {
		// app.setStyle(AppointmentStyle.LIGHT_PURPLE);
		//
		// }
		// if (this.myList.getSelectedIndex() == 2) {
		// app.setStyle(AppointmentStyle.RED);
		//
		// }
		//
		// this.cal.addAppointment(app);
		// this.hide();

		
		if (!this.isUPDATE) {  //INSERT
			//Log.debug("This is a 'DEBUG' test message from the DialogBox1_buttonOK_updateFalse1");
			//System.out.println("This is a 'DEBUG' test message from the DialogBox1_buttonOK_updateFalse1");
			
			final Appointment app = new Appointment();
			app.setTitle(myBeschreibung.getText());
			app.setDescription(myBeschreibungLang.getText());
			app.setStart(this.myVon.getValue());
			app.setEnd(this.myBis.getValue());

			//insertNewAppointment(app);
			new MyApptHelper(this.cal).insertNewAppointment(app);
			
		} else { //Update
			

			this.appt.setTitle(myBeschreibung.getText());
			this.appt.setDescription(myBeschreibungLang.getText());
			this.appt.setStart(this.myVon.getValue());
			this.appt.setEnd(this.myBis.getValue());

			// 1)Zuerst den alten Appt aus der Calender-View entfernen
			cal.removeAppointment(this.appt);
			
			// 2)Dann den Datenbank-Update machen
			//updateAppointment(this.appt);
			new MyApptHelper(this.cal).updateAppointment(this.appt);

		}

		this.hide();

	}



	@UiHandler("buttonCancel")
	void onButtonCancelClick(ClickEvent event) {
		if(this.isUPDATE){
			this.hide();
		}
		else {
		this.createEvent.setCancelled(true);
		this.cal.doLayout();
		this.hide();
		}

	}

	@UiHandler("buttonDelete")
	void onButtonDeleteClick(ClickEvent event) {
		new MyApptHelper(cal).deleteAppointment(this.appt);
		cal.removeAppointment(this.appt);
		this.hide();
	}
}
