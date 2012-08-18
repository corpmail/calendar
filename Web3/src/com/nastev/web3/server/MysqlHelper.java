	package com.nastev.web3.server;

	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
	        public static void main(String args[]) throws ClassNotFoundException, SQLException, NamingException{
	        	MysqlHelper helper = getHelper();
	                ResultSet res = helper.executeQuery("select * from user");
	                while(res.next()){
	                        System.out.println(res.getString(1)+"\t"+res.getString(2));
	                }
	        }
	}


