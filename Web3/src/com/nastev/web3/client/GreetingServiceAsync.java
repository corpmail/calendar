package com.nastev.web3.client;

import java.util.ArrayList;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.gwt.user.client.rpc.AsyncCallback;

//import com.nastev.web3.shared.MyAppointment;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void getAppointment(String name, AsyncCallback<Appointment> callback);

	void getTerminCount(String name, AsyncCallback<Appointment> callback);

	void getAppointments(String query,
			AsyncCallback<ArrayList<Appointment>> callback);

	void saveAppointmen(Appointment appt, AsyncCallback<Integer> callback);	
	void getAppointmentById(int Id, AsyncCallback<Appointment> callback);

	void deleteAppointmen(Appointment appt, AsyncCallback<Boolean> callback);
}
