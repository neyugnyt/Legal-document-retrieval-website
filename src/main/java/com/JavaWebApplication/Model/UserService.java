package com.JavaWebApplication.Model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.JavaWebApplication.Beans.RawData;
import com.JavaWebApplication.Beans.User;
import com.JavaWebApplication.Beans.UserResult;

public class UserService {

	
	public User createUser(User user) throws NoSuchAlgorithmException {
		String role = "0";
		String username = user.getUsername();
		if(!user.getPassword().toString().equals(user.getRepassword().toString()) && !isValid(username)) {
			user.setStatusCode("404");
			user.setResponse("Invalid username and password!");
			return user;
		}
		if(!user.getPassword().toString().equals(user.getRepassword().toString())) {
			user.setStatusCode("404");
			user.setResponse("Password doesn't match!");
			return user;
		}
		if(user.getPassword().length() > 20) {
			user.setStatusCode("404");
			user.setResponse("Password must lower than 20 character!");
			return user;
		}
		user.setUsername(username);
		
		if(isValid(username)) {
			User currentUser = getUser(user);
			String currentUsername = currentUser.getUsername();
			if(username.equals(currentUsername)) {
				user.setStatusCode("404");
				user.setResponse("Username already existed!");
				return user;	
			}
			String password = hashString(user.getPassword());
			MyDb db = new MyDb();
			Connection con = db.getCon();
			try {
				Statement stm = con.createStatement();
				stm.executeUpdate("insert into user(username, password, role) values('"+username+"', '"+password+"', '"+ role + "')");
				user.setUsername(username);
				user.setPassword(password);
				user.setRole(role);
				user.setStatusCode("200");
				user.setResponse("Sign up successfully!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			user.setStatusCode("404");
			user.setResponse("Invalid username. Username include lower case, number and lower than 20 character!");
			return user;
		}
		return user;

	}
	public List<UserResult> getPagination(int pageNumber) {
		List<UserResult> data = fetchUser();
		List<List<UserResult>> parts = chopped(data, 10);
		List<UserResult> rs = parts.get(pageNumber);
		return rs;
	}
	
	public static <T> List<List<T>> chopped(List<T> list, final int L) {
	    List<List<T>> parts = new ArrayList<List<T>>();
	    final int N = list.size();
	    for (int i = 0; i < N; i += L) {
	        parts.add(new ArrayList<T>(
	            list.subList(i, Math.min(N, i + L)))
	        );
	    }
	    return parts;
	}
	
	public String deleteUser(String username) {
		MyDb db = new MyDb();
		Connection con = db.getCon();
		
		try {
			Statement stm = con.createStatement();
			int count = stm.executeUpdate("delete from vanbandb.user where username=" + "'"+username+"'");
			if(count > 0) {
				return "Deleted!";
			}
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Cannot Delete";
		
	}
	
	public List<UserResult> fetchUser() {
		MyDb db = new MyDb();
		Connection con = db.getCon();
		ResultSet rs = null;
		int size =0;
		ArrayList<UserResult> users = new ArrayList<UserResult>();
		try {
			Statement stm = con.createStatement();
			rs = stm.executeQuery("select * from vanbandb.user");
			
			while(rs.next()) {
				size = size + 1;
				UserResult data = new UserResult();
				data.setUserId(rs.getString("userId"));
				data.setUserName(rs.getString("username"));
				data.setRole(rs.getString("role"));
				data.setPageSize(size);
				users.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	
	public User updateUser(User user) throws NoSuchAlgorithmException {

		MyDb db = new MyDb();
		Connection con = db.getCon();
		if(user.getUsername().length() > 20 || user.getPassword().length() > 20) {
			user.setStatusCode("404");
			user.setResponse("Username or password must lower than 20 character!");
			return user;
		}
		if(!user.getPassword().toString().equals(user.getRepassword().toString()) && !isValid(user.getUsername())) {
			user.setStatusCode("404");
			user.setResponse("Invalid username and password!");
			return user;
		}
		if(!user.getPassword().toString().equals(user.getRepassword().toString())) {
			user.setStatusCode("404");
			user.setResponse("Password doesn't match!");
			return user;
		}
		if(user.getPassword().length() > 20) {
			user.setStatusCode("404");
			user.setResponse("Password must lower than 20 character!");
			return user;
		}
		int userId = Integer.valueOf(user.getUserId());
		String hashNewPass =  hashString(user.getRepassword());
		try {

			User newUser = new User();
			newUser.setPassword(hashNewPass);
			newUser.setUsername(user.getUsername());
			Statement stm = con.createStatement();
			int count = stm.executeUpdate("update vanbandb.user set username="+"'"+newUser.getUsername()+"'" +","+"password="+"'"+newUser.getPassword()+"'"+"where userId="+"'"+userId+"'" );
			if(count > 0) {
				
				newUser.setPassword(user.getPassword());
				newUser = getUser(newUser);
				newUser.setStatusCode("200");
				newUser.setResponse("Updated!");
				return newUser;
			}
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setStatusCode("404");
		user.setResponse("Update fail");
		return user;
	}
	
	
	public User getUser(User user) throws NoSuchAlgorithmException {

		MyDb db = new MyDb();
		Connection con = db.getCon();
		ResultSet rs = null;
		if(user.getUsername().length() > 20 || user.getPassword().length() > 20) {
			user.setStatusCode("404");
			user.setResponse("Username or password must lower than 20 character!");
			return user;
		}
		String hashCurrentPass =  hashString(user.getPassword());
		try {
			Statement stm = con.createStatement();
			rs = stm.executeQuery("select userName, password, role from user where username='"+user.getUsername()+"'");
			if(rs.next()) {
				user.setUsername(rs.getString("username"));
				String hashedPass = rs.getString("password").toString();
				if(hashCurrentPass.equals(hashedPass)) {
					user.setRole(rs.getString("role"));
					user.setStatusCode("200");
					user.setResponse("Sign in successfully!");
					return user;
				}
				
			}
			else {
				user.setUsername("notfound");
				user.setStatusCode("404");
				user.setResponse("Invalid username or password!");
				return user;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setStatusCode("404");
		user.setResponse("Invalid username or password!");
		return user;
	}
	
    private static String hashString(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
        for (byte b : encodedhash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
    
    public static boolean isValid(String input) {
        // Regular expression for alphanumeric characters with a maximum length of 20
        String regex = "^[a-z0-9_\\-]{1,20}$";

        // Check if the input matches the regex
        return input.matches(regex);
    }
}
