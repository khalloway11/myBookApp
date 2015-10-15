/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.kh.mybookapp2.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Keiji
 */
public class SQLPrepStatementBuilder implements PrepStatementBuilderStrategy {

    /**
     *
     * @param conn_loc
     * @param tableName
     * @param targetCol
     * @return PreparedStatement in the format:
     *         DELETE FROM [tableName] WHERE [targetCol] = ?
     * @throws SQLException
     */
    @Override
    public PreparedStatement buildDeleteStatement(Connection conn_loc, String tableName, String targetCol, Object targetValue) throws SQLException{
        //syntax:
        //delete from [table] where [column] (<,<=,=,>=,>,!=,BETWEEN,LIKE,IN) [value]
        PreparedStatement pstmt = null;
        final StringBuffer sql = new StringBuffer("DELETE FROM ");
        sql.append(tableName);
        if(targetCol != null){
            sql.append(" WHERE ");
            sql.append(targetCol); sql.append(" = ");
            sql.append("?");
            final String finalSQL = sql.toString();
            System.out.println(finalSQL);
            pstmt = conn_loc.prepareStatement(finalSQL);
            pstmt.setObject(1, targetValue);
        }
        return pstmt;
    }

    /**
     *
     * @param conn_loc
     * @param tableName
     * @param targetCol
     * @param hasWhere
     * @return PreparedStatement in the format: 
     *         UPDATE [tableName] SET [targetCol] = ? (new value)
     *         if hasWhere = true:
     *         WHERE [targetCol] = ? (old value)
     * @throws SQLException
     */
    @Override
    public PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName, String targetCol, boolean hasWhere) throws SQLException {
        //syntax:
        //update [table]
        //set [column] = [new value], etc.
        //where [column] = [old value]
        PreparedStatement pstmt = null;
        final StringBuffer sql = new StringBuffer("UPDATE ");
        sql.append(tableName);
        if(targetCol != null){
            sql.append(" SET ");
            sql.append(targetCol); sql.append(" =? ");
            if(hasWhere){
                sql.append("WHERE "); 
                sql.append(targetCol); sql.append(" =?");
            }
            final String finalSQL = sql.toString();
            System.out.println(finalSQL);
            pstmt = conn_loc.prepareStatement(finalSQL);
        }
        return pstmt;
    }

    
    @Override
    public PreparedStatement buildInsertStatement(Connection conn_loc, String tableName, int numCols, int numValues) throws SQLException {
        //syntax:
        //INSTERT INTO [table] ([columns])
        //VALUES ([values])
        PreparedStatement pstmt = null;
        final StringBuffer sql = new StringBuffer("INSERT INTO ");
        sql.append(tableName); sql.append(" (");
        for(int i = 0; i < numCols; i++){
            sql.append("?");
            if((i+1) != numCols){
                sql.append(", ");
            }
        }
        sql.append(") VALUES ( ");
        for(int j = 0; j < numValues; j++){
            sql.append("?");
            if((j+1) != numValues){
                sql.append(", ");
            }
        }
        sql.append(")");
        final String finalSQL = sql.toString();
        System.out.println(finalSQL);
        pstmt = conn_loc.prepareStatement(finalSQL);
        
        return pstmt;
    }
    
}
