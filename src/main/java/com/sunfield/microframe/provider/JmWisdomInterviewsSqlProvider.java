package com.sunfield.microframe.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.sunfield.microframe.domain.JmWisdomInterviews;

/**
 * jm_wisdom_interviews sql provider
 * @author sunfield coder
 */
public class JmWisdomInterviewsSqlProvider{
 
 	private static String COLUMNS = 
 									" id AS id,"+
 									" title AS title,"+
 									" content AS content,"+
 									" cover_url AS coverUrl,"+
 									" select_order AS selectOrder,"+
 									" video_select_order AS videoSelectOrder,"+
 									" favorites AS favorites,"+
 									" status AS status,"+
 									" create_by AS createBy,"+
 									" create_date AS createDate,"+
 									" update_by AS updateBy,"+
 									" update_date AS updateDate,"+
 									" remarks AS remarks";
 
 	public String generateFindListSql(JmWisdomInterviews obj){
		return new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_wisdom_interviews");
				
				WHERE("status = '0'");
				
				
				
			}
		}.toString();
	}
	
	public String generateFindPageSql(JmWisdomInterviews obj){
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
				FROM("jm_wisdom_interviews");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
	
	public String generateInsertSql(JmWisdomInterviews obj){
		return new SQL(){
			{
				INSERT_INTO("jm_wisdom_interviews");
				
				VALUES("id", "#{id}");
				VALUES("title", "#{title}");
				VALUES("content", "#{content}");
				VALUES("cover_url", "#{coverUrl}");
				VALUES("select_order", "#{selectOrder}");
				VALUES("video_select_order", "#{videoSelectOrder}");
				VALUES("favorites", "#{favorites}");
				VALUES("status", "0");
				VALUES("create_by", "#{createBy}");
				VALUES("create_date", "#{createDate}");
				VALUES("update_by", "#{updateBy}");
				VALUES("update_date", "#{updateDate}");
				VALUES("remarks", "#{remarks}");
			}
		}.toString();
	}
	
	public String generateUpdateSql(JmWisdomInterviews obj){
		return new SQL(){
			{
				UPDATE("jm_wisdom_interviews");
				
				SET("title = #{title}");
				SET("content = #{content}");
				SET("cover_url = #{coverUrl}");
				SET("select_order = #{selectOrder}");
				SET("video_select_order = #{videoSelectOrder}");
				SET("favorites = #{favorites}");
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
				UPDATE("jm_wisdom_interviews");
				
				SET("status = '1'");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
}