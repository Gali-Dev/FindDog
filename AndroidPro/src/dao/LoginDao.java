package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoginDao {
	private LoginDao() {
	}

	private static LoginDao instance = new LoginDao();

	public static LoginDao getInstance() {
		return instance;
	}

	public String insert(String id, String pw, String name, String location, String dog_ox) {
		String str = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from member where id=?";
		String sql2 = "insert into member(id,pw,name,region,owner) values(?,?,?,?,?)";

		try {
			System.out.println(dog_ox);
			conn = DBConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.setNString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				str = "이미 존재하는 아이디입니다";
			} else {

				ps = conn.prepareStatement(sql2);
				ps.setString(1, id);
				ps.setString(2, pw);
				ps.setString(3, name);
				ps.setString(4, location);
				ps.setString(5, dog_ox);
				int n = ps.executeUpdate();
				if (n == 1) {
					str = "회원가입 성공";
				} else {
					str = "회원가입 실패";
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return str;
	}

	public int getNo(String id) {
		int no = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select no from member where id=?";

		try {
			conn = DBConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				no = rs.getInt("no");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConn.close(conn, ps, rs);
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		return no;
	}

	public String getOx(String id) {
		String ox = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select owner from member where id=?";

		try {
			conn = DBConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				ox = rs.getString("owner");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConn.close(conn, ps, rs);
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		return ox;
	}

//	public int selectNo(int no) {
//		
//	}

	public boolean insertDog(int no, String dog_name, String dog_info, String dog_kinds) {
		// String str = "";
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		// ResultSet rs = null;
		String sql = "insert into dog2(no,dog_name,dog_info,dog_kinds) values(?,?,?,?)";

		try {
			conn = DBConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.setString(2, dog_name);
			ps.setString(3, dog_info);
			ps.setString(4, dog_kinds);
			// rs = ps.executeQuery();

			int n = ps.executeUpdate();
			if (n == 1) {
				flag = true;

			} else {

			}
//			if (rs.next()) {
//				ps.setString(1, dog_name);
//				ps.setString(2, dog_info);
//				ps.setString(3, dog_kinds);
//				
//				if (n == 1) {
//					str = "강아지 등록 성공";
//				} else {
//					str = "강아지 등록 실패";
//				}
//			} else {
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}

	public String login(String id, String pw) {
		String str = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select pw from member where id=?";
		System.out.println("Dao.login id:" + id);

		try {
			conn = DBConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				System.out.println("ddssss");
				if (pw.equals(rs.getString(1))) {
					str = "로그인 성공";
					System.out.println("로그인 성공");
				} else {
					str = "패스워드 다름";
					System.out.println("패스워드 다름");
				}
			} else {
				str = "아이디 없음";
				System.out.println("아이디 없음");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return str;
	}

}
