package cn.gnux.core.utils.log.dialect;

import cn.gnux.core.utils.StrUtil;
import cn.gnux.core.utils.log.AbstractLog;



/**
 * <a href="http://www.slf4j.org/">SLF4J</a> log.<br>
 * 同样无缝支持 <a href="http://logback.qos.ch/">LogBack</a>
 * @author Looly
 *
 */
public class Slf4jLog extends AbstractLog {
	private static final long serialVersionUID = -6843151523380063975L;

	private final transient org.slf4j.Logger logger;

	//------------------------------------------------------------------------- Constructor
	public Slf4jLog(org.slf4j.Logger logger) {
		this.logger = logger;
	}
	
	public Slf4jLog(Class<?> clazz) {
		this.logger = org.slf4j.LoggerFactory.getLogger(clazz);
	}
	
	public Slf4jLog(String name) {
		this.logger = org.slf4j.LoggerFactory.getLogger(name);
	}
	
	public String getName() {
		return logger.getName();
	}

	//------------------------------------------------------------------------- Trace
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public void trace(String format, Object... arguments) {
		logger.trace(format, arguments);
	}

	public void trace(Throwable t, String format, Object... arguments) {
		logger.trace(StrUtil.format(format, arguments), t);
	}

	//------------------------------------------------------------------------- Debug
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public void debug(String format, Object... arguments) {
		logger.debug(format, arguments);
	}

	public void debug(Throwable t, String format, Object... arguments) {
		logger.debug(StrUtil.format(format, arguments), t);
	}

	//------------------------------------------------------------------------- Info
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public void info(String format, Object... arguments) {
		logger.info(format, arguments);
	}

	public void info(Throwable t, String format, Object... arguments) {
		logger.info(StrUtil.format(format, arguments), t);
	}

	//------------------------------------------------------------------------- Warn
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	public void warn(String format, Object... arguments) {
		logger.warn(format, arguments);
	}

	public void warn(Throwable t, String format, Object... arguments) {
		logger.warn(StrUtil.format(format, arguments), t);
	}

	//------------------------------------------------------------------------- Error
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	public void error(String format, Object... arguments) {
		logger.error(format, arguments);
	}

	public void error(Throwable t, String format, Object... arguments) {
		logger.error(StrUtil.format(format, arguments), t);
	}

}
