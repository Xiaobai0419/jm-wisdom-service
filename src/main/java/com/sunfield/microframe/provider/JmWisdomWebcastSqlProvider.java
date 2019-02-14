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

				//业务特殊需求：查询所有启用、禁用直播，禁用标识为2，1为删除标识
				WHERE("status != '1'");
				
				//仅用于后台直播管理，启用/禁用第一排序，直播开始时间倒序第二排序，创建时间倒序第三排序
				ORDER_BY("status,begin_time desc,create_date desc");
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
		//业务特殊需求：前台显示唯一启用的、正在播放的直播，按直播开始时间倒序取第一个，查询条件是启用状态的，开始、结束时间与当前时间比较，当前时间在两者之间，包括头尾，有符合条件的唯一一条返回结果，则显示，没有则不显示。新增直播时，与当前正播放直播时间冲突者必须设置为禁用
		String sql = new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_wisdom_webcast");

				WHERE("status = '0'");
				WHERE("begin_time <= now()");
				WHERE("end_time >= now()");
				ORDER_BY("begin_time desc");
			}
		}.toString();
		sql += " LIMIT 0,1 ";
		return sql;
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