package com.stevenlegg.demo.interceptors;

import java.io.PrintWriter;
import java.io.StringWriter;


public class XmlLoggerUtilities {

	
	
	public static String createStackTrace(Throwable throwable) {

		String stackTrace = null;

		if (throwable != null) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			throwable.printStackTrace(printWriter);
			printWriter.close();
			stackTrace = stringWriter.toString();
		}
		return stackTrace;
	}


}
