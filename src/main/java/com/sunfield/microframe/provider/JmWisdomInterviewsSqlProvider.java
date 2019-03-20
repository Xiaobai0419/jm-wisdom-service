package com.sunfield.microframe.provider;

import com.sunfield.microframe.common.utils.SqlUtils;
import org.apache.ibatis.jdbc.SQL;

import com.sunfield.microframe.domain.JmWisdomInterviews;

import static com.sunfield.microframe.common.utils.SqlUtils.inSql;

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

				//按推荐排序列表，独立操作
				if(obj.getSelectOrder() != null && obj.getSelectOrder() == 1){
					WHERE("select_order is not null");
					ORDER_BY("select_order");
				}
				//按视频板块推荐排序列表，独立操作
				else if(obj.getVideoSelectOrder() != null && obj.getVideoSelectOrder() == 1){
					WHERE("video_select_order is not null");
					ORDER_BY("video_select_order");
				}
				//按时间倒序，独立操作
				else {
					ORDER_BY("update_date desc");
				}
				
			}
		}.toString();
	}

	public String generateFindListByIdsSql(String[] ids){
		return new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_wisdom_interviews");

				WHERE("status = '0'");

				if(ids != null && ids.length > 0) {
					String inSql = inSql("id", SqlUtils.ColumnType.VARCHAR,ids);
					WHERE(inSql);
				}
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
				//后台管理功能，支持插入时设置两种排序号，传空或不传代表不参与排序
				VALUES("select_order", "#{selectOrder}");
				VALUES("video_select_order", "#{videoSelectOrder}");
				VALUES("favorites", "0");
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
				
				//前台功能，收藏人数+1，独立操作，用户收藏该访谈时更新
				if(obj.getFavorites() != null && obj.getFavorites() == 1) {//缺少非空判断时，不传该字段会报错
					SET("favorites = favorites + 1");
				}else {
					//后台管理功能，支持编辑时设置两种排序号，传空或不传代表不参与排序
					SET("title = #{title}");
					SET("content = #{content}");
					SET("cover_url = #{coverUrl}");
					SET("select_order = #{selectOrder}");
					SET("video_select_order = #{videoSelectOrder}");
				}

				SET("update_by = #{updateBy}");
				SET("update_date = #{updateDate}");
				SET("remarks = #{remarks}");

				WHERE("id = #{id}");
			}
		}.toString();
	}

	//按业务条件拼接不同的sql--专门用于用户取消赞/踩/收藏时对应字段数值减1
	public String generateUpdateCancelSql(JmWisdomInterviews obj){
		return new SQL(){
			{
				UPDATE("jm_wisdom_interviews");

				if(obj.getFavorites() != null && obj.getFavorites() == 1) {//缺少非空判断时，不传该字段会报错
					SET("favorites = favorites - 1");
				}

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