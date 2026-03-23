package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 作品档案对象 handcraft_work
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
public class HandcraftWork extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 作品名称 */
    @Excel(name = "作品名称")
    private String workName;

    /** 所属品类ID（关联品类表） */
    @Excel(name = "所属品类ID", readConverterExp = "关=联品类表")
    private Long categoryId;

    /** 匠人ID（关联匠人表） */
    @Excel(name = "匠人ID", readConverterExp = "关=联匠人表")
    private Long artistId;

    /** 作品图片URL（多个用逗号分隔） */
    @Excel(name = "作品图片URL", readConverterExp = "多=个用逗号分隔")
    private String workPic;

    /** 作品视频URL */
    @Excel(name = "作品视频URL")
    private String workVideo;

    /** 创作背景 */
    @Excel(name = "创作背景")
    private String createBg;

    /** 文化内涵 */
    @Excel(name = "文化内涵")
    private String culturalConnotation;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setWorkName(String workName) 
    {
        this.workName = workName;
    }

    public String getWorkName() 
    {
        return workName;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setArtistId(Long artistId) 
    {
        this.artistId = artistId;
    }

    public Long getArtistId() 
    {
        return artistId;
    }

    public void setWorkPic(String workPic) 
    {
        this.workPic = workPic;
    }

    public String getWorkPic() 
    {
        return workPic;
    }

    public void setWorkVideo(String workVideo) 
    {
        this.workVideo = workVideo;
    }

    public String getWorkVideo() 
    {
        return workVideo;
    }

    public void setCreateBg(String createBg) 
    {
        this.createBg = createBg;
    }

    public String getCreateBg() 
    {
        return createBg;
    }

    public void setCulturalConnotation(String culturalConnotation) 
    {
        this.culturalConnotation = culturalConnotation;
    }

    public String getCulturalConnotation() 
    {
        return culturalConnotation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("workName", getWorkName())
            .append("categoryId", getCategoryId())
            .append("artistId", getArtistId())
            .append("workPic", getWorkPic())
            .append("workVideo", getWorkVideo())
            .append("createBg", getCreateBg())
            .append("culturalConnotation", getCulturalConnotation())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
