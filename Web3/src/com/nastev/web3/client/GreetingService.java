package com.nastev.web3.client;

import java.util.ArrayList;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

//import com.nastev.web3.shared.MyAppointment;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;

	Appointment getAppointment(String name) throws IllegalArgumentException;
	Appointment getAppointmentById(int Id) throws IllegalArgumentException;

	Appointment getTerminCount(String name) throws IllegalArgumentException;

	ArrayList<Appointment> getAppointments(String query)
			throws IllegalArgumentException;

	int saveAppointmen(Appointment appt) throws IllegalArgumentException;
	Boolean deleteAppointmen(Appointment appt) throws IllegalArgumentException;
	int updateAppointmen(Appointment appt) throws IllegalArgumentException;
}
