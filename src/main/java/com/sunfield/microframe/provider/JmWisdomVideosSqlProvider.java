package com.sunfield.microframe.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.sunfield.microframe.domain.JmWisdomVideos;

/**
 * jm_wisdom_videos sql provider
 * @author sunfield coder
 */
public class JmWisdomVideosSqlProvider{
 
 	private static String COLUMNS = 
 									" id AS id,"+
 									" title AS title,"+
 									" interview_id AS interviewId,"+
 									" cover_url AS coverUrl,"+
 									" video_url AS videoUrl,"+
 									" allow_comments AS allowComments,"+
 									" leaguer_only AS leaguerOnly,"+
 									" free_duration AS freeDuration,"+
 									" status AS status,"+
 									" create_by AS createBy,"+
 									" create_date AS createDate,"+
 									" update_by AS updateBy,"+
 									" update_date AS updateDate,"+
 									" remarks AS remarks";
 
 	public String generateFindListSql(JmWisdomVideos obj){
		return new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_wisdom_videos");
				
				WHERE("status = '0'");
				
				
				
			}
		}.toString();
	}
	
	public String generateFindPageSql(JmWisdomVideos obj){
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
				FROM("jm_wisdom_videos");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
	
	public String generateInsertSql(JmWisdomVideos obj){
		return new SQL(){
			{
				INSERT_INTO("jm_wisdom_videos");
				
				VALUES("id", "#{id}");
				VALUES("title", "#{title}");
				VALUES("interview_id", "#{interviewId}");
				VALUES("cover_url", "#{coverUrl}");
				VALUES("video_url", "#{videoUrl}");
				VALUES("allow_comments", "#{allowComments}");
				VALUES("leaguer_only", "#{leaguerOnly}");
				VALUES("free_duration", "#{freeDuration}");
				VALUES("status", "0");
				VALUES("create_by", "#{createBy}");
				VALUES("create_date", "#{createDate}");
				VALUES("update_by", "#{updateBy}");
				VALUES("update_date", "#{updateDate}");
				VALUES("remarks", "#{remarks}");
			}
		}.toString();
	}
	
	public String generateUpdateSql(JmWisdomVideos obj){
		return new SQL(){
			{
				UPDATE("jm_wisdom_videos");
				
				SET("title = #{title}");
				SET("interview_id = #{interviewId}");
				SET("cover_url = #{coverUrl}");
				SET("video_url = #{videoUrl}");
				SET("allow_comments = #{allowComments}");
				SET("leaguer_only = #{leaguerOnly}");
				SET("free_duration = #{freeDuration}");
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
				UPDATE("jm_wisdom_videos");
				
				SET("status = '1'");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
}