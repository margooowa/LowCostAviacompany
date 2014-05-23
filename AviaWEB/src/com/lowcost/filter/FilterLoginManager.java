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


public class FilterLoginManager implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	 @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {    
	        HttpServletRequest req = (HttpServletRequest) request;
	        HttpSession session = req.getSession(false);
	        User manag = (User) session.getAttribute("manag");

	        if (manag!=null ) {
	              chain.doFilter(request, response);
	        } else {
	            // User is not logged in, so redirect to index.
	            HttpServletResponse res = (HttpServletResponse) response;
	            res.sendRedirect(req.getContextPath() + "/loginAdmin.privet");
	        }
	    }

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
