package cn.gnux.core.utils.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import cn.gnux.core.utils.CollectionUtil;
import cn.gnux.core.utils.db.dialect.Dialect;
import cn.gnux.core.utils.db.dialect.DialectFactory;
import cn.gnux.core.utils.db.handler.EntityListHandler;
import cn.gnux.core.utils.db.handler.NumberHandler;
import cn.gnux.core.utils.db.handler.PageResultHandler;
import cn.gnux.core.utils.db.handler.RsHandler;
import cn.gnux.core.utils.log.StaticLog;

/**
 * SQL执行类<br>
 * 此执行类只接受方言参数，不需要数据源，只有在执行方法时需要数据库连接对象<br>
 * 此对象存在的意义在于，可以由使用者自定义数据库连接对象，并执行多个方法，方便事务的统一控制或减少连接对象的创建关闭
 * @author 
 * 
 */
public class SqlConnRunner{
	private Dialect dialect;
	
	//------------------------------------------------------- Constructor start
	/**
	 * 构造
	 * @param dialect 方言
	 */
	public SqlConnRunner(Dialect dialect) {
		StaticLog.info("Use Dialect: [{}].", dialect.getClass().getSimpleName());
		
		this.dialect = dialect;
	}
	
	/**
	 * 构造
	 * @param driverClassName 驱动类名，，用于识别方言
	 */
	public SqlConnRunner(String driverClassName) {
		this(DialectFactory.newDialect(driverClassName));
	}
	//------------------------------------------------------- Constructor end

	//---------------------------------------------------------------------------- CRUD start
	/**
	 * 插入数据<br>
	 * 此方法不会关闭Connection
	 * @param conn 数据库连接
	 * @param record 记录
	 * @return 插入行数
	 * @throws SQLException
	 */
	public int insert(Connection conn, Entity record) throws SQLException {
		checkConn(conn);
		if(CollectionUtil.isEmpty(record)){
			throw new SQLException("Empty entity provided!");
		}
		PreparedStatement ps = null;
		try {
			ps = dialect.psForInsert(conn, record);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(ps);
		}
	}
	
	/**
	 * 批量插入数据<br>
	 * 此方法不会关闭Connection
	 * @param conn 数据库连接
	 * @param records 记录列表
	 * @return 插入行数
	 * @throws SQLException
	 */
	public int[] insert(Connection conn, Collection<Entity> records) throws SQLException {
		checkConn(conn);
		if(CollectionUtil.isEmpty(records)){
			return new int[]{0};
		}
		Entity template = records.iterator().next();
		PreparedStatement ps = null;
		try {
			ps = dialect.psForInsert(conn, template);
			ps.clearBatch();
			for (Entity entity : records) {
				DbUtil.fillParams(ps, entity.values().toArray(new Object[entity.size()]));
				ps.addBatch();
			}
			return ps.executeBatch();
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(ps);
		}
	}
	
	/**
	 * 插入数据<br>
	 * 此方法不会关闭Connection
	 * @param conn 数据库连接
	 * @param record 记录
	 * @return 主键列表
	 * @throws SQLException
	 */
	public List<Object> insertForGeneratedKeys(Connection conn, Entity record) throws SQLException {
		checkConn(conn);
		if(CollectionUtil.isEmpty(record)){
			throw new SQLException("Empty entity provided!");
		}
		
		PreparedStatement ps = null;
		try {
			ps = dialect.psForInsert(conn, record);
			ps.executeUpdate();
			return DbUtil.getGeneratedKeys(ps);
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(ps);
		}
	}
	
	/**
	 * 插入数据<br>
	 * 此方法不会关闭Connection
	 * @param conn 数据库连接
	 * @param record 记录
	 * @return 自增主键
	 * @throws SQLException
	 */
	public Long insertForGeneratedKey(Connection conn, Entity record) throws SQLException {
		checkConn(conn);
		if(CollectionUtil.isEmpty(record)){
			throw new SQLException("Empty entity provided!");
		}
		
		PreparedStatement ps = null;
		try {
			ps = dialect.psForInsert(conn, record);
			ps.executeUpdate();
			return DbUtil.getGeneratedKeyOfLong(ps);
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(ps);
		}
	}

	/**
	 * 删除数据<br>
	 * 此方法不会关闭Connection
	 * @param conn 数据库连接
	 * @param where 条件
	 * @return 影响行数
	 * @throws SQLException
	 */
	public int del(Connection conn, Entity where) throws SQLException {
		checkConn(conn);
		if(CollectionUtil.isEmpty(where)){
			//不允许做全表删除
			throw new SQLException("Empty entity provided!");
		}
		
		PreparedStatement ps = null;
		try {
			ps = dialect.psForDelete(conn, where);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(ps);
		}
	}
	
	/**
	 * 更新数据<br>
	 * 此方法不会关闭Connection
	 * @param conn 数据库连接
	 * @param record 记录
	 * @return 影响行数
	 * @throws SQLException
	 */
	public int update(Connection conn, Entity record, Entity where) throws SQLException {
		checkConn(conn);
		if(CollectionUtil.isEmpty(record)){
			throw new SQLException("Empty entity provided!");
		}
		if(CollectionUtil.isEmpty(where)){
			//不允许做全表更新
			throw new SQLException("Empty where provided!");
		}
		
		PreparedStatement ps = null;
		try {
			ps = dialect.psForUpdate(conn, record, where);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(ps);
		}
	}
	
