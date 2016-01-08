package cn.gnux.core.utils.log.dialect;


import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import cn.gnux.core.utils.StrUtil;
import cn.gnux.core.utils.log.AbstractLog;


/**
 * <a href="http://java.sun.com/javase/6/docs/technotes/guides/logging/index.html">java.util.logging</a> log.
 * 
 * @author 
 *
 */
public class JdkLog extends AbstractLog {
	private static final long serialVersionUID = -6843151523380063975L;

	private final transient Logger logger;

	// ------------------------------------------------------------------------- Constructor
	public JdkLog(Logger logger) {
		this.logger = logger;
	}

	public JdkLog(Class<?> clazz) {
		this.logger = Logger.getLogger(clazz.getName());
	}

	public JdkLog(String name) {
		this.logger = Logger.getLogger(name);
	}

	public String getName() {
		return logger.getName();
	}

	// ------------------------------------------------------------------------- Trace
	public boolean isTraceEnabled() {
		return logger.isLoggable(Level.FINEST);
	}

	public void trace(String format, Object... arguments) {
		if (isTraceEnabled()) {
			log(SELF, Level.FINEST, StrUtil.format(format, arguments), null);
		}
	}

	public void trace(Throwable t, String format, Object... arguments) {
		if(isTraceEnabled()){
			log(SELF, Level.FINEST, StrUtil.format(format, arguments), t);
		}
	}

	// ------------------------------------------------------------------------- Debug
	public boolean isDebugEnabled() {
		return logger.isLoggable(Level.FINE);
	}

	public void debug(String format, Object... arguments) {
		if(isDebugEnabled()){
			log(SELF, Level.FINE, StrUtil.format(format, arguments), null);
		}
	}

	public void debug(Throwable t, String format, Object... arguments) {
		if(isDebugEnabled()){
			log(SELF, Level.FINE, StrUtil.format(format, arguments), t);
		}
	}

	// ------------------------------------------------------------------------- Info
	public boolean isInfoEnabled() {
		return logger.isLoggable(Level.INFO);
	}

	public void info(String format, Object... arguments) {
		if(isInfoEnabled()){
			log(SELF, Level.INFO, StrUtil.format(format, arguments), null);
		}
	}

	public void info(Throwable t, String format, Object... arguments) {
		if(isInfoEnabled()){
			log(SELF, Level.INFO, StrUtil.format(format, arguments), null);
		}
	}

	// ------------------------------------------------------------------------- Warn
	public boolean isWarnEnabled() {
		return logger.isLoggable(Level.WARNING);
	}

	public void warn(String format, Object... arguments) {
		if(isWarnEnabled()){
			log(SELF, Level.WARNING, StrUtil.format(format, arguments), null);
		}
	}

	public void warn(Throwable t, String format, Object... arguments) {
		if(isWarnEnabled()){
			log(SELF, Level.WARNING, StrUtil.format(format, arguments), t);
		}
	}

	// ------------------------------------------------------------------------- Error
	public boolean isErrorEnabled() {
		return logger.isLoggable(Level.SEVERE);
	}

	public void error(String format, Object... arguments) {
		if(isErrorEnabled()){
			log(SELF, Level.SEVERE, StrUtil.format(format, arguments), null);
		}
	}

	public void error(Throwable t, String format, Object... arguments) {
		if(isErrorEnabled()){
			log(SELF, Level.SEVERE, StrUtil.format(format, arguments), t);
		}
	}

	// ------------------------------------------------------------------------- Private method
	/**
	 * 打印对应等级的日志
	 * @param callerFQCN
	 * @param level 等级
	 * @param msg 消息
	 * @param t 
	 */
	private void log(String callerFQCN, Level level, String msg, Throwable t) {
		LogRecord record = new LogRecord(level, msg);
		record.setLoggerName(getName());
		record.setThrown(t);
		fillCallerData(callerFQCN, record);
		logger.log(record);
	}
	
	private static final String SELF = JdkLog.class.getName();
	private static final String SUPER = JdkLog.class.getSuperclass().getName();

	/**
	 * Fill in caller data if possible.
	 *
	 *@param callerFQCN 调用者
	 * @param record The record to update
	 */
	private static void fillCallerData(String callerFQCN, LogRecord record) {
		StackTraceElement[] steArray = new Throwable().getStackTrace();

		int selfIndex = -1;
		for (int i = 0; i < steArray.length; i++) {
			final String className = steArray[i].getClassName();
			if (className.equals(callerFQCN) || className.equals(SUPER)) {
				selfIndex = i;
				break;
			}
		}

		int found = -1;
		for (int i = selfIndex + 1; i < steArray.length; i++) {
			final String className = steArray[i].getClassName();
			if (!(className.equals(callerFQCN) || className.equals(SUPER))) {
				found = i;
				break;
			}
		}

		if (found != -1) {
			StackTraceElement ste = steArray[found];
			// setting the class name has the side effect of setting
			// the needToInferCaller variable to false.
			record.setSourceClassName(ste.getClassName());
			record.setSourceMethodName(ste.getMethodName());
		}
	}
}
