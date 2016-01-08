package cn.gnux.core.utils.db.dialect.impl;

import cn.gnux.core.utils.db.sql.Wrapper;



/**
 * SqlLite3方言
 * @author 
 *
 */
public class Sqllite3Dialect extends AnsiSqlDialect{
	public Sqllite3Dialect() {
		wrapper = new Wrapper('[', ']');
	}
}
