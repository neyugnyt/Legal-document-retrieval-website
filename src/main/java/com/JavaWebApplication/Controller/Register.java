package com.JavaWebApplication.Controller;

import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.JavaWebApplication.Beans.User;
import com.JavaWebApplication.Model.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
	
		UserService us = new UserService();
		HttpSession session = request.getSession(true);
		String userName = request.getParameter("userName");
		String password = request.getParameter("psw");
		String repassword = request.getParameter("repassword");
		
		User user = new User();
		user.setUsername(userName);
		user.setPassword(password);
		user.setRepassword(repassword);
		
		try {
			User result = us.createUser(user);
			if(result.getStatusCode().equals("200")) {
				
				session.setAttribute("userName", result.getUsername());
				session.setAttribute("resRegister", result.getResponse());
				session.setAttribute("role", result.getRole());
				response.sendRedirect("http://localhost:8080/JavaWebApplication/GetData?pageNumber=0");
			}
			else {
				session.setAttribute("resRegister", result.getResponse());
				response.sendRedirect("http://localhost:8080/JavaWebApplication/jsp/register.jsp");
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
