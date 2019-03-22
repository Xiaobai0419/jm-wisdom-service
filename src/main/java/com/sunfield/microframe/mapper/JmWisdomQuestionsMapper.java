package com.sunfield.microframe.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.*;

import com.sunfield.microframe.domain.JmWisdomQuestions;
import com.sunfield.microframe.provider.JmWisdomQuestionsSqlProvider;

/**
 * jm_wisdom_questions mapper
 * @author sunfield coder
 */
@Mapper
public interface JmWisdomQuestionsMapper{

	/**
	 * 列表查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateFindListSql")
	public List<JmWisdomQuestions> findList(JmWisdomQuestions obj);

	/**
	 * 列表查询--用户id批量
	 * @param userIds
	 * @return
	 */
	@SelectProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateFindByUserIdsSql")
	public List<JmWisdomQuestions> findByUserIds(String[] userIds, @Param("dateStart") Date dateStart,
												 @Param("dateEnd") Date dateEnd);

	/**
	 * 分页查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateFindPageSql")
	public List<JmWisdomQuestions> findPage(JmWisdomQuestions obj);

	/**
	 * 分页查询--用户id批量
	 * @param userIds
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@SelectProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateFindByUserIdsPageSql")
	public List<JmWisdomQuestions> findByUserIdsPage(String[] userIds, @Param("dateStart") Date dateStart,
													 @Param("dateEnd") Date dateEnd,
													 Integer pageNumber, Integer pageSize);

	/**
	 * 单行查询
	 * @param id
	 * @return
	 */
	@SelectProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateFindOneSql")
	public JmWisdomQuestions findOne(String id);

	/**
	 * 插入单行
	 * @param obj
	 * @return
	 */
	@InsertProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateInsertSql")
	public int insert(JmWisdomQuestions obj);

	/**
	 * 更新单行
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateUpdateSql")
	public int update(JmWisdomQuestions obj);

	/**
	 * 更新单行--专门用于用户取消赞/踩/收藏时对应字段数值减1
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateUpdateCancelSql")
	public int updateCancel(JmWisdomQuestions obj);

	/**
	 * 删除单行（一般为逻辑删除）
	 * @param id
	 * @return
	 */
	@UpdateProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateDeleteSql")
	public int delete(String id);
	
}
