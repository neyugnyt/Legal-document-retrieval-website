package com.JavaWebApplication.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.JavaWebApplication.Beans.UserResult;
import com.JavaWebApplication.Model.UserService;

/**
 * Servlet implementation class DeleteUser
 */

public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String spageid = request.getParameter("pageNumber");
		int pageNumber = Integer.parseInt(spageid);
		int totalPages = 0;
		UserService us = new UserService();
		String message = us.deleteUser(request.getParameter("username"));
		
		List<UserResult> results = us.getPagination(pageNumber);
		
		if (results == null) {
			response.sendRedirect("http://localhost:8080/JavaWebApplication/jsp/NotFoundPage.jsp");
		}
		if (results != null) {
			totalPages = results.size();
			request.setAttribute("userResults", results);
			request.setAttribute("totalPagesUser", totalPages);
			request.setAttribute("deleteMess", message);
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/admin.jsp");
			dispatcher.forward(request, response);
		}else {			
			response.sendRedirect("http://localhost:8080/JavaWebApplication/jsp/NotFoundPage.jsp");
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}

}
