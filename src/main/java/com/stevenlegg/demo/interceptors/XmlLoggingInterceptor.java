package com.stevenlegg.demo.interceptors;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLoggingInterceptor implements javax.servlet.Filter {
	private static final Logger logger = LoggerFactory.getLogger(XmlLoggerRequestWrapper.class);
	private XmlLoggerDBService db = null;

	public void destroy() {
		if (db != null) {
			db.disconnect();
		}
	}

	public void init(FilterConfig config) {

		String propFile = config.getInitParameter("XmlLoggerConfigFilePath");
		logger.debug("XmlLoggingInterceptor.propFile:" + propFile);

		try {
			db = new XmlLoggerDBService(propFile);

		} catch (Exception exc) {
			logger.error("Exception loading properties file:" + propFile);

		}

	}

	public void doFilter(ServletRequest rqst, ServletResponse resp, FilterChain fltChain)
			throws IOException, ServletException {
		logger.debug("doFilter start");
		Timestamp timeIn = new Timestamp(Calendar.getInstance().getTimeInMillis());
		HttpServletRequest httprqst = (HttpServletRequest) rqst;

		// logHttpServletRequestDetails(httprqst);

		XmlLoggerRequestWrapper myRqstWrapper = new XmlLoggerRequestWrapper(httprqst);
		XmlLoggerResponseWrapper myRespWrapper = new XmlLoggerResponseWrapper((HttpServletResponse) resp);

		// String rqstContent = readContent(httprqst);
		String rqstContent = new String(myRqstWrapper.getContent());
		logger.debug("Contents:" + rqstContent);

		fltChain.doFilter(myRqstWrapper, myRespWrapper);

		logger.debug("Response Length:" + myRespWrapper.toString().length());
		// resp.setContentLength(myRespWrapper.toString().length());

		// PrintWriter writer = resp.getWriter();
		// writer.write(myRespWrapper.toString());
		// writer.close();

		String respContent = new String(myRespWrapper.getOutputSteamData());
		logger.debug("Response Contents:" + respContent);

		ServletOutputStream ostrm = resp.getOutputStream();
		ostrm.write(myRespWrapper.getOutputSteamData());
		ostrm.flush();
		resp.setContentLength(respContent.length());
		db.logSoapRequest(rqstContent, respContent, timeIn, httprqst);

	}

	public void logHttpServletRequestDetails(HttpServletRequest httprqst) {
		logger.debug("Rqst received:" + httprqst.toString());
		Enumeration paramNames = httprqst.getParameterNames();
		logger.debug("Rqst parameter names:" + paramNames.toString());

		while (paramNames.hasMoreElements()) {
			String curParam = (String) paramNames.nextElement();
			logger.debug(curParam + ":" + httprqst.getParameter(curParam));
		}

		Enumeration attrNames = httprqst.getAttributeNames();
		logger.debug("Rqst attr names:" + attrNames.toString());
		while (attrNames.hasMoreElements()) {
			String curAttr = (String) attrNames.nextElement();
			logger.debug(curAttr + ":" + httprqst.getAttribute(curAttr));
		}

		Enumeration headerNames = httprqst.getHeaderNames();
		logger.debug("Rqst header names:" + headerNames.toString());
		while (headerNames.hasMoreElements()) {
			String headerNm = (String) headerNames.nextElement();
			logger.debug(headerNm + ":" + httprqst.getHeader(headerNm));

		}

		logger.debug("Rqst remote addr:" + httprqst.getRemoteAddr());
		logger.debug("Rqst remote host:" + httprqst.getRemoteHost());
		logger.debug("Rqst server name:" + httprqst.getServerName());
		logger.debug("Rqst server port:" + httprqst.getServerPort());
		logger.debug("Rqst servlet path:" + httprqst.getServletPath());
		logger.debug("Rqst request uri:" + httprqst.getRequestURI());
		logger.debug("Rqst request uri:" + httprqst.getRequestURL());

	}

	public String readInputStream(HttpServletRequest rqst) {

		try {

			HttpServletRequestWrapper rqstwrapper = new HttpServletRequestWrapper(rqst);

			ServletInputStream strm = rqstwrapper.getInputStream();
			int markLimit = rqstwrapper.getContentLength() + 1;
			logger.debug("Mark limit:" + markLimit);
			logger.debug("Mark supported:" + strm.markSupported());
			// strm.mark(markLimit);
			StringBuffer strBuff = new StringBuffer();
			int currCharInt;
			while ((currCharInt = strm.read()) != -1) { // Read a batch of chars
				char currChar = (char) currCharInt;
				strBuff.append(currChar); // Convert to a string
			}
			strm.reset();
			// strm.close();
			return strBuff.toString();

		} catch (IOException e) {
			logger.error("Exception detected in reading Servlet Input Stream:" + e.getMessage());
			logger.error(XmlLoggerUtilities.createStackTrace(e));
		}
		return "";
	}

	public String readContent(HttpServletRequest rqst) {

		try {
			BufferedReader rdr = rqst.getReader();
			int markLimit = rqst.getContentLength() + 1;
			logger.debug("Mark limit:" + markLimit);
			logger.debug("Mark supported:" + rdr.markSupported());
			/*
			 * char[] charbuff = new char[markLimit - 1]; rdr.read(charbuff, 0, markLimit -1
			 * ); logger.debug("charbuff:" + charbuff.toString());
			 * 
			 * String out = new String(charbuff);
			 */
			StringBuffer strBuff = new StringBuffer();
			int currCharInt;
			while ((currCharInt = rdr.read()) != -1) { // Read a batch of chars
				char currChar = (char) currCharInt;
				strBuff.append(currChar); // Convert to a string
			}
			String out = strBuff.toString();

			rdr.reset();
			// rdr.close();
			return out;
		} catch (IOException e) {
			logger.error("Exception detected in readContent",e);
		} catch (Exception e) {
			logger.error("Unexpected Exception detected in readContent:",e);
		}
		return "";
	}
}
