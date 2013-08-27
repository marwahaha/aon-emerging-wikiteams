package internetz;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.SimpleLayout;

public class PjiitLogger {
	
	public static Logger logger = null;
	
	public static void init() throws IOException{
		logger = getRootLogger();
		SimpleLayout layout = new SimpleLayout();   
		FileAppender appender = new FileAppender(layout, "simulation_logs.txt",false);    
	      logger.addAppender(appender);
	    setLevel(Level.ALL);
	}
	
	public static void init(Level level) throws IOException{
		logger = getRootLogger();
		SimpleLayout layout = new SimpleLayout();   
		FileAppender appender = new FileAppender(layout, "simulation_logs.txt",false);    
	      logger.addAppender(appender);
	    setLevel(level);
	}
	
	// Creation & retrieval methods:
	public static Logger getRootLogger() {
		return Logger.getRootLogger();
	}
	
	public static void setLevel(Level level){
		logger.setLevel(level);
	}
	
	@SuppressWarnings("deprecation")
	public static Priority[] getAllPossiblePriorities(){
		return Priority.getAllPossiblePriorities();
	}

	public static Logger getLogger(String name) {
		return Logger.getLogger(name);
	}

	// printing methods:
	public static void trace(String message) {
		logger.log(Priority.INFO, message);
	}

	public static void debug(Object message) {
		logger.log(Priority.DEBUG, message);
	}

	public static void info(String message) {
		logger.log(Priority.INFO, message);
	}

	public static void warn(Object message) {
		logger.log(Priority.WARN, message);
	}

	public static void error(Object message) {
		logger.log(Priority.ERROR, message);
	}

	public static void fatal(Object message) {
		logger.log(Priority.FATAL, message);
	}

	// generic printing method:
	public static void log(Level l, Object message) {
		logger.log(Priority.INFO, message);
	}
}
