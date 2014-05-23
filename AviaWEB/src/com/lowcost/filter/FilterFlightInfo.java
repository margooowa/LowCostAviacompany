package com.lowcost.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lowcost.entity.Timetable;
import com.lowcost.entity.User;


public class FilterFlightInfo implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	 @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {  
	     List<Timetable> flight = (List<Timetable>)((HttpServletRequest)request).getSession().getAttribute("flight"); 	      	      
	        if (flight != null ) {
	              chain.doFilter(request, response);
	        } else {
	        	  String contextPath = ((HttpServletRequest)request).getContextPath();
	              ((HttpServletResponse)response).sendRedirect(contextPath + "/home.privet");
	        }
	    }

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
