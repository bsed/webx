package cn.gnux.core.utils.log.dialect;

import cn.gnux.core.utils.StrUtil;
import cn.gnux.core.utils.log.AbstractLog;


/**
 * <a href="http://logging.apache.org/log4j/2.x/index.html">Apache Log4J 2</a> log.<br>
 * 
 * @author 
 *
 */
public class Log4j2Log extends AbstractLog {
	private static final long serialVersionUID = -6843151523380063975L;

	private final transient org.apache.logging.log4j.Logger logger;

	// ------------------------------------------------------------------------- Constructor
	public Log4j2Log(org.apache.logging.log4j.Logger logger) {
		this.logger = logger;
	}

	public Log4j2Log(Class<?> clazz) {
		this.logger = org.apache.logging.log4j.LogManager.getLogger(clazz);
	}

	public Log4j2Log(String name) {
		this.logger = org.apache.logging.log4j.LogManager.getLogger(name);
	}

	public String getName() {
		return logger.getName();
	}

	// ------------------------------------------------------------------------- Trace
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public void trace(String format, Object... arguments) {
		if (isTraceEnabled()) {
			logger.trace(StrUtil.format(format, arguments));
		}
	}

	public void trace(Throwable t, String format, Object... arguments) {
		if(isTraceEnabled()){
			logger.trace(StrUtil.format(format, arguments), t);
		}
	}

	// ------------------------------------------------------------------------- Debug
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public void debug(String format, Object... arguments) {
		if(isDebugEnabled()){
			logger.debug(StrUtil.format(format, arguments));
		}
	}

	public void debug(Throwable t, String format, Object... arguments) {
		if(isDebugEnabled()){
			logger.debug(StrUtil.format(format, arguments), t);
		}
	}

	// ------------------------------------------------------------------------- Info
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public void info(String format, Object... arguments) {
		if(isInfoEnabled()){
			logger.info(StrUtil.format(format, arguments));
		}
	}

	public void info(Throwable t, String format, Object... arguments) {
		if(isInfoEnabled()){
			logger.info(StrUtil.format(format, arguments), t);
		}
	}

	// ------------------------------------------------------------------------- Warn
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	public void warn(String format, Object... arguments) {
		if(isWarnEnabled()){
			logger.warn(StrUtil.format(format, arguments));
		}
	}

	public void warn(Throwable t, String format, Object... arguments) {
		if(isWarnEnabled()){
			logger.warn(StrUtil.format(format, arguments), t);
		}
	}

	// ------------------------------------------------------------------------- Error
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	public void error(String format, Object... arguments) {
		if(isErrorEnabled()){
			logger.error(StrUtil.format(format, arguments));
		}
	}

	public void error(Throwable t, String format, Object... arguments) {
		if(isErrorEnabled()){
			logger.warn(StrUtil.format(format, arguments), t);
		}
	}

	// ------------------------------------------------------------------------- Private method

}
