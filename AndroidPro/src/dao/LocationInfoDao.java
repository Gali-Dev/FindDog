package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vo.LocationInfo;

public class LocationInfoDao {
	private LocationInfoDao() {}
	private static LocationInfoDao instance = new LocationInfoDao();
	public static LocationInfoDao getInstance() {
		return instance;
	}
	
	public String select(int n) {
		String str=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select id from member where no=?";
		try {
			conn=DBConn.getConn();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, n);
			rs=ps.executeQuery();
			while(rs.next()) {
				str=rs.getString("id");
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
		
		
		return str;
	}
	
	public boolean insertLocation(int dno,double lat,double lng) {
		boolean flag=false;
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="insert into location(dog_no,lat,lng) values(?,?,?)";
		try {
			conn=DBConn.getConn();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, dno);
			ps.setDouble(2, lat);
			ps.setDouble(3, lng);
			int n=ps.executeUpdate();
			if(n==1) {
				flag=true;
				System.out.println("입력성공");
			}else {
				System.out.println("입력실패");
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
	public boolean deleteLocation(int dno) {
		boolean flag=false;
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="DELETE FROM location WHERE (dog_no = ?)";
		try {
			conn=DBConn.getConn();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, dno);
			int n=ps.executeUpdate();
			if(n==1) {
				flag=true;
				System.out.println("삭제성공");
			}else {
				System.out.println("삭제실패");
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
	public List<LocationInfo> selectList(double lat, double lng, double km){
		List<LocationInfo> list=new ArrayList<LocationInfo>();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select f.dog_no,dog_name,dog_info,dog_kinds ,lat,lng,relationship,( 6371 * acos( cos( radians(?) ) * cos( radians( lat ) ) * cos( radians( lng ) - radians(?) ) + sin( radians(?) ) * sin( radians( lat ) ) ) ) AS distance from (select no,d.dog_no,dog_name,dog_info, dog_kinds, lat,lng from dog d,location l where d.dog_no=l.dog_no) f left outer join (select * from friendship where no=11) s on f.dog_no=s.dog_no HAVING distance < ? ORDER BY distance LIMIT 0 , 10";
		try {
			conn=DBConn.getConn();
			ps=conn.prepareStatement(sql);
			ps.setDouble(1, lat);
			ps.setDouble(2, lng);
			ps.setDouble(3, lat);
			ps.setDouble(4, km);
			rs=ps.executeQuery();
			while(rs.next()) {
				LocationInfo info=new LocationInfo();
				info.setDogNo(rs.getInt("dog_no"));
				info.setDogName(rs.getString("dog_name"));
				info.setDogInfo(rs.getString("dog_info"));
				info.setDogkinds(rs.getString("dog_kinds"));
				info.setLat(rs.getDouble("lat"));
				info.setLng(rs.getDouble("lng"));
				info.setDistance(rs.getDouble("distance"));
				info.setRelationship(rs.getString("relationship"));
				list.add(info);
				System.out.println(info);
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
		return list;
	}
	public List<LocationInfo> selectList(double lat, double lng, double km,int dno){
		List<LocationInfo> list=new ArrayList<LocationInfo>();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select f.dog_no,dog_name,dog_info,dog_kinds ,lat,lng,relationship,( 6371 * acos( cos( radians(?) ) * cos( radians( lat ) ) * cos( radians( lng ) - radians(?) ) + sin( radians(?) ) * sin( radians( lat ) ) ) ) AS distance from (select no,d.dog_no,dog_name,dog_info, dog_kinds, lat,lng from dog d,location l where d.dog_no=l.dog_no) f left outer join (select * from friendship where no=11) s on f.dog_no=s.dog_no where f.dog_no!=? HAVING distance < ? ORDER BY distance LIMIT 0 , 10";
		try {
			conn=DBConn.getConn();
			ps=conn.prepareStatement(sql);
			ps.setDouble(1, lat);
			ps.setDouble(2, lng);
			ps.setDouble(3, lat);
			ps.setInt(4, dno);
			ps.setDouble(5, km);
			rs=ps.executeQuery();
			while(rs.next()) {
				LocationInfo info=new LocationInfo();
				info.setDogNo(rs.getInt("dog_no"));
				info.setDogName(rs.getString("dog_name"));
				info.setDogInfo(rs.getString("dog_info"));
				info.setDogkinds(rs.getString("dog_kinds"));
				info.setRelationship(rs.getNString("relationship"));
				info.setLat(rs.getDouble("lat"));
				info.setLng(rs.getDouble("lng"));
				info.setDistance(rs.getDouble("distance"));
				list.add(info);
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
		return list;
	}
}
