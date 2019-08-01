package com.stevenlegg.demo.interceptors;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class XmlLoggerInputStream extends ServletInputStream {

	BufferedInputStream bis = null;
	private static final Logger logger = LoggerFactory.getLogger(XmlLoggerInputStream.class);

	
	XmlLoggerInputStream(InputStream is)
	{
		logger.debug("XmlLoggerInputStream(InputStream) called");
	     bis = new BufferedInputStream(is);	
	}
	XmlLoggerInputStream(byte[] data)
	{
		logger.debug("XmlLoggerInputStream(byte[]b) called ");
	     bis = new BufferedInputStream(new ByteArrayInputStream(data));	
	}	
	public int read() throws IOException {
		logger.debug("XmlLoggerInputStream.read() called ");
		return bis.read();
	}

	public void mark(int markLimit){
		logger.debug("XmlLoggerInputStream.mark() called ");
		bis.mark(markLimit);
	}

	public boolean markSupported(){
		logger.debug("XmlLoggerInputStream.markSupported() called ");
		return bis.markSupported();
	}
	
	public void reset() throws IOException {
		logger.debug("XmlLoggerInputStream.reset() called ");
		bis.reset();
	}

	public int read(byte[]b) throws IOException {
		logger.debug("XmlLoggerInputStream.read(byte[]b) called ");
		return bis.read(b);
	}

	public int read(byte[]b, int off, int len) throws IOException {
		logger.debug("XmlLoggerInputStream.read(byte[]b, off, len) called ");
		return bis.read(b,off,len);
	}
	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setReadListener(ReadListener listener) {
		// TODO Auto-generated method stub
		
	}
	
}
