package project.book;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.common.CommonJDBC;

public class BookDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	//insert 
	public int insert(BookVO vo) {
		int result = 0;
		
		conn = CommonJDBC.getConnection();
		
		if (conn == null) {
			return -5;
		}
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO BOOK ");
			sql.append("    (BOOK_ID, BOOKDATE, CUST_ID, FOOD_ID)");
			sql.append(" VALUES ((SELECT NVL(MAX(BOOK_ID), 0) + 1 FROM BOOK),TO_DATE(?, 'YYYYMMDDHH24MI'),?,?)");
			pstmt = conn.prepareStatement(sql.toString());
			
			int i = 1;
			pstmt.setString(i++, vo.getBookDate());
			pstmt.setInt(i++, vo.getCust_Id());
			pstmt.setInt(i++, vo.getFood_Id());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		}
		
		return result;
	}
	
	//메뉴를 Book 테이블에 넣기
//	public int 
	
	//delete
	public int delete(int bookId) {
		int result = 0;
		conn = CommonJDBC.getConnection();
		if (conn == null) {
			return -5;
		}
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE BOOK ");
			sql.append("  WHERE BOOK_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, bookId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt);
		}
		
		return result;
	}
	
	
	//update date
	public int updateDate(Date date, int bookId ) {
		int result = 0;
		conn = CommonJDBC.getConnection();
		if (conn == null) {
			return -5;
		}
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE BOOK ");
			sql.append("  SET BOOKDATE = ? ");
			sql.append("  WHERE BOOK_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setDate(1, date);
			pstmt.setInt(2, bookId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt);
		}
		
		return result;
	}
	
	//selectONe : bookid 넣어서 정보 조회
	public BookVO selectOne(int bookId) {
		BookVO vo = null;
		conn = CommonJDBC.getConnection();

		if (conn != null) {
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT BOOK_ID, BOOKDATE, CUST_ID, FOOD_ID ");
				sql.append(" FROM BOOK ");
				sql.append("  WHERE BOOK_ID = ? ");
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setInt(1, bookId);
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					vo = new BookVO(
							rs.getInt("BOOK_ID"),
							rs.getString("BOOKDATE"),
							rs.getInt("CUST_ID"),
							rs.getInt("FOOD_ID") );
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
		}
		
		return vo;
	}
	
	//select All
	public List<BookVO> selectAll() {
		List<BookVO> list = null;
		
		conn = CommonJDBC.getConnection();
		if (conn != null) {
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT BOOK_ID, BOOKDATE, CUST_ID, FOOD_ID ");
				sql.append(" FROM BOOK ");
				pstmt = conn.prepareStatement(sql.toString());
				
				rs = pstmt.executeQuery();
				list = new ArrayList<BookVO>();
				
				while (rs.next()) {
					BookVO vo =  new BookVO(
							rs.getInt("BOOK_ID"),
							rs.getString("BOOKDATE"),
							rs.getInt("CUST_ID"),
							rs.getInt("FOOD_ID") );
					list.add(vo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
		}
		
		return list;
	}
	
	
}
