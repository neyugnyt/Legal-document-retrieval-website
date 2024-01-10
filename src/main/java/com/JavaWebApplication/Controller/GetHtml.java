package com.JavaWebApplication.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.html.HTMLDocument;

import com.JavaWebApplication.Beans.RawData;
import com.JavaWebApplication.Model.DataProvider;

/**
 * Servlet implementation class GetHtml
 */
@WebServlet("/GetHtmlById")
public class GetHtml extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetHtml() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DataProvider dp = new DataProvider();
		
		String searchTerm = request.getParameter("vanbanId");  
		String htmlResult = dp.getHtmlById(searchTerm);
		request.setAttribute("htmlResult", htmlResult);
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/htmlcontent.jsp");
        dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