	/**
	 * 查询<br>
	 * 此方法不会关闭Connection
	 * 
	 * @param conn 数据库连接对象
	 * @param fields 返回的字段列表，null则返回所有字段
	 * @param where 条件实体类（包含表名）
	 * @param rsh 结果集处理对象
	 * @return 结果对象
	 * @throws SQLException
	 */
	public <T> T find(Connection conn, Collection<String> fields, Entity where, RsHandler<T> rsh) throws SQLException {
		checkConn(conn);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = dialect.psForFind(conn, fields, where);
			rs = ps.executeQuery();
			return rsh.handle(rs);
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(rs, ps);
		}
	}
	
	/**
	 * 查询，返回所有字段<br>
	 * 此方法不会关闭Connection
	 * 
	 * @param conn 数据库连接对象
	 * @param where 条件实体类（包含表名）
	 * @param rsh 结果集处理对象
	 * @return 结果对象
	 * @throws SQLException
	 */
	public <T> T find(Connection conn, Entity where, RsHandler<T> rsh) throws SQLException {
		return find(conn, null, where, rsh);
	}
	
	/**
	 * 结果的条目数
	 * @param conn 数据库连接对象
	 * @param where 查询条件
	 * @return 复合条件的结果数
	 * @throws SQLException
	 */
	public int count(Connection conn, Entity where) throws SQLException {
		checkConn(conn);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = dialect.psForCount(conn, where);
			rs = ps.executeQuery();
			return new NumberHandler().handle(rs).intValue();
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(rs, ps);
		}
	}
	
	/**
	 * 分页查询<br>
	 * 此方法不会关闭Connection
	 * 
	 * @param conn 数据库连接对象
	 * @param fields 返回的字段列表，null则返回所有字段
	 * @param where 条件实体类（包含表名）
	 * @param page 页码
	 * @param numPerPage 每页条目数
	 * @param rsh 结果集处理对象
	 * @return 结果对象
	 * @throws SQLException
	 */
	public <T> T page(Connection conn, Collection<String> fields, Entity where, int page, int numPerPage, RsHandler<T> rsh) throws SQLException {
		checkConn(conn);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = dialect.psForPage(conn, fields, where, page, numPerPage);
			rs = ps.executeQuery();
			return rsh.handle(rs);
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(rs, ps);
		}
	}
	
	/**
	 * 分页查询<br>
	 * 此方法不会关闭Connection
	 * 
	 * @param conn 数据库连接对象
	 * @param fields 返回的字段列表，null则返回所有字段
	 * @param where 条件实体类（包含表名）
	 * @param page 分页对象
	 * @param rsh 结果集处理对象
	 * @return 结果对象
	 * @throws SQLException
	 */
	public <T> T page(Connection conn, Collection<String> fields, Entity where, Page page, RsHandler<T> rsh) throws SQLException {
		checkConn(conn);
		if(null == page){
			return this.find(conn, fields, where, rsh);
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = dialect.psForPage(conn, fields, where, page);
			rs = ps.executeQuery();
			return rsh.handle(rs);
		} catch (SQLException e) {
			throw e;
		} finally {
			DbUtil.close(rs, ps);
		}
	}
	
	/**
	 * 分页查询<br>
	 * 此方法不会关闭Connection
	 * 
	 * @param conn 数据库连接对象
	 * @param fields 返回的字段列表，null则返回所有字段
	 * @param where 条件实体类（包含表名）
	 * @param page 页码
	 * @param numPerPage 每页条目数
	 * @return 结果对象
	 * @throws SQLException
	 */
	public PageResult<Entity> page(Connection conn, Collection<String> fields, Entity where, int page, int numPerPage) throws SQLException {
		checkConn(conn);
		
		final int count = count(conn, where);
		PageResultHandler pageResultHandler = PageResultHandler.create(new PageResult<Entity>(page, numPerPage, count));
		return this.page(conn, fields, where, page, numPerPage, pageResultHandler);
	}
	
	/**
	 * 分页查询<br>
	 * 此方法不会关闭Connection
	 * 
	 * @param conn 数据库连接对象
	 * @param fields 返回的字段列表，null则返回所有字段
	 * @param where 条件实体类（包含表名）
	 * @param page 分页对象
	 * @return 结果对象
	 * @throws SQLException
	 */
	public PageResult<Entity> page(Connection conn, Collection<String> fields, Entity where, Page page) throws SQLException {
		checkConn(conn);
		
		//查询全部
		if(null == page){
			List<Entity> entityList = this.find(conn, fields, where, new EntityListHandler());
			PageResult<Entity> pageResult = new PageResult<Entity>(0, entityList.size(), entityList.size());
			pageResult.addAll(entityList);
			return pageResult;
		}
		
		final int count = count(conn, where);
		PageResultHandler pageResultHandler = PageResultHandler.create(new PageResult<Entity>(page.getPageNumber(), page.getNumPerPage(), count));
		return this.page(conn, fields, where, page, pageResultHandler);
	}
	
	/**
	 * 分页全字段查询<br>
	 * 此方法不会关闭Connection
	 * 
	 * @param conn 数据库连接对象
	 * @param where 条件实体类（包含表名）
	 * @param page 分页对象
	 * @return 结果对象
	 * @throws SQLException
	 */
	public PageResult<Entity> page(Connection conn, Entity where, Page page) throws SQLException {
		return this.page(conn, null, where, page);
	}
	//---------------------------------------------------------------------------- CRUD end
	
	//---------------------------------------------------------------------------- Getters and Setters end
	/**
	 * @return SQL方言
	 */
	public Dialect getDialect() {
		return dialect;
	}
	/**
	 * 设置SQL方言
	 * @param dialect 方言
	 */
	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}
	//---------------------------------------------------------------------------- Getters and Setters end
	
	//---------------------------------------------------------------------------- Private method start
	private void checkConn(Connection conn){
		if(null == conn){
			throw new NullPointerException("Connection object is null!");
		}
	}
	//---------------------------------------------------------------------------- Private method start
}