package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.HandcraftTech;

/**
 * 技艺档案Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
public interface HandcraftTechMapper 
{
    /**
     * 查询技艺档案
     * 
     * @param id 技艺档案主键
     * @return 技艺档案
     */
    public HandcraftTech selectHandcraftTechById(Long id);

    /**
     * 查询技艺档案列表
     * 
     * @param handcraftTech 技艺档案
     * @return 技艺档案集合
     */
    public List<HandcraftTech> selectHandcraftTechList(HandcraftTech handcraftTech);

    /**
     * 新增技艺档案
     * 
     * @param handcraftTech 技艺档案
     * @return 结果
     */
    public int insertHandcraftTech(HandcraftTech handcraftTech);

    /**
     * 修改技艺档案
     * 
     * @param handcraftTech 技艺档案
     * @return 结果
     */
    public int updateHandcraftTech(HandcraftTech handcraftTech);

    /**
     * 删除技艺档案
     * 
     * @param id 技艺档案主键
     * @return 结果
     */
    public int deleteHandcraftTechById(Long id);

    /**
     * 批量删除技艺档案
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHandcraftTechByIds(Long[] ids);
}
