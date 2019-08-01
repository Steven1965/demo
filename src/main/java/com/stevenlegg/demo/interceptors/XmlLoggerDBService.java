//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : DBDataService.java
//  @ Date : 4/15/2009
//  @ Author : 
//
//



package com.stevenlegg.demo.interceptors;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;


/**
 * 
 * This class is responsible for interfacing with the database to hid the details from the rest of the XmlLogging package
 * 
 *
 */
public class XmlLoggerDBService
{

	/**
	* Log4j logger so that all xml logger out put can be sent to specific log file
	*/	
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(XmlLoggerDBService.class);

	/**
	* String used in db connection
	*/	
	private String dbDriverClass = null;
	
	/** sql.connection - the db connection*/
	private static Connection connection = null;
	
	/** String used in dbConnection*/
	private String userName = null;
	
	/** String used in dbConnection*/
	private String password = null;
	
	/** String used in dbConnection*/
	private String connectionString = null ;
	
	/**PrepararedStatment used to exequte specific sqlXmlLogger */
	private PreparedStatement pstmtXmlLogger = null;
	
	/** String contains detailed sql for inserting data*/
    private String sqlXmlLogger = 
    	"INSERT INTO interface_db..XmlLogger col1, col2,col3,col4, col5, col6, col7, col8, col9 " +
    	"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	/**String sql used to test connection */
	public String testConnectionSQL;

	/** 
	* Constructs the database object using a properties files. The name of the property file is passed to it
	* The constructor calls the connect method for simplicity.
	* @param propFileName String : This is the name of the property file which holds db connection details.
	*/
	public XmlLoggerDBService(String propFileName)
	{
		
		try {
				Properties properties = new Properties();
				properties.load(new FileInputStream(propFileName));
				connectionString = properties.getProperty("db.connection");
				userName = properties.getProperty("db.username");
				password = properties.getProperty("db.password");
				dbDriverClass = properties.getProperty("db.driver");


				logger.debug("databaseConnectionString:" + connectionString);
				logger.debug("databaseUsername:" + userName);
				logger.debug("databaseDriverClass:" + dbDriverClass);
				//System.out.println("databasePassword:" + password);
			
				connect();
			}
			catch (Exception exc)
			{
				logger.error("Exception loading properties file:" + propFileName);
				logger.error(exc.getMessage());	
			}
	}
        
	/** Method used to establish the connection to the database*/
	public void connect() throws Exception
	{
         logger.info("connect method starts..");
	//Connection connection = null;
		try {
            if(connection == null)
            {
                logger.debug("DB Driver Class :"+dbDriverClass);
                Class.forName(dbDriverClass);
                logger.debug("ConnectionString :"+connectionString);                            
                connection = DriverManager.getConnection(connectionString, userName, password);
                logger.debug("Connected to database ");
                
                
                pstmtXmlLogger = connection.prepareStatement(sqlXmlLogger);

                
           }
        } catch (ClassNotFoundException c) {
            logger.error("Unable to load Driver Class. Exp Message:" + c.getMessage());                        
			throw new Exception("Unable to load Driver Class. Exp Message:" + c.getMessage());

		} catch (SQLException e) {
            logger.error("SQLException occurred while connecting to DB. Exp Message:"+e.getMessage());
			throw new Exception("SQLException occurred while connecting to DB. Exp Message:" + e.getMessage());

		} catch (Exception e) {
            logger.error("Exception occurred while connecting to DB. Exp Message:"+e.getMessage());
			throw new Exception("Exception occurred while connecting to DB. Exp Message:" + e.getMessage());
		}
         logger.info("connect method ends..");
                //return connection;
	}
	
	/** Method used to disconnect fromt the database*/
	public void disconnect()
	{
           logger.info("disconnect starts...");            
            try
            {
            	if (connection != null)
            	{
            		logger.debug("Connection closing...");
            		connection.close();
            		connection = null;
            		logger.debug("Connection closed...");
            	}
            }
            catch(Exception ex)
            {
                logger.error("Exception Occured while closing the connection...",ex); 
            }
           logger.info("disconnect ends...");            
	}
	
	/** Metod to reestablish connectivity to the database*/
	public void reconnect()
	{
           logger.info("reconnect starts...");
           try
            {
                logger.debug("disconnect called...");
                disconnect();
                logger.debug("disconnected..."); 
                logger.debug("Connection called...");                   
                connect();
                logger.debug("Connected...");                
            }
            catch(Exception ex)
            {
                logger.error("Exception Occured while reconnection...",ex); 
            }	
           logger.info("reconnect ends...");           
	}
	
	/** Method to test out the connection*/
	public void testConnection() 
	{
           try
           {
                connect();
                if(connection != null)
                    logger.debug("Connection created.."+connection);
                else
                    logger.debug("Connection is null.."+connection);
           }
           catch(Exception ex)
           {
                logger.error("Exception Occured while testConnection..."+ex.getMessage()); 
                logger.error(" "+ XmlLoggerUtilities.createStackTrace(ex));                
           }
	}
    
	/** Method to store the soap message detail in the xml logging database table 
	 * 
	 * @param rqstXml String : contents of the received xml message
	 * @param respXml String : contents of the returned xml message
	 * @param timeIn Timestamp : time when the request was received
	 * @param rqst HttpServletRequest : time when the request was received
	 * 
	 * 
	 * */
	public void logSoapRequest(String rqstXml, String respXml, Timestamp timeIn, HttpServletRequest rqst)
	{
		String uri =  rqst.getRequestURI();
		String url =  rqst.getRequestURL().toString();
		String remoteAddr =rqst.getRemoteAddr();
		String remoteHost =rqst.getRemoteHost();
		Timestamp timeOut = new Timestamp(Calendar.getInstance().getTimeInMillis());
		long processingTime = timeOut.getTime() - timeIn.getTime();
		
		logger.debug("uri=" + uri);
		logger.debug("url=" + url);
		logger.debug("rqstXml=" + rqstXml);
		logger.debug("respXml=" + respXml);
		logger.debug("timeIn=" + timeIn);
		logger.debug("timeOut=" + timeOut);
		logger.debug("processingTime=" + processingTime);
		
		try {
			pstmtXmlLogger.setString(1, uri);
			pstmtXmlLogger.setString(2, url);
			pstmtXmlLogger.setString(3, remoteAddr);
			pstmtXmlLogger.setString(4, remoteHost);
			pstmtXmlLogger.setTimestamp(5, timeIn);
			pstmtXmlLogger.setTimestamp(6, timeOut);
			pstmtXmlLogger.setLong(7, processingTime);
			pstmtXmlLogger.setString(8, rqstXml);
			pstmtXmlLogger.setString(9, respXml);
			
			//pstmtXmlLogger.executeQuery();
			
			logger.debug("Successfully written to the database.."+pstmtXmlLogger.toString());
			
			
		}
		catch (Exception exc)
		{
            logger.error("Exception Occured while logging xml"+exc.getMessage()); 
            logger.error(" "+ XmlLoggerUtilities.createStackTrace(exc));                
			
		}
		
		
		
	}
}
