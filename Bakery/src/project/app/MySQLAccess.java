package project.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import project.common.CommonJDBC;
import project.customer.CustomerDAO;

public class MySQLAccess {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
    public boolean checkExistUser(String id, String password){
        boolean result = false;
        conn = CommonJDBC.getConnection();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM CUSTOMER ");
            sql.append("  WHERE ID = ? " );
            sql.append(" AND PASSWORD = ? ");
            pstmt = conn.prepareStatement(sql.toString());      
            
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            
            rs = pstmt.executeQuery();
            result = rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	CommonJDBC.close(conn, pstmt, rs);
        }
        
        return result;
    }
    
    public int getTotprice(int custId) {
    	int result = 0;
    	conn = CommonJDBC.getConnection();
    	
    	if (conn == null) {
    		result = -5;
    	}
    	
    	try {
    		StringBuilder sql = new StringBuilder();
    		sql.append("SELECT PRICE ");
    		sql.append(" FROM MENU M, BOOK B ");
    		sql.append(" WHERE B.FOOD_ID = M.FOOD_ID ");
    		sql.append(" AND B.CUST_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, custId);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				result += rs.getInt("PRICE");
			}
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		}
    	
    	return result;
    }
    
    public int delBook(int custid) {
    	int result = 0;
    	CustomerDAO cDao = new CustomerDAO();
    	conn = CommonJDBC.getConnection();
    	try {
    		StringBuilder sql = new StringBuilder();
    		sql.append("DELETE FROM BOOK");
    		sql.append(" WHERE CUST_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, custid);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return result;
    	
    }
    
	
}