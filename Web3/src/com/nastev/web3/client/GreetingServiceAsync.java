package com.nastev.web3.client;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void getAppointment(String name, AsyncCallback<Appointment> callback);

	void getTerminCount(String name, AsyncCallback<Appointment> callback);
}
