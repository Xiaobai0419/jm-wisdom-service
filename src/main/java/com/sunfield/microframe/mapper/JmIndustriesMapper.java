package com.sunfield.microframe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.sunfield.microframe.domain.JmIndustries;
import com.sunfield.microframe.provider.JmIndustriesSqlProvider;

/**
 * jm_industries mapper
 * @author sunfield coder
 */
@Mapper
public interface JmIndustriesMapper{

	/**
	 * �б��ѯ
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmIndustriesSqlProvider.class, method="generateFindListSql")
	public List<JmIndustries> findList(JmIndustries obj);

	/**
	 * ��ҳ��ѯ
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmIndustriesSqlProvider.class, method="generateFindPageSql")
	public List<JmIndustries> findPage(JmIndustries obj);

	/**
	 * ���в�ѯ
	 * @param id
	 * @return
	 */
	@SelectProvider(type=JmIndustriesSqlProvider.class, method="generateFindOneSql")
	public JmIndustries findOne(String id);

	/**
	 * ���뵥��
	 * @param obj
	 * @return
	 */
	@InsertProvider(type=JmIndustriesSqlProvider.class, method="generateInsertSql")
	public int insert(JmIndustries obj);

	/**
	 * ���µ���
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmIndustriesSqlProvider.class, method="generateUpdateSql")
	public int update(JmIndustries obj);

	/**
	 * ɾ�����У�һ��Ϊ�߼�ɾ����
	 * @param id
	 * @return
	 */
	@UpdateProvider(type=JmIndustriesSqlProvider.class, method="generateDeleteSql")
	public int delete(String id);
	
}
