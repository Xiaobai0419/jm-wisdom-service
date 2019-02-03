package com.sunfield.microframe.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.sunfield.microframe.domain.JmWisdomQuestions;

/**
 * jm_wisdom_questions sql provider
 * @author sunfield coder
 */
public class JmWisdomQuestionsSqlProvider{
 
 	private static String COLUMNS = 
 									" id AS id,"+
 									" title AS title,"+
 									" industry_id AS industryId,"+
 									" user_id AS userId,"+
 									" content AS content,"+
 									" oss_urls AS ossUrls,"+
 									" ayes AS ayes,"+
 									" antis AS antis,"+
 									" answers AS answers,"+
 									" status AS status,"+
 									" create_by AS createBy,"+
 									" create_date AS createDate,"+
 									" update_by AS updateBy,"+
 									" update_date AS updateDate,"+
 									" remarks AS remarks,"+
 									" order AS order";
 
 	public String generateFindListSql(JmWisdomQuestions obj){
		return new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_wisdom_questions");
				
				WHERE("status = '0'");
				
				
				
			}
		}.toString();
	}
	
	public String generateFindPageSql(JmWisdomQuestions obj){
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
				FROM("jm_wisdom_questions");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
	
	public String generateInsertSql(JmWisdomQuestions obj){
		return new SQL(){
			{
				INSERT_INTO("jm_wisdom_questions");
				
				VALUES("id", "#{id}");
				VALUES("title", "#{title}");
				VALUES("industry_id", "#{industryId}");
				VALUES("user_id", "#{userId}");
				VALUES("content", "#{content}");
				VALUES("oss_urls", "#{ossUrls}");
				VALUES("ayes", "#{ayes}");
				VALUES("antis", "#{antis}");
				VALUES("answers", "#{answers}");
				VALUES("status", "0");
				VALUES("create_by", "#{createBy}");
				VALUES("create_date", "#{createDate}");
				VALUES("update_by", "#{updateBy}");
				VALUES("update_date", "#{updateDate}");
				VALUES("remarks", "#{remarks}");
				VALUES("order", "#{order}");
			}
		}.toString();
	}
	
	public String generateUpdateSql(JmWisdomQuestions obj){
		return new SQL(){
			{
				UPDATE("jm_wisdom_questions");
				
				SET("title = #{title}");
				SET("industry_id = #{industryId}");
				SET("user_id = #{userId}");
				SET("content = #{content}");
				SET("oss_urls = #{ossUrls}");
				SET("ayes = #{ayes}");
				SET("antis = #{antis}");
				SET("answers = #{answers}");
				SET("update_by = #{updateBy}");
				SET("update_date = #{updateDate}");
				SET("remarks = #{remarks}");
				SET("order = #{order}");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
	
	public String generateDeleteSql(String id){
		return new SQL(){
			{
				UPDATE("jm_wisdom_questions");
				
				SET("status = '1'");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
}