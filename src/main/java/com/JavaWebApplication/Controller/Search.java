package com.JavaWebApplication.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.JavaWebApplication.Beans.SearchResult;

import com.JavaWebApplication.Model.DataProvider;

/**
 * Servlet implementation class Search
 */
@WebServlet("/SearchQuery")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
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
		String searchQuery = request.getParameter("searchQuery");
		if (searchQuery != null && !searchQuery.isEmpty()) {
			DataProvider dp = new DataProvider();
			List<SearchResult> results = dp.searchDoc(pageNumber, request.getParameter("searchQuery"));
			if(results == null) {
				String encodedSearchQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8.toString());
				response.sendRedirect("http://localhost:8080/JavaWebApplication/jsp/NotFoundPage.jsp" + "?searchQuery=" + "\"" + encodedSearchQuery + "\"");
			}
			if(results != null) {
				totalPages = results.get(0).getPageSize();
				request.setAttribute("searchResults", results);
				request.setAttribute("totalPages", totalPages);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/content.jsp");
		        dispatcher.forward(request, response);
			}

		}
		else {
			response.sendError(0, "No Result");
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
