package com.lowcost.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lowcost.entity.User;


public class FilterSession implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	 @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {    
		 HttpSession session = ((HttpServletRequest) request).getSession(false);// don't create if it doesn't exist
		 if(session != null && !session.isNew()) {
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