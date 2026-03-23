package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 技艺档案对象 handcraft_tech
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
public class HandcraftTech extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 技艺名称 */
    @Excel(name = "技艺名称")
    private String techName;

    /** 所属品类ID（关联品类表） */
    @Excel(name = "所属品类ID", readConverterExp = "关=联品类表")
    private Long categoryId;

    /** 工艺流程 */
    @Excel(name = "工艺流程")
    private String processFlow;

    /** 工具材料 */
    @Excel(name = "工具材料")
    private String toolsMaterials;

    /** 技法要点 */
    @Excel(name = "技法要点")
    private String techPoints;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setTechName(String techName) 
    {
        this.techName = techName;
    }

    public String getTechName() 
    {
        return techName;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setProcessFlow(String processFlow) 
    {
        this.processFlow = processFlow;
    }

    public String getProcessFlow() 
    {
        return processFlow;
    }

    public void setToolsMaterials(String toolsMaterials) 
    {
        this.toolsMaterials = toolsMaterials;
    }

    public String getToolsMaterials() 
    {
        return toolsMaterials;
    }

    public void setTechPoints(String techPoints) 
    {
        this.techPoints = techPoints;
    }

    public String getTechPoints() 
    {
        return techPoints;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("techName", getTechName())
            .append("categoryId", getCategoryId())
            .append("processFlow", getProcessFlow())
            .append("toolsMaterials", getToolsMaterials())
            .append("techPoints", getTechPoints())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
