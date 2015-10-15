/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.kh.mybookapp2.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Keiji
 */
public class MySqlDbStrat implements DBStrategy {
    private Connection conn;
    private PrepStatementBuilderStrategy pstmt;
    
    public MySqlDbStrat(Connection conn, PrepStatementBuilderStrategy pstmt){
        this.conn = conn;
        this.pstmt = pstmt;
    }
    
    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }

    @Override
    public void openConnection(DataSource ds) throws Exception {
        conn = ds.getConnection();
    }

    @Override
    public void deleteById(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException {
        PreparedStatement execute = pstmt.buildDeleteStatement(conn, tableName, primaryKeyFieldName, primaryKeyValue);
        execute.executeUpdate();
    }

    @Override
    public List<Map<String, Object>> findAllRecords(String tableName) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void openConnection(String driverClass, String url, String userName, String password) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
