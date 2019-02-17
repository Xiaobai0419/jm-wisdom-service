package com.sunfield.microframe.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.sunfield.microframe.domain.JmWisdomUserQuestions;

/**
 * jm_wisdom_user_questions sql provider
 * @author sunfield coder
 */
public class JmWisdomUserQuestionsSqlProvider{
 
 	private static String COLUMNS = 
 									" id AS id,"+
 									" type AS type,"+
 									" user_id AS userId,"+
 									" question_id AS questionId,"+
 									" yesorno AS yesorno,"+
 									" status AS status,"+
 									" create_by AS createBy,"+
 									" create_date AS createDate,"+
 									" update_by AS updateBy,"+
 									" update_date AS updateDate,"+
 									" remarks AS remarks";
 
 	public String generateFindListSql(JmWisdomUserQuestions obj){
		return new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_wisdom_user_questions");
				
				WHERE("status = '0'");
				
				
				
			}
		}.toString();
	}
	
	public String generateFindPageSql(JmWisdomUserQuestions obj){
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
				FROM("jm_wisdom_user_questions");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}

	//用户获取自身对各类目标对象的赞、踩情况
	public String generateFindSelfSql(JmWisdomUserQuestions obj){
		String sql = new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_wisdom_user_questions");

				WHERE("type = #{type}");
				WHERE("user_id = #{userId}");
				WHERE("question_id = #{questionId}");
				//只查询有效赞、踩、收藏，去除逻辑删除的（取消过赞、踩、收藏的）
				WHERE("status = '0'");
				//获取插入时间倒序第一条，也就是最终踩赞情况，防止一个用户对一个对象多个有效踩赞插入的情况
				ORDER_BY("create_date desc");
			}
		}.toString();
		sql += " LIMIT 0,1 ";
		return sql;
	}

	//用户踩/赞/收藏时插入中间表记录该用户情况，无踩/赞/收藏则不记录，由前台控制一个用户不可踩/赞/收藏多次，只能点击和取消交替进行
	public String generateInsertSql(JmWisdomUserQuestions obj){
		return new SQL(){
			{
				INSERT_INTO("jm_wisdom_user_questions");
				
				VALUES("id", "#{id}");
				VALUES("type", "#{type}");
				VALUES("user_id", "#{userId}");
				VALUES("question_id", "#{questionId}");
				VALUES("yesorno", "#{yesorno}");
				VALUES("status", "0");
				VALUES("create_by", "#{createBy}");
				VALUES("create_date", "#{createDate}");
				VALUES("update_by", "#{updateBy}");
				VALUES("update_date", "#{updateDate}");
				VALUES("remarks", "#{remarks}");
			}
		}.toString();
	}
	
	public String generateUpdateSql(JmWisdomUserQuestions obj){
		return new SQL(){
			{
				UPDATE("jm_wisdom_user_questions");
				
				SET("type = #{type}");
				SET("user_id = #{userId}");
				SET("question_id = #{questionId}");
				SET("yesorno = #{yesorno}");
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
				UPDATE("jm_wisdom_user_questions");
				
				SET("status = '1'");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}

	//用户取消踩/赞/收藏时，进行逻辑删除
	public String generateDeleteSelfSql(JmWisdomUserQuestions obj){
		return new SQL(){
			{
				UPDATE("jm_wisdom_user_questions");

				SET("status = '1'");

				WHERE("type = #{type}");
				WHERE("user_id = #{userId}");
				WHERE("question_id = #{questionId}");
				WHERE("yesorno = #{yesorno}");
			}
		}.toString();
	}
}