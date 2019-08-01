package com.stevenlegg.demo.interceptors;

import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.CharArrayWriter;
import java.io.PrintWriter;


public class XmlLoggerResponseWrapper extends HttpServletResponseWrapper {

		
	private CharArrayWriter output = null;
	private XmlLoggerOutputStream  ostrm = null;
	private static final Logger logger = LoggerFactory.getLogger(XmlLoggerRequestWrapper.class);
	
	public String toString() {
	   logger.debug("XmlLoggerResponseWrapper toString called");
	   return output.toString();
	}
	public XmlLoggerResponseWrapper(HttpServletResponse response){
	   super(response);
	   output = new CharArrayWriter();
	   ostrm = new XmlLoggerOutputStream();
	}
	public PrintWriter getWriter(){
		logger.debug("XmlLoggerResponseWrapper getWriter called");
	   return new PrintWriter(output);
	}
	
	public ServletOutputStream getOutputStream(){
		logger.debug("XmlLoggerResponseWrapper getOutputStream called");
	   return ostrm;
	}
	
	public byte[] getOutputSteamData()
	{
		logger.debug("XmlLoggerResponseWrapper getOutputStreamData called");
		return ostrm.getData();
	}

}
