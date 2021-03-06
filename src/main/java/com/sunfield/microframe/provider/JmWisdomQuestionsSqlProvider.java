package com.sunfield.microframe.provider;

import com.sunfield.microframe.common.utils.SqlUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.sunfield.microframe.domain.JmWisdomQuestions;

import java.util.Date;

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
									" media_type AS mediaType,"+
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
 									" select_order AS selectOrder";
 
 	public String generateFindListSql(JmWisdomQuestions obj){
		return new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_wisdom_questions");
				
				WHERE("status = '0'");

				if(obj.getUserIdList() != null && obj.getUserIdList().size() > 0) {
					String inSql = SqlUtils.inSql("user_id", SqlUtils.ColumnType.VARCHAR,
							obj.getUserIdList().toArray(new String[obj.getUserIdList().size()]));
					WHERE(inSql);
				}

				if(obj.getDateStart() != null) {
					WHERE("update_date >= #{dateStart}");
				}

				if(obj.getDateEnd() != null) {
					WHERE("update_date <= #{dateEnd}");
				}

				//按行业分类列表，日期（按天）/赞数综合排序，可集成分页，独立操作
				//修改：以精确时间为第三序，让同一天中相同赞数的按时间倒序排列
				if(StringUtils.isNotBlank(obj.getIndustryId())){
					WHERE("industry_id = #{industryId}");
				}
				//按精品排序列表，独立操作
				if(obj.getSelectOrder() != null && obj.getSelectOrder() == 1){
					WHERE("select_order is not null");
					ORDER_BY("select_order");
				}else if(obj.getSelectOrder() != null && obj.getSelectOrder() == 2){//非精品的，后台
					WHERE("select_order is null");
				}
				ORDER_BY("date(update_date) desc,ayes desc,update_date desc");//使用函数会拖累性能，后期根据需要优化
			}
		}.toString();
	}

	public String generateFindByUserIdsSql(String[] userIds, Date dateStart, Date dateEnd,
										   String industryId,Integer selectOrder){
		return new SQL(){
			{
				SELECT(COLUMNS);
				FROM("jm_wisdom_questions");

				WHERE("status = '0'");

				if(userIds != null && userIds.length > 0) {
					String inSql = SqlUtils.inSql("user_id", SqlUtils.ColumnType.VARCHAR,userIds);
					WHERE(inSql);
				}

				if(dateStart != null) {
					WHERE("update_date >= #{dateStart}");
				}

				if(dateEnd != null) {
					WHERE("update_date <= #{dateEnd}");
				}

				if(StringUtils.isNotBlank(industryId)){
					WHERE("industry_id = #{industryId}");
				}
				//按精品排序列表，独立操作
				if(selectOrder != null && selectOrder == 1){
					WHERE("select_order is not null");
					ORDER_BY("select_order");
				}else if(selectOrder != null && selectOrder == 2){//非精品的，后台
					WHERE("select_order is null");
				}
				ORDER_BY("date(update_date) desc,ayes desc,update_date desc");//使用函数会拖累性能，后期根据需要优化
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

	public String generateFindByUserIdsPageSql(String[] userIds, Date dateStart, Date dateEnd,
											   String industryId,Integer selectOrder,Integer pageNumber, Integer pageSize){
		StringBuilder sql = new StringBuilder(generateFindByUserIdsSql(userIds,dateStart, dateEnd,industryId,selectOrder));
		sql.append(" LIMIT ");
		sql.append((pageNumber - 1) * pageSize);
		sql.append(", ");
		sql.append(pageSize);
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
				VALUES("media_type", "#{mediaType}");
				VALUES("oss_urls", "#{ossUrls}");
				VALUES("ayes", "0");
				VALUES("antis", "0");
				VALUES("answers", "0");
				VALUES("status", "0");
				VALUES("create_by", "#{createBy}");
				VALUES("create_date", "#{createDate}");
				VALUES("update_by", "#{updateBy}");
				VALUES("update_date", "#{updateDate}");
				VALUES("remarks", "#{remarks}");
				//后台，添加精品排序
				VALUES("select_order", "#{selectOrder}");
			}
		}.toString();
	}
	//按业务条件拼接不同的sql
	public String generateUpdateSql(JmWisdomQuestions obj){
		return new SQL(){
			{
				UPDATE("jm_wisdom_questions");
				//赞+1，独立操作，赞时更新
				if(obj.getAyesTag() != null && obj.getAyesTag() == 1) {//缺少非空判断时，不传该字段会报错
					SET("ayes = ayes + 1");
				}
				//踩+1，独立操作，踩时更新
				if(obj.getAntisTag() != null && obj.getAntisTag() == 1) {
					SET("antis = antis + 1");
				}
				//回答数+1，独立操作，添加回答时更新--修复bug:避免回答数为1的问题也被加上一个回答数
				if(obj.getAnswerTag() != null && obj.getAnswerTag() == 1) {
					SET("answers = answers + 1");
				}

				SET("update_by = #{updateBy}");
				SET("update_date = #{updateDate}");
				SET("remarks = #{remarks}");

				//修复BUG:后台精品排序设置被前台新增回答增加回答数覆盖排序号（变为空）的情况
				if(obj.getSelectOrder() != null) {
					//设置精品排序，独立操作，后台管理功能
					SET("select_order = #{selectOrder}");
				}

				WHERE("id = #{id}");
			}
		}.toString();
	}

	//按业务条件拼接不同的sql--专门用于用户取消赞/踩/收藏时对应字段数值减1
	public String generateUpdateCancelSql(JmWisdomQuestions obj){
		return new SQL(){
			{
				UPDATE("jm_wisdom_questions");
				//赞-1
				if(obj.getAyesTag() != null && obj.getAyesTag() == 1) {//缺少非空判断时，不传该字段会报错
					SET("ayes = ayes - 1");
				}
				//踩-1
				if(obj.getAntisTag() != null && obj.getAntisTag() == 1) {
					SET("antis = antis - 1");
				}
				//回答数-1，用于后台删除回答
				if(obj.getAnswerTag() != null && obj.getAnswerTag() == 1) {
					SET("answers = answers - 1");
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
				UPDATE("jm_wisdom_questions");
				
				SET("status = '1'");
				
				WHERE("id = #{id}");
			}
		}.toString();
	}
}