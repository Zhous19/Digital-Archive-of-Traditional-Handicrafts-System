package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.HandcraftCategory;

/**
 * 手工艺品类Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
public interface HandcraftCategoryMapper 
{
    /**
     * 查询手工艺品类
     * 
     * @param id 手工艺品类主键
     * @return 手工艺品类
     */
    public HandcraftCategory selectHandcraftCategoryById(Long id);

    /**
     * 查询手工艺品类列表
     * 
     * @param handcraftCategory 手工艺品类
     * @return 手工艺品类集合
     */
    public List<HandcraftCategory> selectHandcraftCategoryList(HandcraftCategory handcraftCategory);

    /**
     * 新增手工艺品类
     * 
     * @param handcraftCategory 手工艺品类
     * @return 结果
     */
    public int insertHandcraftCategory(HandcraftCategory handcraftCategory);

    /**
     * 修改手工艺品类
     * 
     * @param handcraftCategory 手工艺品类
     * @return 结果
     */
    public int updateHandcraftCategory(HandcraftCategory handcraftCategory);

    /**
     * 删除手工艺品类
     * 
     * @param id 手工艺品类主键
     * @return 结果
     */
    public int deleteHandcraftCategoryById(Long id);

    /**
     * 批量删除手工艺品类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHandcraftCategoryByIds(Long[] ids);
}
