package project.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.app.App;
import project.common.CommonJDBC;

public class CustomerDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	//CommonJDBC jdbc = new CommonJDBC();
	
	public int insert(CustomerVO vo) {
		int result = 0;
		conn = CommonJDBC.getConnection();
		
		if (conn == null) {
			return -5;
		}
		
		try {
			//2. DB연결 - Connection 객체 생성 <- DriverManager
			
			//3. Statement 문 실행(SQL 문 실행)
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO CUSTOMER ");
			sql.append("       (CUST_ID, NAME, ID, PASSWORD, PHONE, POINT) ");
			sql.append("VALUES ((SELECT MAX(CUST_ID) + 1 FROM CUSTOMER), ?, ?, ?, ?) ");
			pstmt = conn.prepareStatement(sql.toString());
			
			// ? 값 설정
			int i = 1;
			pstmt.setString(i++, vo.getName());
			pstmt.setString(i++, vo.getId());
			pstmt.setString(i++, vo.getpassword());
			pstmt.setString(i++, vo.getPhone());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			result = -1;
		} finally {
			CommonJDBC.close(conn, pstmt);
		}
		
		return result;
	}
	
	
	public CustomerVO selectOne(String id) {
		CustomerVO vo = null;
		
		conn = CommonJDBC.getConnection();
		if (conn != null) {
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT CUST_ID, ID, PASSWORD, NAME, PHONE");
				sql.append(" FROM CUSTOMER ");
				sql.append(" WHERE ID = ?" );
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, id);
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					vo = new CustomerVO(
							rs.getInt("CUST_ID"),
							rs.getString("ID"),
							rs.getString("PASSWORD"),
							rs.getString("NAME"),
							rs.getString("PHONE") );
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
		}
		
		return vo;
	}
	
	//phone update
	public int updatePhone(String id, String aftPN) {
		int result = 0;
		
		conn = CommonJDBC.getConnection();
		if (conn == null) {
			return -5;
		}
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE CUSTOMER");
			sql.append("  SET PHONE = ?" );
			sql.append(" WHERE ID = ?" );
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, aftPN);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			result = -1;
		} finally {
			CommonJDBC.close(conn, pstmt);
		}
		
		return result;
	}
	
	public int updatePW(String id, String aftPW) {
		int result = 0;
		
		try {
			//2. DB연결 - Connection 객체 생성 <- DriverManager
			conn = CommonJDBC.getConnection();
			//3. Statement 문 실행(SQL 문 실행)
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE CUSTOMER ");
			sql.append(" Set password = ? ");
			sql.append(" WHERE ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			int i = 1;
			pstmt.setString(i++, aftPW);
			pstmt.setString(i++, id);
			result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			result = -1;
		} finally {
			CommonJDBC.close(conn, pstmt);
		}
		
		return result;
	}
	
		   
   public int delete(String id, String password) {
	   int result = 0;
	   conn = CommonJDBC.getConnection();
	   
	   if (conn == null) {
		   return -5;
	   }
	   
	   try {
		  StringBuilder sql = new StringBuilder();
	      sql.append("DELETE FROM CUSTOMER");
	      sql.append(" WHERE ID = ?");
	      sql.append(" AND PASSWORD = ?");
	      pstmt = conn.prepareStatement(sql.toString());
	      
	      pstmt.setString(1, id);
	      pstmt.setString(2, password);
	      
	      result = pstmt.executeUpdate();
	   } catch (SQLException e) {
		   result = -1;
		   e.printStackTrace();
	   } finally {
	      CommonJDBC.close(conn, pstmt);
	   }
	   
	   return result;
   }
   
   public List<CustomerVO> selectAll() {
		List<CustomerVO> list = null;
		conn = CommonJDBC.getConnection();                  
		if (conn != null) {
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT ID, PASSWORD, NAME, PHONE ");
				sql.append("  FROM CUSTOMER ");
				sql.append(" ORDER BY CUST_ID");
				pstmt = conn.prepareStatement(sql.toString());
				
				rs = pstmt.executeQuery();
				
				list = new ArrayList<CustomerVO>();
				
				while (rs.next()) {
					CustomerVO vo = new CustomerVO(
							rs.getString("ID"),
							rs.getString("PASSWORD"),
							rs.getString("NAME"),
							rs.getString("PHONE"));
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

   public boolean checkEmail(String email) {
	      // 회원가입 시 이메일 중복체크(CUSTOMER DB)
	      String sql = "SELECT ID FROM CUSTOMER";
	      ArrayList<String> list = new ArrayList<String>();
	      ResultSet rs = null;
	      Connection conn = CommonJDBC.getConnection();// db 연결
	      PreparedStatement pstmt = null;
	      try {
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();// db에서 읽어오기 실행
	         while (rs.next()) {
	            list.add(rs.getString(1));// db에서 읽어온 데이터를 list에 추가
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	      CommonJDBC.close(conn, pstmt, rs);
	      }
	      return list.contains(email);
   }
	
// 아이디, 비밀번호 넣어서 custId 받아오기
   public int getCustId(String id, String pw) {
      int custId = 0;
      conn = CommonJDBC.getConnection();
      if (conn != null) {
         try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT CUST_ID FROM CUSTOMER ");
            sql.append("  WHERE ID = ? AND PASSWORD = ? ");
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, id);
            pstmt.setString(2, pw);
            rs = pstmt.executeQuery();
            while (rs.next()) {
               custId = rs.getInt("CUST_ID");
            }
         } catch (SQLException e) {
         custId = -1;
         e.printStackTrace();
         } finally {
            CommonJDBC.close(conn, pstmt, rs);
         }
      }
      return custId;
   }
   
  

}
