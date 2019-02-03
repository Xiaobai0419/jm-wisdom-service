package com.sunfield.microframe.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.sunfield.microframe.domain.JmWisdomAnswers;

/**
 * jm_wisdom_answers sql provider
 * @author sunfield coder
 */
public class JmWisdomAnswersSqlProvider{
 
 	private static String COLUMNS = 
 									" id AS id,"+
 									" title AS title,"+
 									" question_id AS questionId,"+
 									" user_id AS userId,"+
 									" content AS content,"+
 									" ayes AS ayes,"+
 									" antis AS antis,"+
 									" status AS status,"+
 									" create_by AS createBy,"+
 									" create_date AS createDate,"+
 									" update_by AS updateBy,"+
 									" update_date AS updateDate,"+
 									" remarks AS remarks";
 
 	public String generateFindListSql(JmWisdomAnswers obj){
		return new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_wisdom_answers");
				
				WHERE("status = '0'");
				
				
				
			}
		}.toString();
	}
	
	public String generateFindPageSql(JmWisdomAnswers obj){
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
				FROM("jm_wisdom_answers");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
	
	public String generateInsertSql(JmWisdomAnswers obj){
		return new SQL(){
			{
				INSERT_INTO("jm_wisdom_answers");
				
				VALUES("id", "#{id}");
				VALUES("title", "#{title}");
				VALUES("question_id", "#{questionId}");
				VALUES("user_id", "#{userId}");
				VALUES("content", "#{content}");
				VALUES("ayes", "#{ayes}");
				VALUES("antis", "#{antis}");
				VALUES("status", "0");
				VALUES("create_by", "#{createBy}");
				VALUES("create_date", "#{createDate}");
				VALUES("update_by", "#{updateBy}");
				VALUES("update_date", "#{updateDate}");
				VALUES("remarks", "#{remarks}");
			}
		}.toString();
	}
	
	public String generateUpdateSql(JmWisdomAnswers obj){
		return new SQL(){
			{
				UPDATE("jm_wisdom_answers");
				
				SET("title = #{title}");
				SET("question_id = #{questionId}");
				SET("user_id = #{userId}");
				SET("content = #{content}");
				SET("ayes = #{ayes}");
				SET("antis = #{antis}");
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
				UPDATE("jm_wisdom_answers");
				
				SET("status = '1'");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
}