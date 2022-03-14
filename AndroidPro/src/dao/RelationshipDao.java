package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RelationshipDao {
	private RelationshipDao() {}
	private static RelationshipDao instance=new RelationshipDao();
	public static RelationshipDao getInstance() {
		return instance;
	}
	
	public boolean selectEmpty(int no, int dogNo) {
		boolean flag=false;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select * from friendship where no=? && dog_no=?";
		try {
			conn=DBConn.getConn();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.setInt(2, dogNo);
			rs=ps.executeQuery();
			
			if(rs.next()) {
				flag=true;
			}else {
				flag=false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBConn.close(conn, ps,rs);
			}catch(Exception e) {
				e.getStackTrace();
			}
		}
		System.out.println(flag);
		return flag;
	}
	public boolean insertRelationship(int no,int dogNo,String idx) {
		boolean flag=false;
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="insert into friendship(no,dog_no,relationship) values(?,?,?)";
		try {
			conn=DBConn.getConn();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.setInt(2, dogNo);
			ps.setString(3, idx);
			int n=ps.executeUpdate();
			if(n==1) {
				flag=true;
			}else {
				flag=false;
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBConn.close(conn, ps);
			}catch(Exception e) {
				e.getStackTrace();
			}
		}
		
		return flag;
	}
}
