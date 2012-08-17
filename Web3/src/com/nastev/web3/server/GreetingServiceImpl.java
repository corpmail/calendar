package com.nastev.web3.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nastev.web3.client.GreetingService;
import com.nastev.web3.shared.FieldVerifier;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	
	
	public static int myRandomWithHigh(int low, int high) {  
	    high++;  
	    return (int) (Math.random() * (high - low) + low);  
	} 

	@Override
	public Appointment getAppointment(String name)
			throws IllegalArgumentException {
		Appointment appt = new Appointment();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		try {
//			appt.setStart(simpleDateFormat.parse("17.08.2012 17:00:00"));
//			appt.setEnd(simpleDateFormat.parse("17.08.2012 19:00:00"));
			int start = myRandomWithHigh(1,23);
			int end = start + myRandomWithHigh(1,3);
			appt.setStart(simpleDateFormat.parse("17.08.2012 "+start+":00:00"));
			appt.setEnd(simpleDateFormat.parse("17.08.2012 "+end+":00:00"));
			//System.out.println("Start : "+start+" End: "+end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		appt.setTitle("Taskos Appointment");
		appt.setStyle(AppointmentStyle.GREEN);
		appt.setDescription("Billing");
		// TODO Auto-generated method stub
		return appt;
	}
}
