package com.sunfield.microframe.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.sunfield.microframe.domain.JmWisdomWebcast;

/**
 * jm_wisdom_webcast sql provider
 * @author sunfield coder
 */
public class JmWisdomWebcastSqlProvider{
 
 	private static String COLUMNS = 
 									" id AS id,"+
 									" title AS title,"+
 									" cover_url AS coverUrl,"+
 									" webcast_link AS webcastLink,"+
 									" begin_time AS beginTime,"+
 									" end_time AS endTime,"+
 									" status AS status,"+
 									" create_by AS createBy,"+
 									" create_date AS createDate,"+
 									" update_by AS updateBy,"+
 									" update_date AS updateDate,"+
 									" remarks AS remarks";
 
 	public String generateFindListSql(JmWisdomWebcast obj){
		return new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_wisdom_webcast");
				
				WHERE("status = '0'");
				
				
				
			}
		}.toString();
	}
	
	public String generateFindPageSql(JmWisdomWebcast obj){
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
				FROM("jm_wisdom_webcast");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
	
	public String generateInsertSql(JmWisdomWebcast obj){
		return new SQL(){
			{
				INSERT_INTO("jm_wisdom_webcast");
				
				VALUES("id", "#{id}");
				VALUES("title", "#{title}");
				VALUES("cover_url", "#{coverUrl}");
				VALUES("webcast_link", "#{webcastLink}");
				VALUES("begin_time", "#{beginTime}");
				VALUES("end_time", "#{endTime}");
				VALUES("status", "0");
				VALUES("create_by", "#{createBy}");
				VALUES("create_date", "#{createDate}");
				VALUES("update_by", "#{updateBy}");
				VALUES("update_date", "#{updateDate}");
				VALUES("remarks", "#{remarks}");
			}
		}.toString();
	}
	
	public String generateUpdateSql(JmWisdomWebcast obj){
		return new SQL(){
			{
				UPDATE("jm_wisdom_webcast");
				
				SET("title = #{title}");
				SET("cover_url = #{coverUrl}");
				SET("webcast_link = #{webcastLink}");
				SET("begin_time = #{beginTime}");
				SET("end_time = #{endTime}");
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
				UPDATE("jm_wisdom_webcast");
				
				SET("status = '1'");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
}