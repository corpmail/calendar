package com.nastev.web3.client;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	Appointment getAppointment(String name) throws IllegalArgumentException;
	Appointment  getTerminCount(String name) throws IllegalArgumentException;
}
