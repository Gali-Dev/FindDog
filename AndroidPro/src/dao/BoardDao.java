package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vo.Board;

public class BoardDao {
	private BoardDao() {}
	private static BoardDao instance = new BoardDao();
	public static BoardDao getInstance() {
		return instance;
	}
	
	public List<Board> selectBoardList(){
		List<Board> list=new ArrayList<Board>();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select * from board";
		try {
			conn=DBConn.getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				Board board=new Board();
				board.setNo(rs.getInt("no"));
				board.setBno(rs.getInt("bno"));
				board.setTitle(rs.getString("title"));
				board.setCategory(rs.getString("category"));
				board.setContent(rs.getString("content"));
				list.add(board);
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
