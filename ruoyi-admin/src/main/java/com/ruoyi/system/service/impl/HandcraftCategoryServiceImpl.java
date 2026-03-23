package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.HandcraftCategoryMapper;
import com.ruoyi.system.domain.HandcraftCategory;
import com.ruoyi.system.service.IHandcraftCategoryService;

/**
 * 手工艺品类Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
@Service
public class HandcraftCategoryServiceImpl implements IHandcraftCategoryService 
{
    @Autowired
    private HandcraftCategoryMapper handcraftCategoryMapper;

    /**
     * 查询手工艺品类
     * 
     * @param id 手工艺品类主键
     * @return 手工艺品类
     */
    @Override
    public HandcraftCategory selectHandcraftCategoryById(Long id)
    {
        return handcraftCategoryMapper.selectHandcraftCategoryById(id);
    }

    /**
     * 查询手工艺品类列表
     * 
     * @param handcraftCategory 手工艺品类
     * @return 手工艺品类
     */
    @Override
    public List<HandcraftCategory> selectHandcraftCategoryList(HandcraftCategory handcraftCategory)
    {
        return handcraftCategoryMapper.selectHandcraftCategoryList(handcraftCategory);
    }

    /**
     * 新增手工艺品类
     * 
     * @param handcraftCategory 手工艺品类
     * @return 结果
     */
    @Override
    public int insertHandcraftCategory(HandcraftCategory handcraftCategory)
    {
        handcraftCategory.setCreateTime(DateUtils.getNowDate());
        return handcraftCategoryMapper.insertHandcraftCategory(handcraftCategory);
    }

    /**
     * 修改手工艺品类
     * 
     * @param handcraftCategory 手工艺品类
     * @return 结果
     */
    @Override
    public int updateHandcraftCategory(HandcraftCategory handcraftCategory)
    {
        handcraftCategory.setUpdateTime(DateUtils.getNowDate());
        return handcraftCategoryMapper.updateHandcraftCategory(handcraftCategory);
    }

    /**
     * 批量删除手工艺品类
     * 
     * @param ids 需要删除的手工艺品类主键
     * @return 结果
     */
    @Override
    public int deleteHandcraftCategoryByIds(Long[] ids)
    {
        return handcraftCategoryMapper.deleteHandcraftCategoryByIds(ids);
    }

    /**
     * 删除手工艺品类信息
     * 
     * @param id 手工艺品类主键
     * @return 结果
     */
    @Override
    public int deleteHandcraftCategoryById(Long id)
    {
        return handcraftCategoryMapper.deleteHandcraftCategoryById(id);
    }
}
