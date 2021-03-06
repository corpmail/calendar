package com.nastev.web3.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.nastev.web3.client.GreetingService;
import com.nastev.web3.shared.FieldVerifier;
import com.allen_sauer.gwt.log.client.Log;
//import com.nastev.web3.shared.MyAppointment;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.nastev.web3.server.MysqlHelper;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	//Logger logger = Logger.getLogger("ParentLogger");
	
	//logger.log(Level.SEVERE, "this message should get logged");
	
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
				String AppId=""+rs.getInt("id");
				appt.setId(AppId);
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

	public int saveAppointmen(Appointment appt)
			throws IllegalArgumentException {
		//logger.log(Level.SEVERE, "saveAppointmen_log "+appt.getTitle());
		Log.debug("This is a 'DEBUG' test message from the GSImplementation");
		MFQueries query = MFQueries.ADD_APPOINTMENT;
		
		java.util.Date dt_start = appt.getStart();
		java.util.Date dt_end = appt.getEnd();
		java.text.SimpleDateFormat sdf =   new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dt_start_converted = sdf.format(dt_start);
		String dt_end_converted = sdf.format(dt_end);

		String[] args = new String[]{appt.getTitle(),appt.getDescription(),dt_start_converted,dt_end_converted};
		int gen_key = -1;
		try {
			gen_key= mysql.executeInsert(query, args);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gen_key;
	}

	@Override
	public Appointment getAppointmentById(int Id)
			throws IllegalArgumentException {
		Appointment appt = new Appointment();
		try {
			ResultSet rs = mysql.executeQuery("SELECT * FROM termin where id="+Id);
//			System.out.println("getAppointments1:");
			
			while(rs.next()){
//				System.out.println("getAppointments2: "+rs.getString("bezeichnung"));
				
				//appt = new Appointment();
				String AppId=""+rs.getInt("id");
				appt.setId(AppId);
				appt.setTitle(rs.getString("bezeichnung"));
				appt.setDescription(rs.getString("bezeichnung_lang"));
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		appt.setStart(simpleDateFormat.parse(rs.getString("startdate")));
				appt.setEnd(simpleDateFormat.parse(rs.getString("enddate")));
				appt.setStyle(AppointmentStyle.GREEN);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return appt;
	}

	@Override
	public Boolean deleteAppointmen(Appointment appt)
			throws IllegalArgumentException {
		String DbId = appt.getId();
		int anz = -1;

		try {
			anz = mysql.executeUpdate(MFQueries.DEL_APPOINTMENT,DbId);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if (anz>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int updateAppointmen(Appointment appt)
			throws IllegalArgumentException {
		Log.debug("This is a 'DEBUG' test message from the GreetingServiceImpl_updateAppointmen");
		MFQueries query = MFQueries.UPD_APPOINTMENT;
		
		java.util.Date dt_start = appt.getStart();
		java.util.Date dt_end = appt.getEnd();
		java.text.SimpleDateFormat sdf =   new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dt_start_converted = sdf.format(dt_start);
		String dt_end_converted = sdf.format(dt_end);
		String id = appt.getId();

		System.out.println("ARGS"+appt.getTitle()+" "+appt.getDescription()+" "+dt_start_converted+" "+dt_end_converted+" "+id);
		String[] args = new String[]{appt.getTitle(),appt.getDescription(),dt_start_converted,dt_end_converted,id};

		try {
			mysql.executeInsert(query, args);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.parseInt(id);
		
	}
}
