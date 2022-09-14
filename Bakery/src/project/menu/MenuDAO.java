package project.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.app.App;
import project.common.CommonJDBC;

public class MenuDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	//insert menu
	public int insert(MenuVO vo) {
		int result = 0;
		
		conn = CommonJDBC.getConnection();
		
		if (conn == null) {
			return -5;
		}
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO MENU ");
			sql.append(" 	(FOOD_ID, FOOD_NAME, PRICE) ");
			sql.append(" VALUES ((SELECT MAX(FOOD_ID) + 1 FROM MENU) ,? ,?) ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, vo.getFood_id());
			pstmt.setNString(2, vo.getFood_name());
			pstmt.setInt(3, vo.getPrice());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			result = -1;
		} finally {
			CommonJDBC.close(conn, pstmt);
		}
		
		return result;
	}
	
	//delete menu
	// foodId 넣어서 지운다
	public int delete(int foodId) {
		int result = 0;
		
		conn = CommonJDBC.getConnection();
		if (conn == null) {
			return -5;
		}
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE MENU ");
			sql.append(" WHERE FOOD_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, foodId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			result = -1;
		} finally {
			CommonJDBC.close(conn, pstmt);
		}
		
		return result;
	}
	
	//update menu : 이름 업데이트
	public int updateName(MenuVO vo) {
		int result = 0;
		conn = CommonJDBC.getConnection();
		
		if (conn == null) {
			return -5;
		}
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE MENU " );
			sql.append("  SET FOOD_NAME = ? ");
			sql.append(" WHERE FOOD_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setNString(1, vo.getFood_name());
			pstmt.setInt(2, vo.getFood_id());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			result = -1;
		} finally {
			CommonJDBC.close(conn, pstmt);
		}
		
		return result;
	}
	
	public int updateName(int foodId, String newName) {
		int result = 0;
		conn = CommonJDBC.getConnection();
		
		if (conn == null) {
			return -5;
		}
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE MENU " );
			sql.append("  SET FOOD_NAME = ? ");
			sql.append(" WHERE FOOD_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, newName);
			pstmt.setInt(2, foodId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			result = -1;
		} finally {
			CommonJDBC.close(conn, pstmt);
		}
		
		return result;
	}
	
	//select 이름을 찾아서 셀렉트 해준다
	public MenuVO selectOneMenu(int foodId) {
		MenuVO vo = null;
		
		conn = CommonJDBC.getConnection();
		
		if (conn != null) {
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT FOOD_ID, FOOD_NAME, PRICE ");
				sql.append(" FROM MENU ");
				sql.append(" WHERE FOOD_ID = ? ");
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setInt(1, foodId);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					vo = new MenuVO(
							rs.getInt("FOOD_ID"),
							rs.getNString("FOOD_NAME"),
							rs.getInt("PRICE") );
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
		}
		
		return vo;
	}
	
	public MenuVO selectOneMenu(String foodName) {
		MenuVO vo = null;
		
		conn = CommonJDBC.getConnection();
		
		if (conn != null) {
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT FOOD_ID, FOOD_NAME, PRICE ");
				sql.append(" FROM MENU ");
				sql.append(" WHERE FOOD_NAME = ? ");
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, foodName);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					vo = new MenuVO(
							rs.getInt("FOOD_ID"),
							rs.getNString("FOOD_NAME"),
							rs.getInt("PRICE") );
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
		}
		
		return vo;
	}
	
	//selectAll : 모든 데이터 정보 조회
	//selectAll : 모든 데이터 정보 조회
	public List<MenuVO> selectAll() {
		List<MenuVO> list = null;
		App app = new App();
		conn = CommonJDBC.getConnection();                  
		if (conn != null) {
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT FOOD_ID, FOOD_NAME, PRICE, FOOD_INFO ");
				sql.append("  FROM MENU ");
				sql.append("ORDER BY FOOD_ID");
				pstmt = conn.prepareStatement(sql.toString());
				
				rs = pstmt.executeQuery();
				
				list = new ArrayList<MenuVO>();
				
				while (rs.next()) {
					MenuVO vo = new MenuVO(
							rs.getInt("FOOD_ID"),
							rs.getString("FOOD_NAME"),
							rs.getInt("PRICE"),
							rs.getString("FOOD_INFO") );
					list.add(vo);
				}
				System.out.println("=============== MENU ===============");
				System.out.println();
				for(MenuVO str : list) {
					System.out.println(str);
					System.out.println("—————————————————―――――――――――――――");
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
