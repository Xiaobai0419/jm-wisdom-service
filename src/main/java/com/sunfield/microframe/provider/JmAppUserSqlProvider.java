package com.sunfield.microframe.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.sunfield.microframe.domain.JmAppUser;

/**
 * jm_app_user sql provider
 * @author sunfield coder
 */
public class JmAppUserSqlProvider{
 
 	private static String COLUMNS = 
 									" id AS id,"+
 									" mobile AS mobile,"+
 									" nick_name AS nickName,"+
 									" sign AS sign,"+
 									" company_name AS companyName,"+
 									" industry AS industry,"+
 									" post AS post,"+
 									" gender AS gender,"+
 									" head_pic_url AS headPicUrl,"+
 									" card_pic_url AS cardPicUrl,"+
 									" card_status AS cardStatus,"+
 									" wx_open_id AS wxOpenId,"+
 									" qq_open_id AS qqOpenId,"+
 									" sina_open_id AS sinaOpenId,"+
 									" expert_status AS expertStatus,"+
 									" status AS status,"+
 									" create_by AS createBy,"+
 									" create_date AS createDate,"+
 									" update_by AS updateBy,"+
 									" update_date AS updateDate,"+
 									" remarks AS remarks";
 
 	public String generateFindListSql(JmAppUser obj){
		return new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_app_user");
				
				WHERE("status = '0'");
				
				
				
			}
		}.toString();
	}
	
	public String generateFindPageSql(JmAppUser obj){
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
				FROM("jm_app_user");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
	
	public String generateInsertSql(JmAppUser obj){
		return new SQL(){
			{
				INSERT_INTO("jm_app_user");
				
				VALUES("id", "#{id}");
				VALUES("mobile", "#{mobile}");
				VALUES("nick_name", "#{nickName}");
				VALUES("sign", "#{sign}");
				VALUES("company_name", "#{companyName}");
				VALUES("industry", "#{industry}");
				VALUES("post", "#{post}");
				VALUES("gender", "#{gender}");
				VALUES("head_pic_url", "#{headPicUrl}");
				VALUES("card_pic_url", "#{cardPicUrl}");
				VALUES("card_status", "#{cardStatus}");
				VALUES("wx_open_id", "#{wxOpenId}");
				VALUES("qq_open_id", "#{qqOpenId}");
				VALUES("sina_open_id", "#{sinaOpenId}");
				VALUES("expert_status", "#{expertStatus}");
				VALUES("status", "0");
				VALUES("create_by", "#{createBy}");
				VALUES("create_date", "#{createDate}");
				VALUES("update_by", "#{updateBy}");
				VALUES("update_date", "#{updateDate}");
				VALUES("remarks", "#{remarks}");
			}
		}.toString();
	}
	
	public String generateUpdateSql(JmAppUser obj){
		return new SQL(){
			{
				UPDATE("jm_app_user");
				
				SET("mobile = #{mobile}");
				SET("nick_name = #{nickName}");
				SET("sign = #{sign}");
				SET("company_name = #{companyName}");
				SET("industry = #{industry}");
				SET("post = #{post}");
				SET("gender = #{gender}");
				SET("head_pic_url = #{headPicUrl}");
				SET("card_pic_url = #{cardPicUrl}");
				SET("card_status = #{cardStatus}");
				SET("wx_open_id = #{wxOpenId}");
				SET("qq_open_id = #{qqOpenId}");
				SET("sina_open_id = #{sinaOpenId}");
				SET("expert_status = #{expertStatus}");
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
				UPDATE("jm_app_user");
				
				SET("status = '1'");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
}