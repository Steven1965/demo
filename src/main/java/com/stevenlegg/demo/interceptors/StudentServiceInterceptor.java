package com.stevenlegg.demo.interceptors;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class StudentServiceInterceptor extends HandlerInterceptorAdapter {
	
	   private static final Logger LOG = LoggerFactory.getLogger(StudentServiceInterceptor.class);

	   @Override
	   public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) 
	      throws Exception {
		   
		  //String myString = org.apache.commons.io.IOUtils.toString(request.getInputStream());
	      //LOG.info("Pre Handle method is Calling body:" + myString);
	      getParameters(request);
	      return true;
	   }
	   @Override
	   public void postHandle(HttpServletRequest request, HttpServletResponse response, 
	      Object handler, ModelAndView modelAndView) throws Exception {
	      
		   LOG.info("Post Handle method is Calling");
	   }
	   @Override
	   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object 
	      handler, Exception exception) throws Exception {
	      
		   LOG.info("Request and Response is completed");
	   }
	   
	   private String getParameters(HttpServletRequest request) {
		    StringBuffer posted = new StringBuffer();
		    Enumeration<?> e = request.getParameterNames();
		    if (e != null) {
		        posted.append("?");
		    }
		    while (e.hasMoreElements()) {
		        if (posted.length() > 1) {
		            posted.append("&");
		        }
		        String curr = (String) e.nextElement();
		        posted.append(curr + "=");
		        if (curr.contains("password") 
		          || curr.contains("pass")
		          || curr.contains("pwd")) {
		            posted.append("*****");
		        } else {
		            posted.append(request.getParameter(curr));
		        }
		    }
		    String ip = request.getHeader("X-FORWARDED-FOR");
		    String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
		    if (ipAddr!=null && !ipAddr.equals("")) {
		        posted.append("&_psip=" + ipAddr); 
		    }
		    return posted.toString();
		}
	   
	   private String getRemoteAddr(HttpServletRequest request) {
		    String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
		    if (ipFromHeader != null && ipFromHeader.length() > 0) {
		        LOG.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
		        return ipFromHeader;
		    }
		    return request.getRemoteAddr();
		}
}
