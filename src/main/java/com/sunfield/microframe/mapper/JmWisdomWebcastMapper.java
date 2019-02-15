package com.sunfield.microframe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.sunfield.microframe.domain.JmWisdomWebcast;
import com.sunfield.microframe.provider.JmWisdomWebcastSqlProvider;

/**
 * jm_wisdom_webcast mapper
 * @author sunfield coder
 */
@Mapper
public interface JmWisdomWebcastMapper{

	/**
	 * 列表查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomWebcastSqlProvider.class, method="generateFindListSql")
	public List<JmWisdomWebcast> findList(JmWisdomWebcast obj);

	/**
	 * 分页查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomWebcastSqlProvider.class, method="generateFindPageSql")
	public List<JmWisdomWebcast> findPage(JmWisdomWebcast obj);

	/**
	 * 单行查询
	 * @param id
	 * @return
	 */
	@SelectProvider(type=JmWisdomWebcastSqlProvider.class, method="generateFindOneSql")
	public JmWisdomWebcast findOne(String id);

	/**
	 * 当前直播查询
	 * @param
	 * @return
	 */
	@SelectProvider(type=JmWisdomWebcastSqlProvider.class, method="generateFindCurrentSql")
	public JmWisdomWebcast findCurrent();

	/**
	 * 插入单行
	 * @param obj
	 * @return
	 */
	@InsertProvider(type=JmWisdomWebcastSqlProvider.class, method="generateInsertSql")
	public int insert(JmWisdomWebcast obj);

	/**
	 * 更新单行
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmWisdomWebcastSqlProvider.class, method="generateUpdateSql")
	public int update(JmWisdomWebcast obj);

	/**
	 * 删除单行（一般为逻辑删除）
	 * @param id
	 * @return
	 */
	@UpdateProvider(type=JmWisdomWebcastSqlProvider.class, method="generateDeleteSql")
	public int delete(String id);

}
