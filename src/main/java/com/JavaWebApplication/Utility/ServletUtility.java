package com.JavaWebApplication.Utility;

import java.util.List;

import com.JavaWebApplication.Beans.RawData;

import jakarta.servlet.http.HttpServletRequest;

public class ServletUtility {

	public static List<RawData> getList(HttpServletRequest request) {
		    return (List<RawData>) request.getAttribute("list");
		  }
	  public static void setList(List<RawData> list, HttpServletRequest request) {
		    request.setAttribute("list", list);
		  }
}