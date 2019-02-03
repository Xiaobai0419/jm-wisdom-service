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
	 * �б��ѯ
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateFindListSql")
	public List<JmWisdomUserQuestions> findList(JmWisdomUserQuestions obj);

	/**
	 * ��ҳ��ѯ
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateFindPageSql")
	public List<JmWisdomUserQuestions> findPage(JmWisdomUserQuestions obj);

	/**
	 * ���в�ѯ
	 * @param id
	 * @return
	 */
	@SelectProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateFindOneSql")
	public JmWisdomUserQuestions findOne(String id);

	/**
	 * ���뵥��
	 * @param obj
	 * @return
	 */
	@InsertProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateInsertSql")
	public int insert(JmWisdomUserQuestions obj);

	/**
	 * ���µ���
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateUpdateSql")
	public int update(JmWisdomUserQuestions obj);

	/**
	 * ɾ�����У�һ��Ϊ�߼�ɾ����
	 * @param id
	 * @return
	 */
	@UpdateProvider(type=JmWisdomUserQuestionsSqlProvider.class, method="generateDeleteSql")
	public int delete(String id);
	
}
