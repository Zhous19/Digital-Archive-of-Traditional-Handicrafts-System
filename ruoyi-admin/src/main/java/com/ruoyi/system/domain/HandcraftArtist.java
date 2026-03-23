package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 匠人档案对象 handcraft_artist
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
public class HandcraftArtist extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 匠人姓名 */
    @Excel(name = "匠人姓名")
    private String artistName;

    /** 性别（男/女） */
    @Excel(name = "性别", readConverterExp = "男=/女")
    private String gender;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;

    /** 所属地域 */
    @Excel(name = "所属地域")
    private String region;

    /** 技艺专长 */
    @Excel(name = "技艺专长")
    private String specialty;

    /** 生平履历 */
    @Excel(name = "生平履历")
    private String resume;

    /** 代表作品（关联作品表ID，用逗号分隔） */
    @Excel(name = "代表作品", readConverterExp = "关=联作品表ID，用逗号分隔")
    private String representWorks;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setArtistName(String artistName) 
    {
        this.artistName = artistName;
    }

    public String getArtistName() 
    {
        return artistName;
    }

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }

    public void setBirthDate(Date birthDate) 
    {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() 
    {
        return birthDate;
    }

    public void setRegion(String region) 
    {
        this.region = region;
    }

    public String getRegion() 
    {
        return region;
    }

    public void setSpecialty(String specialty) 
    {
        this.specialty = specialty;
    }

    public String getSpecialty() 
    {
        return specialty;
    }

    public void setResume(String resume) 
    {
        this.resume = resume;
    }

    public String getResume() 
    {
        return resume;
    }

    public void setRepresentWorks(String representWorks) 
    {
        this.representWorks = representWorks;
    }

    public String getRepresentWorks() 
    {
        return representWorks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("artistName", getArtistName())
            .append("gender", getGender())
            .append("birthDate", getBirthDate())
            .append("region", getRegion())
            .append("specialty", getSpecialty())
            .append("resume", getResume())
            .append("representWorks", getRepresentWorks())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
