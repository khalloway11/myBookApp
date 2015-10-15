/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.kh.mybookapp2.models;

import java.sql.*;
import java.util.*;
import javax.sql.DataSource;

/**
 *
 * @author Keiji
 */
public class MySqlDb implements DBStrategy {
    //class variables
    private Connection conn;
    private final PrepStatementBuilderStrategy pstmtBuilder = new SQLPrepStatementBuilder();
    
    public void openConnection(String driverClass, String url, String userName, String password) throws Exception {
        Class.forName (driverClass);
	conn = DriverManager.getConnection(url, userName, password);
    }
    
    @Override
    public void openConnection(DataSource ds) throws Exception {
        conn = ds.getConnection();
    }
    
    public void closeConnection() throws SQLException {
        conn.close();
    }
    
    public List<Map<String, Object>> findAllRecords(String tableName) throws SQLException{
        List<Map<String, Object>> records = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();
        
        while(rs.next()){
            Map<String, Object> record = new HashMap<>();
            for(int i = 1; i <= colCount; i++){
                record.put(meta.getColumnName(i), rs.getObject(i));
            }
            records.add(record);
        }
        
        return records;
    }
    
    @Override
    public void deleteById(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException{
        //delete from [table] where [column] = [value]
        
        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKeyFieldName + "=";
        if(primaryKeyValue instanceof String){
            sql += "\"" + (String)primaryKeyValue + "\"";
        } else {
            sql += (String)primaryKeyValue;
        }
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }
    
    public int prepDelete(String tableName, String targetCol, Object targetRecord) throws SQLException{
        //syntax:
        //delete from [table] where [column] (<,<=,=,>=,>,!=,BETWEEN,LIKE,IN) [value]
        int deleted = 0;
        PreparedStatement pstmt = pstmtBuilder.buildDeleteStatement(conn, tableName, targetCol, targetRecord);
        
        try{
            deleted = pstmt.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println(sqle);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch(Exception e) {
                System.out.println(e);
            }
        }
        return deleted;
    }
    
    public int update(String tableName, String colName, Object newValue, Object oldValue) throws SQLException{
        //syntax:
        //update [table] set [column] = [new value] 
        //where [column] = [old value]
        int updated = 0;
        boolean hasWhere = false;
        if((oldValue != null) && !(oldValue.equals(""))){
            hasWhere = true;
        }
        PreparedStatement pstmt = pstmtBuilder.buildUpdateStatement(conn, tableName, colName, hasWhere);
        try{
            pstmt.setObject(1, newValue);
            if(hasWhere){
                pstmt.setObject(2, oldValue);
            }
            updated = pstmt.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println(sqle);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch(Exception e) {
                System.out.println(e);
            }
        }
        return updated;
    }
    
    public void createRecord(String tableName, Object[] newRecordInfo) throws SQLException{
        
    }
    
    public static void main(String[] args) throws Exception{
        MySqlDb db = new MySqlDb();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        List<Map<String, Object>> records = db.findAllRecords("author");
        for(Map record : records){
            System.out.println(record);
        }
        db.prepDelete("author", "author_id", 1);
        db.update("author", "author_name", "Joe Schmoe", "Steve Peeve");
//        records = db.findAllRecords("author");
//        for(Map record : records){
//            System.out.println(record);
//        }
        db.closeConnection();
    }
}
