package com.nastev.web3.server;

public enum MFQueries {

    GET_USER_FROM_UID("select user_id, user_name from user where user_id = ?", MFDataType.CHAR),
    GET_USER_FROM_NAME("select user_id, user_name from user where user_name = ?",MFDataType.CHAR),
    ADD_USER("insert into user(user_id, user_name) values(?,?)",MFDataType.CHAR,MFDataType.CHAR),
    DEL_APPOINTMENT("delete from termin where id = ?", MFDataType.CHAR),
    UPD_APPOINTMENT("update termin set bezeichnung = ?, bezeichnung_lang = ?,startdate = ?,enddate = ? where id = ?",MFDataType.CHAR,MFDataType.CHAR,MFDataType.CHAR,MFDataType.CHAR,MFDataType.CHAR),
    ADD_APPOINTMENT("insert into termin(bezeichnung, bezeichnung_lang,startdate,enddate) values(?,?,?,?)",MFDataType.CHAR,MFDataType.CHAR,MFDataType.CHAR,MFDataType.CHAR);
    
    ;
    
    private String sqlQuery = null;
    private MFDataType[] types = null;

    private MFQueries(String query, MFDataType... types) {
            this.sqlQuery = query;
            this.types = types;
    }

    public String getSqlQuery() {
            return sqlQuery;
    }

    public MFDataType[] getTypes() {
            return types;
    }
   
}


