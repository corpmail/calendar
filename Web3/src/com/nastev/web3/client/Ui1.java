package com.nastev.web3.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.bradrydzewski.gwt.calendar.client.CalendarFormat;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings.Click;
import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.bradrydzewski.gwt.calendar.client.event.CreateEvent;
import com.bradrydzewski.gwt.calendar.client.event.CreateHandler;
import com.bradrydzewski.gwt.calendar.client.event.DeleteEvent;
import com.bradrydzewski.gwt.calendar.client.event.DeleteHandler;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickEvent;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickHandler;
import com.bradrydzewski.gwt.calendar.client.event.UpdateEvent;
import com.bradrydzewski.gwt.calendar.client.event.UpdateHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

public class Ui1 extends Composite {

	private static Ui1UiBinder uiBinder = GWT.create(Ui1UiBinder.class);
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	@UiField
	HTMLPanel myHtmlPanel;
	@UiField
	VerticalPanel myVerticalPanel;
	@UiField
	Button b_day;
	@UiField
	Button b_week;
	@UiField
	Button b_month;
	@UiField
	Label label;
	Calendar calendar = null;
	CalendarSettings settings = null;

	interface Ui1UiBinder extends UiBinder<Widget, Ui1> {
	}

	public Ui1() {
		initWidget(uiBinder.createAndBindUi(this));
		calendar = new Calendar();
		calendar.setDate(new Date()); // calendar date, not required
		calendar.setDays(7); // number of days displayed at a time, not required
		calendar.setWidth("1000px");
		calendar.setHeight("600px");
		settings = new CalendarSettings();
		// Enable the Drag&Drop
		settings.setEnableDragDrop(true);
		// Enable the Drag&Drop Creation
		settings.setEnableDragDropCreation(true);
		// And the "magic setting"
		settings.setTimeBlockClickNumber(Click.None);
		settings.setPixelsPerInterval(20);
		settings.setOffsetHourLabels(true);

		CalendarFormat.INSTANCE.setTimeFormat("HH:00");
		CalendarFormat.INSTANCE.setAm("");
		CalendarFormat.INSTANCE.setPm("");
		CalendarFormat.INSTANCE.setNoon("12:00");
		calendar.setSettings(settings);
		calendar.setView(CalendarViews.DAY, 7);
		myVerticalPanel.add(calendar);

		
		
		greetingService.getAppointments("asdf", new AsyncCallback<ArrayList<Appointment>>() {
            public void onFailure(Throwable caught) {
              Window.alert("RPC to getAppointments() failed.");
            }
			@Override
			public void onSuccess(ArrayList<Appointment> result) {
				//System.out.println("greetingService.getAppointments");
				//ArrayList<Appointment> l1 = null;
				calendar.suspendLayout();
				calendar.addAppointments(result);
				calendar.resumeLayout();
				
			}

          });
		

		
		
		
		
		
		calendar.addTimeBlockClickHandler(new TimeBlockClickHandler<Date>() {
			@Override
			public void onTimeBlockClick(TimeBlockClickEvent<Date> event) {

				Date startDate = (Date) event.getTarget();
				Window.alert(startDate.toString());
				// Appointment ap = new Appointment();
				// ap.setTitle("title");
				// ap.setStart(startDate);
				// calendar.addAppointment(ap);

				Appointment appt = new Appointment();
				DateTimeFormat calformat = DateTimeFormat
						.getFormat("yyyy-MM-dd hh:mm:ss");
				appt.setStart(startDate);
				// appt.setStart(calformat.parse("2012-08-13 09:00:00"));
				appt.setEnd(calformat.parse("2012-08-13 23:00:00"));
				appt.setTitle("Taskos Appointment");
				appt.setStyle(AppointmentStyle.GREEN);
				appt.setDescription("Billing");
				calendar.addAppointment(appt);

			}
		});

		// /

		// calendar.addCreateHandler(new CreateHandler<Appointment>() {
		// @Override
		// public void onCreate(CreateEvent<Appointment> event) {
		// boolean commit = Window
		// .confirm("Are you sure you want to create a new appointment");
		// if (!commit) {
		// event.setCancelled(true);
		// System.out.println("Cancelled Appointment creation");
		// } else {
		// Appointment app = event.getTarget();
		// app.setTitle("New Appointment");
		// calendar.addAppointment(app);
		// }
		// }
		// });

		calendar.addCreateHandler(new CreateHandler<Appointment>() {
			@Override
			public void onCreate(CreateEvent<Appointment> event) {

				MyDialogBox1 box1 = new MyDialogBox1(calendar, event);
				//MyDialogBox2 box1 = new MyDialogBox2();
				box1.show();

				// boolean commit = Window
				// .confirm("Are you sure you want to create a new appointment");
				// if (!commit) {
				// event.setCancelled(true);
				// System.out.println("Cancelled Appointment creation");
				// } else {
				// Appointment app = event.getTarget();
				// app.setStart(event.getTarget().getStart());
				// app.setEnd(event.getTarget().getEnd());
				// app.setTitle("New Appointment");
				// calendar.addAppointment(app);
				// }
			}
		});

		calendar.addUpdateHandler(new UpdateHandler<Appointment>() {
			@Override
			public void onUpdate(UpdateEvent<Appointment> event) {
				// TODO Auto-generated method stub
				boolean commit = Window
						.confirm("Are you sure you want to update the appointment \n"
								+ "NAME:"
								+ event.getTarget().getTitle()
								+ "\n"
								+ "START:"
								+ event.getTarget().getStart().toString()
								+ "\n"
								+ "END:"
								+ event.getTarget().getEnd().toString() + "");

				Appointment app = event.getTarget();
				calendar.removeAppointment(app);
				app.setStart(event.getTarget().getStart());
				app.setEnd(event.getTarget().getEnd());
				calendar.addAppointment(app);

				if (!commit) {
					event.setCancelled(true); // Cancel Appointment update
					calendar.resetSelectedAppointment();
				}

			}
		});

		
		calendar.addDeleteHandler(new DeleteHandler<Appointment>(){
			  @Override
			  public void onDelete( DeleteEvent<Appointment> event) {
			    boolean commit = Window.confirm("Are you sure you want to delete?");
			    if(commit==false) {
			      event.setCancelled(true);
			    } else { 
			    	// anscheinend ist kein explizites calendar.removeAppointment nötig, das mach dieser Hanedler selbst
			    	final GreetingServiceAsync greetingService = GWT
			    			.create(GreetingService.class);
			    	greetingService.deleteAppointmen(event.getTarget(), new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onSuccess(Boolean result) {
							
						}
			    		
			    	});
			    }
			  }                     
			});
		
