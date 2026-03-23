package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.HandcraftTech;

/**
 * 技艺档案Service接口
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
public interface IHandcraftTechService 
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
     * 批量删除技艺档案
     * 
     * @param ids 需要删除的技艺档案主键集合
     * @return 结果
     */
    public int deleteHandcraftTechByIds(Long[] ids);

    /**
     * 删除技艺档案信息
     * 
     * @param id 技艺档案主键
     * @return 结果
     */
    public int deleteHandcraftTechById(Long id);
}
