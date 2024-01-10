package com.JavaWebApplication.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.JavaWebApplication.Beans.SearchResult;
import com.JavaWebApplication.Model.DataProvider;

/**
 * Servlet implementation class GetDocByTerm
 */
@WebServlet("/GetDocWithTerm")
public class GetDocByTerm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDocByTerm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        String spageid=request.getParameter("pageNumber");  
        int pageNumber=Integer.parseInt(spageid);  
        int totalPages = 0;
		String searchTerm = request.getParameter("searchTerm");  
		if (searchTerm != null && !searchTerm.isEmpty()) {
			DataProvider dp = new DataProvider();
			List<SearchResult> results = dp.getDocByTerm(pageNumber, searchTerm);
			if(results == null) {
				response.sendRedirect("http://localhost:8080/JavaWebApplication/jsp/NotFoundPage.jsp");
			}
			if(results != null) {
				totalPages = results.get(0).getPageSize();
				request.setAttribute("searchResults", results);
				request.setAttribute("totalPagesIndex", totalPages);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/tittle.jsp");
		        dispatcher.forward(request, response);
			}

		}
		else {
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
