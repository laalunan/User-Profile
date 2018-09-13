package com.ibm.bootcamp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.ibm.bootcamp.entity.User;

public class UserDAO {
	
	private DBConnectionFactory DBAccount = new DBConnectionAccounts();
	
	//View Profile
	public List<User> viewProfile(Map<String, Object> request) {
		List<User> userdetails = new ArrayList<User>();
		Connection conn = DBAccount.getConnection();

		String query = "select * from accounts where username = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(query);

			pstmt.setString(1, request.get("username").toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setUserType(rs.getString("userType"));
				user.setAccountStatus(rs.getInt("accountStatus"));
				user.setAuthenticationStatus(rs.getInt("authenticationStatus"));
				userdetails.add(user);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userdetails;
	}
	
	//Update User Profile

	public String updateProfile(Map<String, Object> request) {
		Connection conn = DBAccount.getConnection();
		PreparedStatement pstmt = null;

		String query = "UPDATE accounts SET email = ?, firstName = ?, lastName = ? WHERE username = ?";
		String result = "";

		try {
			conn = DBAccount.getConnection();

			if (conn != null) {
				int i = 1;
				pstmt = conn.prepareStatement(query);

				
				pstmt.setString(i++, request.get("email").toString());
				pstmt.setString(i++, request.get("firstName").toString());
				pstmt.setString(i++, request.get("lastName").toString());
				
				pstmt.setString(i++, request.get("username").toString());

				int rs = pstmt.executeUpdate();
				if (rs == 1) {
					result = "Update Success";
				} else {
					result = "Update Fail";
				}
			}

		} catch (SQLException ex) {
			Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			DBAccount.closeConnection(conn, pstmt);
		}
		return result;
	}
	

}
