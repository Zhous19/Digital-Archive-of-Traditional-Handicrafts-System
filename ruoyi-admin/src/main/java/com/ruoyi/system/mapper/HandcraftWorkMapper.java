package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.HandcraftWork;

/**
 * 作品档案Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
public interface HandcraftWorkMapper 
{
    /**
     * 查询作品档案
     * 
     * @param id 作品档案主键
     * @return 作品档案
     */
    public HandcraftWork selectHandcraftWorkById(Long id);

    /**
     * 查询作品档案列表
     * 
     * @param handcraftWork 作品档案
     * @return 作品档案集合
     */
    public List<HandcraftWork> selectHandcraftWorkList(HandcraftWork handcraftWork);

    /**
     * 新增作品档案
     * 
     * @param handcraftWork 作品档案
     * @return 结果
     */
    public int insertHandcraftWork(HandcraftWork handcraftWork);

    /**
     * 修改作品档案
     * 
     * @param handcraftWork 作品档案
     * @return 结果
     */
    public int updateHandcraftWork(HandcraftWork handcraftWork);

    /**
     * 删除作品档案
     * 
     * @param id 作品档案主键
     * @return 结果
     */
    public int deleteHandcraftWorkById(Long id);

    /**
     * 批量删除作品档案
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHandcraftWorkByIds(Long[] ids);

    /**
     * 查询作品图片列表（用于首页轮播）
     * 
     * @return 作品图片列表
     */
    public List<Map<String, Object>> selectWorkImages();
}
