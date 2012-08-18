package com.nastev.web3.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.nastev.web3.client.GreetingService;
import com.nastev.web3.shared.FieldVerifier;
//import com.nastev.web3.shared.MyAppointment;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.nastev.web3.server.MysqlHelper;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	MysqlHelper mysql = MysqlHelper.getHelper();

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
			appt.setStart(simpleDateFormat.parse("18.08.2012 "+start+":00:00"));
			appt.setEnd(simpleDateFormat.parse("18.08.2012 "+end+":00:00"));
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

	@Override
	public Appointment getTerminCount(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		String Erg = "asdf12345";
		
		
		//
//		InitialContext ctx;
//		Connection conn = null;
//        Statement stmt = null;
        
        String bezeichnung = null;
        String startdate = null;
        String enddate = null;
        Appointment appt = null;
        
		try {
//			ctx = new InitialContext();
//			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/LiveDataSource");
//			conn = ds.getConnection();
//			stmt = conn.createStatement();
			
	//		ResultSet rs = stmt.executeQuery("SELECT * FROM termin");
			ResultSet rs = mysql.executeQuery("SELECT * FROM termin");
            
            while(rs.next()){
            //int theInt= rs.getInt("test_id");
             bezeichnung = rs.getString("bezeichnung");
             startdate = rs.getString("startdate");
             enddate = rs.getString("enddate");
            
            System.out.println("Abfrage: "+rs.getString("bezeichnung"));
            System.out.println("Abfrage: "+startdate);
            System.out.println("Abfrage: "+enddate);
            }//end while loop
            
            
            appt = new Appointment();
    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		appt.setStart(simpleDateFormat.parse(startdate));
			appt.setEnd(simpleDateFormat.parse(enddate));
			appt.setTitle(bezeichnung);
			appt.setStyle(AppointmentStyle.GREEN);
			appt.setDescription("Billing");
            
            

//            stmt.close();
//            stmt = null;
//            conn.close();
//            conn = null;
            
            
            
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


 
		//
		
		return appt;
	}

	@Override
	public ArrayList<Appointment> getAppointments(String query)
			throws IllegalArgumentException {
//		System.out.println("getAppointments0:");
		ArrayList<Appointment> list1 = new ArrayList<Appointment>();
		
		try {
			ResultSet rs = mysql.executeQuery("SELECT * FROM termin");
//			System.out.println("getAppointments1:");
			
			while(rs.next()){
//				System.out.println("getAppointments2: "+rs.getString("bezeichnung"));
				
				Appointment appt = new Appointment();
				
				appt.setTitle(rs.getString("bezeichnung"));
				appt.setDescription(rs.getString("bezeichnung_lang"));
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		appt.setStart(simpleDateFormat.parse(rs.getString("startdate")));
				appt.setEnd(simpleDateFormat.parse(rs.getString("enddate")));
				appt.setStyle(AppointmentStyle.GREEN);
				
				
				list1.add(appt);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list1;
	}

//	public boolean saveAppointmen(MyAppointment appt)
//			throws IllegalArgumentException {
//		
//		MFQueries query = MFQueries.ADD_APPOINTMENT;
//		String[] args = new String[]{appt.getTitle(),appt.getDescription(),appt.getStart().toString(),appt.getEnd().toString()};
//		
//		try {
//			mysql.executeQuery(query, args);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return true;
//	}
}
