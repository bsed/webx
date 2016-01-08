package cn.gnux.core.utils.db.dialect;


import java.sql.Connection;

import javax.sql.DataSource;

import cn.gnux.core.utils.StrUtil;
import cn.gnux.core.utils.db.DbUtil;
import cn.gnux.core.utils.db.dialect.impl.AnsiSqlDialect;
import cn.gnux.core.utils.db.dialect.impl.MysqlDialect;
import cn.gnux.core.utils.db.dialect.impl.OracleDialect;
import cn.gnux.core.utils.db.dialect.impl.PostgresqlDialect;
import cn.gnux.core.utils.db.dialect.impl.Sqllite3Dialect;


/**
 * 方言工厂类
 * @author 
 *
 */
public class DialectFactory {
	
	/** JDBC 驱动 MySQL */
	public final static String DRIVER_MYSQL = "com.mysql.jdbc.Driver";
	/** JDBC 驱动 Oracle */
	public final static String DRIVER_ORACLE = "oracle.jdbc.driver.OracleDriver";
	/** JDBC 驱动 PostgreSQL */
	public final static String DRIVER_POSTGRESQL = "org.postgresql.Driver";
	/** JDBC 驱动 SQLLite3 */
	public final static String DRIVER_SQLLITE3 = "org.sqlite.JDBC";
	
	private DialectFactory() {
	}
	
	/**
	 * 创建方言
	 * @param driverName JDBC驱动类名
	 * @return 方言
	 */
	public static Dialect newDialect(String driverName) {
		if(StrUtil.isNotBlank(driverName)) {
			if(DRIVER_MYSQL.equalsIgnoreCase(driverName)) {
				return new MysqlDialect();
			}else if(DRIVER_ORACLE.equalsIgnoreCase(driverName)) {
				return new OracleDialect();
			}else if(DRIVER_SQLLITE3.equalsIgnoreCase(driverName)) {
				return new Sqllite3Dialect();
			}else if(DRIVER_POSTGRESQL.equalsIgnoreCase(driverName)) {
				return new PostgresqlDialect();
			}
		}
		
		return new AnsiSqlDialect();
	}
	
	/**
	 * 创建方言
	 * @param ds 数据源
	 * @return 方言
	 */
	public static Dialect newDialect(DataSource ds) {
		return newDialect(DbUtil.identifyDriver(ds));
	}
	
	/**
	 * 创建方言
	 * @param conn 数据库连接对象
	 * @return 方言
	 */
	public static Dialect newDialect(Connection conn) {
		return newDialect(DbUtil.identifyDriver(conn));
	}
	
}
