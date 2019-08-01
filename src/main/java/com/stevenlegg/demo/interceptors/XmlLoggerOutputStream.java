package com.stevenlegg.demo.interceptors;

import java.io.IOException;
import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import org.slf4j.LoggerFactory;

public class XmlLoggerOutputStream extends ServletOutputStream {

	private ByteArrayOutputStream bis = null;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(XmlLoggerInputStream.class);


	
	XmlLoggerOutputStream()
	{
		logger.debug("XmlLoggerOutputStream() called");
	     bis = new ByteArrayOutputStream();	
	}

	
	public void write(byte[]b) throws IOException {
		logger.debug("XmlLoggerOutputStream.write(byte[]b) called b=" + new String(b));
		bis.write(b);
	}

	public void write(int b) throws IOException {
		logger.debug("XmlLoggerOutputStream.write(int b) called b=" + b);
		bis.write(b);
	}

	public void write(byte[]b, int off, int len) throws IOException {
		logger.debug("XmlLoggerOutputStream.write(byte[]b,off,len) called b=" + new String(b) + ",off=" + off + ",len=" + len);
		bis.write(b, off, len);
		
	}
	
	public void flush() throws IOException {
		logger.debug("XmlLoggerOutputStream.flush() called");
		bis.flush();
	}
	
	public byte[] getData()
	{
		logger.debug("XmlLoggerOutputStream.getData() called");
		return bis.toByteArray();
		
	}

	public void reset()
	{
		logger.debug("XmlLoggerOutputStream.reset() called");
		bis.reset();
	}

	public void close() throws IOException
	{
		logger.debug("XmlLoggerOutputStream.close() called");
		bis.close();
	}


	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setWriteListener(WriteListener listener) {
		// TODO Auto-generated method stub
		
	}
	
}
