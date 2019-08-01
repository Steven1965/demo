package com.stevenlegg.demo.interceptors;


import java.io.IOException;

import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletInputStream;

import java.io.InputStreamReader;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;

public class XmlLoggerRequestWrapper extends HttpServletRequestWrapper {
	
	private byte data[] = null;
	private XmlLoggerInputStream in;
    private static final Logger logger = LoggerFactory.getLogger(XmlLoggerRequestWrapper.class);

	public String toString() {
	   return "I am here";
	}
	public XmlLoggerRequestWrapper(HttpServletRequest request){
	   super(request);
	   readInputStream(request);
	}
	
	public ServletInputStream getInputStream()
	{
		logger.debug("XmlLoggerRequestWrapper getInputString called");
		return in;
	}

	public BufferedReader getReader()
	{
		logger.debug("XmlLoggerRequestWrapper getReader called");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		return br;
	}

	public void readInputStream(HttpServletRequest rqst) 
	{
		 logger.debug("XmlLoggerRequestWrapper readInputString called");
	    try {
	    	
	    	ServletInputStream is = rqst.getInputStream();
			int ch;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((ch = is.read()) != -1)
			{
				baos.write((byte)ch);
			}
			data = baos.toByteArray();
		

			//strm.close();
			in = new XmlLoggerInputStream(data);
			
		} catch (IOException e) {
			logger.error("Exception detected in reading Servlet Input Stream:" +  e.getMessage());
			logger.error(XmlLoggerUtilities.createStackTrace(e));
		}
	}

	public byte[] getContent()
	{
		return data;
	}


}
