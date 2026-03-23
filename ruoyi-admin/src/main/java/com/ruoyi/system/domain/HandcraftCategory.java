package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 手工艺品类对象 handcraft_category
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
public class HandcraftCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 品类名称 */
    @Excel(name = "品类名称")
    private String categoryName;

    /** 品类描述 */
    @Excel(name = "品类描述")
    private String categoryDesc;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }

    public void setCategoryDesc(String categoryDesc) 
    {
        this.categoryDesc = categoryDesc;
    }

    public String getCategoryDesc() 
    {
        return categoryDesc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryName", getCategoryName())
            .append("categoryDesc", getCategoryDesc())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
