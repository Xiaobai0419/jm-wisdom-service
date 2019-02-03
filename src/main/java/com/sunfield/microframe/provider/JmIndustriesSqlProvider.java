package com.sunfield.microframe.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.sunfield.microframe.domain.JmIndustries;

/**
 * jm_industries sql provider
 * @author sunfield coder
 */
public class JmIndustriesSqlProvider{
 
 	private static String COLUMNS = 
 									" id AS id,"+
 									" name AS name,"+
 									" status AS status,"+
 									" create_by AS createBy,"+
 									" create_date AS createDate,"+
 									" update_by AS updateBy,"+
 									" update_date AS updateDate,"+
 									" remarks AS remarks";
 
 	public String generateFindListSql(JmIndustries obj){
		return new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_industries");
				
				WHERE("status = '0'");
				
				
				
			}
		}.toString();
	}
	
	public String generateFindPageSql(JmIndustries obj){
		StringBuilder sql = new StringBuilder(generateFindListSql(obj));
		sql.append(" LIMIT ");
		sql.append((obj.getPageNumber() - 1) * obj.getPageSize());
		sql.append(", ");
		sql.append(obj.getPageSize());
		return sql.toString();
	}
 
 	public String generateFindOneSql(String id){
		return new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_industries");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
	
	public String generateInsertSql(JmIndustries obj){
		return new SQL(){
			{
				INSERT_INTO("jm_industries");
				
				VALUES("id", "#{id}");
				VALUES("name", "#{name}");
				VALUES("status", "0");
				VALUES("create_by", "#{createBy}");
				VALUES("create_date", "#{createDate}");
				VALUES("update_by", "#{updateBy}");
				VALUES("update_date", "#{updateDate}");
				VALUES("remarks", "#{remarks}");
			}
		}.toString();
	}
	
	public String generateUpdateSql(JmIndustries obj){
		return new SQL(){
			{
				UPDATE("jm_industries");
				
				SET("name = #{name}");
				SET("update_by = #{updateBy}");
				SET("update_date = #{updateDate}");
				SET("remarks = #{remarks}");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
	
	public String generateDeleteSql(String id){
		return new SQL(){
			{
				UPDATE("jm_industries");
				
				SET("status = '1'");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
}