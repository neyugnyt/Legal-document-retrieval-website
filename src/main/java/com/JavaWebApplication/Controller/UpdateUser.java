package com.JavaWebApplication.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.JavaWebApplication.Beans.User;
import com.JavaWebApplication.Model.UserService;

/**
 * Servlet implementation class UpdateUser
 */
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		UserService us = new UserService();
		String userName = request.getParameter("userName");
		String password = request.getParameter("psw");
		String repassword = request.getParameter("repassword");
		String userId = (String) session.getAttribute("userId");
		User user = new User();
		user.setUsername(userName);
		user.setPassword(password);
		user.setRepassword(repassword);
		user.setUserId(userId);
		
		System.out.println(session.getAttribute("userId"));
		try {
			User result = us.updateUser(user);
			if(result.getStatusCode().equals("200")) {
				
				session.setAttribute("userName", result.getUsername());
				session.setAttribute("update", result.getResponse());
				response.sendRedirect("http://localhost:8080/JavaWebApplication/GetUsers?pageNumber=0");
			}
			else {
				session.setAttribute("resUpdate", result.getResponse());
				response.sendRedirect("http://localhost:8080/JavaWebApplication/jsp/update.jsp");
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
