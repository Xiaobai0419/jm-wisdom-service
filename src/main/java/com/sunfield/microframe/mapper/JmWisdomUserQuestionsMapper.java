package com.sunfield.microframe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.sunfield.microframe.domain.JmWisdomUserQuestions;
import com.sunfield.microframe.provider.JmWisdomUserQuestionsSqlProvider;

/**
 * jm_wisdom_user_questions mapper
 * @author sunfield coder
 */
@Mapper
public interface JmWisdomUserQuestionsMapper{

	/**
	 * 列表查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateFindListSql")
	public List<JmWisdomUserQuestions> findList(JmWisdomUserQuestions obj);

	/**
	 * 分页查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateFindPageSql")
	public List<JmWisdomUserQuestions> findPage(JmWisdomUserQuestions obj);

	/**
	 * 单行查询
	 * @param id
	 * @return
	 */
//	@SelectProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateFindOneSql")
//	public JmWisdomUserQuestions findOne(String id);

	/**
	 * 用户获取自身对各类目标对象的赞、踩情况
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateFindSelfSql")
	public JmWisdomUserQuestions findOne(JmWisdomUserQuestions obj);

	/**
	 * 插入单行
	 * @param obj
	 * @return
	 */
	@InsertProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateInsertSql")
	public int insert(JmWisdomUserQuestions obj);

	/**
	 * 更新单行
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateUpdateSql")
	public int update(JmWisdomUserQuestions obj);

	/**
	 * 删除单行（一般为逻辑删除）
	 * @param id
	 * @return
	 */
//	@UpdateProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateDeleteSql")
//	public int delete(String id);

	/**
	 * 用户取消踩/赞/收藏时，进行逻辑删除
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateDeleteSelfSql")
	public int delete(JmWisdomUserQuestions obj);
}
