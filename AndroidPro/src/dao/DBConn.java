package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConn {
	public static Connection getConn() {
		Connection conn = null;
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/android");
			
			conn = ds.getConnection();
			System.out.println("DB연결성공");
		}catch(Exception e) {
			System.out.println("DB연결 실패");
			e.getStackTrace();
		}
		return conn;
	}
	public static void close(Connection conn, PreparedStatement ps) {
		try {
			if(ps!=null)ps.close();
			if(conn!=null)conn.close();
		}catch(Exception e) {
			e.getStackTrace();
		}
	}
	public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if(rs!=null)rs.close();
			if(ps!=null)ps.close();
			if(conn!=null)conn.close();
		}catch(Exception e) {
			e.getStackTrace();
		}
	}
}
