package cn.gnux.core.utils.log.dialect;

import cn.gnux.core.utils.StrUtil;
import cn.gnux.core.utils.log.AbstractLog;



/**
 * <a href="http://logging.apache.org/log4j/1.2/index.html">Apache Log4J</a> log.<br>
 * 
 * @author 
 *
 */
public class ApacheCommonsLog extends AbstractLog {
	private static final long serialVersionUID = -6843151523380063975L;

	private final transient org.apache.commons.logging.Log logger;
	private final String name;

	// ------------------------------------------------------------------------- Constructor
	public ApacheCommonsLog(org.apache.commons.logging.Log logger, String name) {
		this.logger = logger;
		this.name = name;
	}

	public ApacheCommonsLog(Class<?> clazz) {
		this.logger = org.apache.commons.logging.LogFactory.getLog(clazz);
		this.name = clazz.getName();
	}

	public ApacheCommonsLog(String name) {
		this.logger = org.apache.commons.logging.LogFactory.getLog(name);
		this.name = name;
	}

	public String getName() {
		return this.name;
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
