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
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserService us = new UserService();
		User user = new User();
		HttpSession session = request.getSession(true);
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		
		user.setUsername(userName);
		user.setPassword(password);
		
		try {
			User result = us.getUser(user);
			if(result.getStatusCode().equals("200")) {
				
				session.setAttribute("userName", result.getUsername());
				session.setAttribute("resLogin", result.getResponse());
				session.setAttribute("role", result.getRole());
				response.sendRedirect("http://localhost:8080/JavaWebApplication/GetData?pageNumber=0");
			}
			else {
				session.setAttribute("resLogin", result.getResponse());
				response.sendRedirect("http://localhost:8080/JavaWebApplication/jsp/login.jsp");
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
