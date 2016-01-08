package cn.gnux.core.utils.db.ds;


import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * Data source abstraction
 * @author 
 *
 */
public abstract class AbstractDataSource implements DataSource, Cloneable{
	public PrintWriter getLogWriter() throws SQLException {
		return DriverManager.getLogWriter();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		DriverManager.setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		DriverManager.setLoginTimeout(seconds);
	}

	public int getLoginTimeout() throws SQLException {
		return DriverManager.getLoginTimeout();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Can't support unwrap method!");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Can't support isWrapperFor method!");
	}

	/**
	 * Support from JDK7
	 */
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException("Simple DataSource can't support getParentLogger method!");
	}
}
