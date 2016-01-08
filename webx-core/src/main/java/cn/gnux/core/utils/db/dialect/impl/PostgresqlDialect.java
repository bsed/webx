package cn.gnux.core.utils.db.dialect.impl;

import cn.gnux.core.utils.db.sql.Wrapper;




/**
 * SqlLite3方言
 * @author 
 *
 */
public class PostgresqlDialect extends AnsiSqlDialect{
	public PostgresqlDialect() {
		wrapper = new Wrapper('"');
	}

}
