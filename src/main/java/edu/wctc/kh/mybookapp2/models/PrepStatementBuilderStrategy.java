/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.kh.mybookapp2.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Keiji
 */
public interface PrepStatementBuilderStrategy {
    public PreparedStatement buildDeleteStatement(Connection conn_loc, String tableName, String targetCol) throws SQLException;
    public PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName, String targetCol, boolean hasWhere) throws SQLException;
    public PreparedStatement buildInsertStatement(Connection conn_loc, String tableName, int numCols, int numValues) throws SQLException;
}
