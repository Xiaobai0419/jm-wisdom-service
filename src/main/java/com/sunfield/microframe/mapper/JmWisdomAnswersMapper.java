package com.sunfield.microframe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.sunfield.microframe.domain.JmWisdomAnswers;
import com.sunfield.microframe.provider.JmWisdomAnswersSqlProvider;

/**
 * jm_wisdom_answers mapper
 * @author sunfield coder
 */
@Mapper
public interface JmWisdomAnswersMapper{

	/**
	 * 列表查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomAnswersSqlProvider.class, method="generateFindListSql")
	public List<JmWisdomAnswers> findList(JmWisdomAnswers obj);

	/**
	 * 分页查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomAnswersSqlProvider.class, method="generateFindPageSql")
	public List<JmWisdomAnswers> findPage(JmWisdomAnswers obj);

	/**
	 * 单行查询
	 * @param id
	 * @return
	 */
	@SelectProvider(type=JmWisdomAnswersSqlProvider.class, method="generateFindOneSql")
	public JmWisdomAnswers findOne(String id);

	/**
	 * 插入单行
	 * @param obj
	 * @return
	 */
	@InsertProvider(type=JmWisdomAnswersSqlProvider.class, method="generateInsertSql")
	public int insert(JmWisdomAnswers obj);

	/**
	 * 更新单行
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmWisdomAnswersSqlProvider.class, method="generateUpdateSql")
	public int update(JmWisdomAnswers obj);

	/**
	 * 删除单行（一般为逻辑删除）
	 * @param id
	 * @return
	 */
	@UpdateProvider(type=JmWisdomAnswersSqlProvider.class, method="generateDeleteSql")
	public int delete(String id);
	
}