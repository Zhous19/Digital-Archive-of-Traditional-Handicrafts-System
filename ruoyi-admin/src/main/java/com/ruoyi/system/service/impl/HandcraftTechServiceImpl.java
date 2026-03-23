package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.HandcraftTechMapper;
import com.ruoyi.system.domain.HandcraftTech;
import com.ruoyi.system.service.IHandcraftTechService;

/**
 * 技艺档案Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
@Service
public class HandcraftTechServiceImpl implements IHandcraftTechService 
{
    @Autowired
    private HandcraftTechMapper handcraftTechMapper;

    /**
     * 查询技艺档案
     * 
     * @param id 技艺档案主键
     * @return 技艺档案
     */
    @Override
    public HandcraftTech selectHandcraftTechById(Long id)
    {
        return handcraftTechMapper.selectHandcraftTechById(id);
    }

    /**
     * 查询技艺档案列表
     * 
     * @param handcraftTech 技艺档案
     * @return 技艺档案
     */
    @Override
    public List<HandcraftTech> selectHandcraftTechList(HandcraftTech handcraftTech)
    {
        return handcraftTechMapper.selectHandcraftTechList(handcraftTech);
    }

    /**
     * 新增技艺档案
     * 
     * @param handcraftTech 技艺档案
     * @return 结果
     */
    @Override
    public int insertHandcraftTech(HandcraftTech handcraftTech)
    {
        handcraftTech.setCreateTime(DateUtils.getNowDate());
        return handcraftTechMapper.insertHandcraftTech(handcraftTech);
    }

    /**
     * 修改技艺档案
     * 
     * @param handcraftTech 技艺档案
     * @return 结果
     */
    @Override
    public int updateHandcraftTech(HandcraftTech handcraftTech)
    {
        handcraftTech.setUpdateTime(DateUtils.getNowDate());
        return handcraftTechMapper.updateHandcraftTech(handcraftTech);
    }

    /**
     * 批量删除技艺档案
     * 
     * @param ids 需要删除的技艺档案主键
     * @return 结果
     */
    @Override
    public int deleteHandcraftTechByIds(Long[] ids)
    {
        return handcraftTechMapper.deleteHandcraftTechByIds(ids);
    }

    /**
     * 删除技艺档案信息
     * 
     * @param id 技艺档案主键
     * @return 结果
     */
    @Override
    public int deleteHandcraftTechById(Long id)
    {
        return handcraftTechMapper.deleteHandcraftTechById(id);
    }
}
