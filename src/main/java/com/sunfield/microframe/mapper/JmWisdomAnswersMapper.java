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
	 * �б��ѯ
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomAnswersSqlProvider.class, method="generateFindListSql")
	public List<JmWisdomAnswers> findList(JmWisdomAnswers obj);

	/**
	 * ��ҳ��ѯ
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomAnswersSqlProvider.class, method="generateFindPageSql")
	public List<JmWisdomAnswers> findPage(JmWisdomAnswers obj);

	/**
	 * ���в�ѯ
	 * @param id
	 * @return
	 */
	@SelectProvider(type=JmWisdomAnswersSqlProvider.class, method="generateFindOneSql")
	public JmWisdomAnswers findOne(String id);

	/**
	 * ���뵥��
	 * @param obj
	 * @return
	 */
	@InsertProvider(type=JmWisdomAnswersSqlProvider.class, method="generateInsertSql")
	public int insert(JmWisdomAnswers obj);

	/**
	 * ���µ���
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmWisdomAnswersSqlProvider.class, method="generateUpdateSql")
	public int update(JmWisdomAnswers obj);

	/**
	 * ɾ�����У�һ��Ϊ�߼�ɾ����
	 * @param id
	 * @return
	 */
	@UpdateProvider(type=JmWisdomAnswersSqlProvider.class, method="generateDeleteSql")
	public int delete(String id);
	
}
