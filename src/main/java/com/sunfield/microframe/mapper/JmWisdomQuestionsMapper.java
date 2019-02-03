package com.sunfield.microframe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.sunfield.microframe.domain.JmWisdomQuestions;
import com.sunfield.microframe.provider.JmWisdomQuestionsSqlProvider;

/**
 * jm_wisdom_questions mapper
 * @author sunfield coder
 */
@Mapper
public interface JmWisdomQuestionsMapper{

	/**
	 * �б��ѯ
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateFindListSql")
	public List<JmWisdomQuestions> findList(JmWisdomQuestions obj);

	/**
	 * ��ҳ��ѯ
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateFindPageSql")
	public List<JmWisdomQuestions> findPage(JmWisdomQuestions obj);

	/**
	 * ���в�ѯ
	 * @param id
	 * @return
	 */
	@SelectProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateFindOneSql")
	public JmWisdomQuestions findOne(String id);

	/**
	 * ���뵥��
	 * @param obj
	 * @return
	 */
	@InsertProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateInsertSql")
	public int insert(JmWisdomQuestions obj);

	/**
	 * ���µ���
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateUpdateSql")
	public int update(JmWisdomQuestions obj);

	/**
	 * ɾ�����У�һ��Ϊ�߼�ɾ����
	 * @param id
	 * @return
	 */
	@UpdateProvider(type=JmWisdomQuestionsSqlProvider.class, method="generateDeleteSql")
	public int delete(String id);
	
}
