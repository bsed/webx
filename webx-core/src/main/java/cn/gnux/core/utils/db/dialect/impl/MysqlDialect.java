package cn.gnux.core.utils.db.dialect.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;


import cn.gnux.core.utils.StrUtil;
import cn.gnux.core.utils.db.DbUtil;
import cn.gnux.core.utils.db.Entity;
import cn.gnux.core.utils.db.Page;
import cn.gnux.core.utils.db.sql.Order;
import cn.gnux.core.utils.db.sql.SqlBuilder;
import cn.gnux.core.utils.db.sql.SqlBuilder.LogicalOperator;
import cn.gnux.core.utils.db.sql.Wrapper;
import cn.gnux.core.utils.exceptions.DbRuntimeException;


/**
 * MySQL方言
 * @author 
 *
 */
public class MysqlDialect extends AnsiSqlDialect{
	
	public MysqlDialect() {
		wrapper = new Wrapper('`');
	}

	@Override
	public PreparedStatement psForPage(Connection conn, Collection<String> fields, Entity where, Page page) throws SQLException {
		//验证
		if(where == null || StrUtil.isBlank(where.getTableName())) {
			throw new DbRuntimeException("Table name is null !");
		}
		
		final SqlBuilder find = SqlBuilder.create(wrapper)
				.select(fields)
				.from(where.getTableName())
				.where(LogicalOperator.AND, DbUtil.buildConditions(where));
		
		final Order order = page.getOrder();
		if(null != order){
			find.orderBy(order);
		}
		
		find.append(" LIMIT ").append(page.getStartPosition()).append(", ").append(page.getNumPerPage());
		
		final PreparedStatement ps = conn.prepareStatement(find.build());
		DbUtil.fillParams(ps, find.getParamValueArray());
		return ps;
	}
}
