package com.sunfield.microframe.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.sunfield.microframe.domain.MUsers;

/**
 * m_users sql provider
 * @author sunfield coder
 */
public class MUsersSqlProvider{
 
 	private static String COLUMNS = 
 									" id AS id,"+
 									" mobile AS mobile,"+
 									" nick_name AS nickName,"+
 									" age AS age,"+
 									" status AS status,"+
 									" create_by AS createBy,"+
 									" create_date AS createDate,"+
 									" update_by AS updateBy,"+
 									" update_date AS updateDate,"+
 									" remarks AS remarks";
 
 	public String generateFindListSql(MUsers obj){
		return new SQL(){
			{
				SELECT(COLUMNS);
				FROM("m_users");
				
				WHERE("status = '0'");
				
				
				if(StringUtils.isNotBlank(obj.getMobile())){
					WHERE("mobile LIKE CONCAT('%',#{mobile},'%')");
				}
				if(StringUtils.isNotBlank(obj.getNickName())){
					WHERE("nick_name LIKE CONCAT('%',#{nickName},'%')");
				}
				
			}
		}.toString();
	}
	
	public String generateFindPageSql(MUsers obj){
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
				FROM("m_users");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
	
	public String generateInsertSql(MUsers obj){
		return new SQL(){
			{
				INSERT_INTO("m_users");
				
				VALUES("id", "#{id}");
				VALUES("mobile", "#{mobile}");
				VALUES("nick_name", "#{nickName}");
				VALUES("age", "#{age}");
				VALUES("status", "0");
				VALUES("create_by", "#{createBy}");
				VALUES("create_date", "#{createDate}");
				VALUES("update_by", "#{updateBy}");
				VALUES("update_date", "#{updateDate}");
				VALUES("remarks", "#{remarks}");
			}
		}.toString();
	}
	
	public String generateUpdateSql(MUsers obj){
		return new SQL(){
			{
				UPDATE("m_users");
				
				SET("mobile = #{mobile}");
				SET("nick_name = #{nickName}");
				SET("age = #{age}");
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
				UPDATE("m_users");
				
				SET("status = '1'");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
}