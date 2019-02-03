package com.sunfield.microframe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.sunfield.microframe.domain.JmAppUser;
import com.sunfield.microframe.provider.JmAppUserSqlProvider;

/**
 * jm_app_user mapper
 * @author sunfield coder
 */
@Mapper
public interface JmAppUserMapper{
	/**
	 * 列表查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmAppUserSqlProvider.class, method="generateFindListSql")
	public List<JmAppUser> findList(JmAppUser obj);

	/**
	 * 分页查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmAppUserSqlProvider.class, method="generateFindPageSql")
	public List<JmAppUser> findPage(JmAppUser obj);

	/**
	 * 单行查询
	 * @param id
	 * @return
	 */
	@SelectProvider(type=JmAppUserSqlProvider.class, method="generateFindOneSql")
	public JmAppUser findOne(String id);

	/**
	 * 插入单行
	 * @param obj
	 * @return
	 */
	@InsertProvider(type=JmAppUserSqlProvider.class, method="generateInsertSql")
	public int insert(JmAppUser obj);

	/**
	 * 更新单行
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmAppUserSqlProvider.class, method="generateUpdateSql")
	public int update(JmAppUser obj);

	/**
	 * 删除单行（一般为逻辑删除）
	 * @param id
	 * @return
	 */
	@UpdateProvider(type=JmAppUserSqlProvider.class, method="generateDeleteSql")
	public int delete(String id);
	
}
