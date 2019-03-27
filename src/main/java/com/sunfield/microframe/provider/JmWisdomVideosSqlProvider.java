package com.sunfield.microframe.provider;

import com.sunfield.microframe.common.utils.SqlUtils;
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

				if(obj.getInterviewIdList() != null && obj.getInterviewIdList().size() > 0) {
					String inSql = SqlUtils.inSql("interview_id", SqlUtils.ColumnType.VARCHAR,
							obj.getInterviewIdList().toArray(new String[obj.getInterviewIdList().size()]));
					WHERE(inSql);
				}

				if(obj.getDateStart() != null) {
					WHERE("update_date >= #{dateStart}");
				}

				if(obj.getDateEnd() != null) {
					WHERE("update_date <= #{dateEnd}");
				}

				if(StringUtils.isNotBlank(obj.getTitle())) {
					WHERE("title like CONCAT('%',#{title},'%')");
				}

				//查询某访谈关联视频列表，时间倒序，必须传递关联访谈ID，非空判断只是为了代码安全
				if(StringUtils.isNotBlank(obj.getInterviewId())){
					WHERE("interview_id = #{interviewId}");
				}
				ORDER_BY("update_date desc");
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
				//后台管理功能，数据库字段非空，必须传递的参数，值必须为1或2
				VALUES("allow_comments", "#{allowComments}");
				VALUES("ayes", "0");
				//后台管理功能，数据库字段非空，必须传递的参数，值必须为1或2
				VALUES("leaguer_only", "#{leaguerOnly}");
				//后台管理功能，会员专属时才有值，可为空，空代表不限制，数值代表免费时长
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

				//前台功能，视频允许评论点赞情形下（前台用户可看到点赞按钮并点赞，说明一定是配置了可评论点赞，记录赞数是一定没错的），赞数+1，独立操作，用户点赞该视频时更新
				if(obj.getAyes() != null && obj.getAyes() == 1) {//缺少非空判断时，不传该字段会报错
					SET("ayes = ayes + 1");
				}else {//后台功能，不能更改视频的访谈id,视频仍对应原访谈，只更改其他信息
					SET("title = #{title}");
					SET("cover_url = #{coverUrl}");
					SET("video_url = #{videoUrl}");
					//后台管理功能，数据库字段非空，必须传递的参数，值必须为1或2
					SET("allow_comments = #{allowComments}");

					//后台管理功能，数据库字段非空，必须传递的参数，值必须为1或2
					SET("leaguer_only = #{leaguerOnly}");
					//后台管理功能，会员专属时才有值，可为空，空代表不限制，数值代表免费时长
					SET("free_duration = #{freeDuration}");
				}

				SET("update_by = #{updateBy}");
				SET("update_date = #{updateDate}");
				SET("remarks = #{remarks}");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}

	//按业务条件拼接不同的sql--专门用于用户取消赞/踩/收藏时对应字段数值减1
	public String generateUpdateCancelSql(JmWisdomVideos obj){
		return new SQL(){
			{
				UPDATE("jm_wisdom_videos");

				if(obj.getAyes() != null && obj.getAyes() == 1) {//缺少非空判断时，不传该字段会报错
					SET("ayes = ayes - 1");
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
				UPDATE("jm_wisdom_videos");
				
				SET("status = '1'");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
}