package com.sunfield.microframe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.sunfield.microframe.domain.JmWisdomInterviews;
import com.sunfield.microframe.provider.JmWisdomInterviewsSqlProvider;

/**
 * jm_wisdom_interviews mapper
 * @author sunfield coder
 */
@Mapper
public interface JmWisdomInterviewsMapper{

	/**
	 * 列表查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomInterviewsSqlProvider.class, method="generateFindListSql")
	public List<JmWisdomInterviews> findList(JmWisdomInterviews obj);

	/**
	 * 分页查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomInterviewsSqlProvider.class, method="generateFindPageSql")
	public List<JmWisdomInterviews> findPage(JmWisdomInterviews obj);

	/**
	 * 单行查询
	 * @param id
	 * @return
	 */
	@SelectProvider(type=JmWisdomInterviewsSqlProvider.class, method="generateFindOneSql")
	public JmWisdomInterviews findOne(String id);

	/**
	 * 插入单行
	 * @param obj
	 * @return
	 */
	@InsertProvider(type=JmWisdomInterviewsSqlProvider.class, method="generateInsertSql")
	public int insert(JmWisdomInterviews obj);

	/**
	 * 更新单行
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmWisdomInterviewsSqlProvider.class, method="generateUpdateSql")
	public int update(JmWisdomInterviews obj);

	/**
	 * 删除单行（一般为逻辑删除）
	 * @param id
	 * @return
	 */
	@UpdateProvider(type=JmWisdomInterviewsSqlProvider.class, method="generateDeleteSql")
	public int delete(String id);

}
