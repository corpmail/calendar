	package com.nastev.web3.server;

	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.nastev.web3.server.MFQueries;

public class MysqlHelper {


	        private static MysqlHelper helper = null;
	        private Connection con = null;
	        private InitialContext ctx = null;
	        private DataSource ds = null;

	        public static MysqlHelper getHelper(){
	                if (helper == null) {
	                        try {
								helper = new MysqlHelper();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NamingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                }
	                return helper;
	        }

	        private  MysqlHelper() throws ClassNotFoundException, SQLException, NamingException {	                
	        	    ctx = new InitialContext();
	                ds = (DataSource)ctx.lookup("java:comp/env/jdbc/LiveDataSource");
	                con = ds.getConnection();
	        }
	        
	        public ResultSet executeQuery(String sql) throws SQLException{
	                ResultSet resultSet = con.createStatement().executeQuery(sql);
	                return resultSet;
	        }
	        
	        public int executeUpdate(MFQueries query, String...args) throws Exception{
	                PreparedStatement pstmt = con.prepareStatement(query.getSqlQuery());
	                for(int i=0;i<query.getTypes().length;i++){
	                        switch (query.getTypes()[i]) {
	                        case CHAR:
	                        case VARCHAR:
	                                pstmt.setString(i+1, args[i]);
	                                break;
	                        
	                        case INT:
	                                pstmt.setInt(i+1, Integer.parseInt(args[i]));

	                        default:
	                                break;
	                        }
	                }

	                int count = pstmt.executeUpdate();
	                
	                return count;
	        }
	        
	        public ResultSet executeQuery(MFQueries query, String...args) throws SQLException{
	                PreparedStatement pstmt = con.prepareStatement(query.getSqlQuery());
	                for(int i=0;i<query.getTypes().length;i++){
	                        switch (query.getTypes()[i]) {
	                        case CHAR:
	                        case VARCHAR:
	                                pstmt.setString(i+1, args[i]);
	                                break;
	                        
	                        case INT:
	                                pstmt.setInt(i+1, Integer.parseInt(args[i]));

	                        default:
	                                break;
	                        }
	                }
	                ResultSet res = pstmt.executeQuery();
	                return res;
	        }
	        
	        
	        public int executeInsert(MFQueries query, String...args) throws Exception{
                PreparedStatement pstmt = con.prepareStatement(query.getSqlQuery());
                System.out.println("MysqlHelper|executeInsert|statement: "+query.getSqlQuery());
                System.out.println("MysqlHelper|executeInsert|args.leng: "+args.length);
                
                for(int i=0;i<query.getTypes().length;i++){
                        switch (query.getTypes()[i]) {
                        case CHAR:
                        case VARCHAR:
                                pstmt.setString(i+1, args[i]);
                                break;
                        
                        case INT:
                                pstmt.setInt(i+1, Integer.parseInt(args[i]));

                        default:
                                break;
                        }
                }
                

                System.out.println("MysqlHelper|executeInsert|pstmt.toString: "+pstmt.toString());
                int autoIncKeyFromApi = 0;
                


                
                int count = pstmt.executeUpdate();
                //ResultSet rs = pstmt.getGeneratedKeys();
                ResultSet rs = pstmt.executeQuery("SELECT LAST_INSERT_ID()");
                if (rs.next()) {
                    autoIncKeyFromApi = rs.getInt(1);
                } else {

                }
                //System.out.println("executeInsert1:");
                //logger.log(Level.SEVERE, "executeInsert2:");
                GWT.log("executeInsert3:");
                
                return autoIncKeyFromApi;
        }
	        
	        
	        
	        public static void main(String args[]) throws ClassNotFoundException, SQLException, NamingException{
	        	MysqlHelper helper = getHelper();
	                ResultSet res = helper.executeQuery("select * from user");
	                while(res.next()){
	                        System.out.println(res.getString(1)+"\t"+res.getString(2));
	                }
	        }
	}


