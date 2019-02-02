package com.sunfield.microframe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.sunfield.microframe.domain.MUsers;
import com.sunfield.microframe.provider.MUsersSqlProvider;

/**
 * m_users mapper
 * @author sunfield coder
 */
@Mapper
public interface MUsersMapper{

	@SelectProvider(type=MUsersSqlProvider.class, method="generateFindListSql")
	public List<MUsers> findList(MUsers obj);
	
	@SelectProvider(type=MUsersSqlProvider.class, method="generateFindPageSql")
	public List<MUsers> findPage(MUsers obj);
	
	@SelectProvider(type=MUsersSqlProvider.class, method="generateFindOneSql")
	public MUsers findOne(String id);
	
	@InsertProvider(type=MUsersSqlProvider.class, method="generateInsertSql")
	public int insert(MUsers obj);
	
	@UpdateProvider(type=MUsersSqlProvider.class, method="generateUpdateSql")
	public int update(MUsers obj);
	
	@UpdateProvider(type=MUsersSqlProvider.class, method="generateDeleteSql")
	public int delete(String id);
	
}
