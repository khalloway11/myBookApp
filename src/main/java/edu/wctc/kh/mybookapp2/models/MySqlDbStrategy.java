package edu.wctc.kh.mybookapp2.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author jlombardo
 */
public class MySqlDbStrategy implements DBStrategy {
    private Connection conn;

    /**
     * Open a connection manually, without pooling.
     * @param driverClass
     * @param url
     * @param userName
     * @param password
     * @throws Exception 
     */
    @Override
    public final void openConnection(String driverClass, String url,
        String userName, String password) throws Exception {
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    /**
     * Open a connection using a connection pool configured on server.
     * @param ds - a reference to a connection pool via a JNDI name, producing
     * this object. Typically done in a servlet using InitalContext object.
     * @throws Exception - if ds cannot be established
     */
    @Override
    public final void openConnection(DataSource ds) throws Exception {
        conn = ds.getConnection();
    }

    /**
     * Closes a manual connection or returns a pooled connection to pool.
     * @throws SQLException 
     */
    @Override
    public final void closeConnection() throws SQLException {
        conn.close();
    }
    
    @Override
    public final List<Map<String,Object>> findAllRecords(String tableName) throws SQLException {
        String sql = "SELECT * FROM " + tableName;
        List<Map<String,Object>> recordList = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        while(rs.next()) {
            Map<String,Object> record = new HashMap<>();
            for(int i=1; i <= columnCount; i++) {
                record.put(metaData.getColumnName(i),rs.getObject(i));
            }
            recordList.add(record);
        }
        
        return recordList;
    }
    
    
    public final void deleteById(String tableName, String primaryKeyFieldName, 
            Object primaryKeyValue) throws SQLException {
        
        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKeyFieldName + " = ?";
        
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1, primaryKeyValue);
        // recordsDeleted count not used, but could be later
        stmt.executeUpdate();
    }
    
    // Test harness - not used in production
    public static void main(String[] args) throws Exception {
        MySqlDbStrategy db = new MySqlDbStrategy();
        
        // ALWAYS OPEN THE CONNECTION BEFORE YOU RUN YOUR QUERY
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        
        System.out.println("All author records before delete:");
        List<Map<String,Object>> records =
                db.findAllRecords("author");
        for(Map record : records) {
            System.out.println(record);
        }
        
        db.deleteById("author", "author_id", 2);
        
        System.out.println("\nAll author records before delete:");
        records =
                db.findAllRecords("author");
        for(Map record : records) {
            System.out.println(record);
        }
        
        // DON'T FORGET TO CLOSE THE CONNECTION WHEN YOU ARE DONE!!!
        db.closeConnection();
    }
}