		calendar.addOpenHandler(new OpenHandler<Appointment>(){

			@Override
			public void onOpen(OpenEvent<Appointment> event) {
				// TODO Auto-generated method stub
				//Window.confirm("Are you sure you want to open?");
				MyDialogBox1 box1 = new MyDialogBox1(calendar, event.getTarget());
				//MyDialogBox2 box1 = new MyDialogBox2();
				box1.show();
				
			}

			});

	}

	public Ui1(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	// @UiFactory Calendar makeCricketScores() { // method name is insignificant
	// Calendar calendar = new Calendar();
	// calendar.setDate(new Date()); //calendar date, not required
	// calendar.setDays(7); //number of days displayed at a time, not required
	// calendar.setWidth("1200px");
	// calendar.setHeight("800px");
	// return calendar;
	// }

	@UiHandler("b_day")
	void onB_dayClick(ClickEvent event) {
		calendar.setView(CalendarViews.DAY, 1);
	}

	@UiHandler("b_week")
	void onB_weekClick(ClickEvent event) {
		calendar.setView(CalendarViews.DAY, 7);
	}

	@UiHandler("b_month")
	void onB_monthClick(ClickEvent event) {
		calendar.setView(CalendarViews.MONTH);
		calendar.setTitle("Mein Kalender");
	}

	@UiHandler("label")
	void onLabelClick(ClickEvent event) {
		calendar.setView(CalendarViews.DAY, 1);

		Appointment appt = new Appointment();
		DateTimeFormat calformat = DateTimeFormat
				.getFormat("yyyy-MM-dd hh:mm:ss");
		appt.setStart(calformat.parse("2012-08-14 04:04:05"));
		appt.setEnd(calformat.parse("2012-08-14 04:05:30"));
		appt.setTitle("CCM20125000001");
		appt.setStyle(AppointmentStyle.GREEN);
		appt.setDescription("Billing");
		calendar.addAppointment(appt);
	}
}
