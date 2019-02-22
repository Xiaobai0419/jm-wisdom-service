package com.sunfield.microframe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.sunfield.microframe.domain.JmWisdomVideos;
import com.sunfield.microframe.provider.JmWisdomVideosSqlProvider;

/**
 * jm_wisdom_videos mapper
 * @author sunfield coder
 */
@Mapper
public interface JmWisdomVideosMapper{

	/**
	 * 列表查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomVideosSqlProvider.class, method="generateFindListSql")
	public List<JmWisdomVideos> findList(JmWisdomVideos obj);

	/**
	 * 分页查询
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=JmWisdomVideosSqlProvider.class, method="generateFindPageSql")
	public List<JmWisdomVideos> findPage(JmWisdomVideos obj);

	/**
	 * 单行查询
	 * @param id
	 * @return
	 */
	@SelectProvider(type=JmWisdomVideosSqlProvider.class, method="generateFindOneSql")
	public JmWisdomVideos findOne(String id);

	/**
	 * 插入单行
	 * @param obj
	 * @return
	 */
	@InsertProvider(type=JmWisdomVideosSqlProvider.class, method="generateInsertSql")
	public int insert(JmWisdomVideos obj);

	/**
	 * 更新单行
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmWisdomVideosSqlProvider.class, method="generateUpdateSql")
	public int update(JmWisdomVideos obj);

	/**
	 * 更新单行--专门用于用户取消赞/踩/收藏时对应字段数值减1
	 * @param obj
	 * @return
	 */
	@UpdateProvider(type=JmWisdomVideosSqlProvider.class, method="generateUpdateCancelSql")
	public int updateCancel(JmWisdomVideos obj);

	/**
	 * 删除单行（一般为逻辑删除）
	 * @param id
	 * @return
	 */
	@UpdateProvider(type=JmWisdomVideosSqlProvider.class, method="generateDeleteSql")
	public int delete(String id);

}
