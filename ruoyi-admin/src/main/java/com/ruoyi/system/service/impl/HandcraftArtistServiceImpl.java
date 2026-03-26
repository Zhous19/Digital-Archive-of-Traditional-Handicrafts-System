package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Map;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.HandcraftArtistMapper;
import com.ruoyi.system.domain.HandcraftArtist;
import com.ruoyi.system.service.IHandcraftArtistService;

/**
 * 匠人档案Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
@Service
public class HandcraftArtistServiceImpl implements IHandcraftArtistService 
{
    @Autowired
    private HandcraftArtistMapper handcraftArtistMapper;

    /**
     * 查询匠人档案
     * 
     * @param id 匠人档案主键
     * @return 匠人档案
     */
    @Override
    public HandcraftArtist selectHandcraftArtistById(Long id)
    {
        return handcraftArtistMapper.selectHandcraftArtistById(id);
    }

    /**
     * 查询匠人档案列表
     * 
     * @param handcraftArtist 匠人档案
     * @return 匠人档案
     */
    @Override
    public List<HandcraftArtist> selectHandcraftArtistList(HandcraftArtist handcraftArtist)
    {
        return handcraftArtistMapper.selectHandcraftArtistList(handcraftArtist);
    }

    /**
     * 新增匠人档案
     * 
     * @param handcraftArtist 匠人档案
     * @return 结果
     */
    @Override
    public int insertHandcraftArtist(HandcraftArtist handcraftArtist)
    {
        handcraftArtist.setCreateTime(DateUtils.getNowDate());
        return handcraftArtistMapper.insertHandcraftArtist(handcraftArtist);
    }

    /**
     * 修改匠人档案
     * 
     * @param handcraftArtist 匠人档案
     * @return 结果
     */
    @Override
    public int updateHandcraftArtist(HandcraftArtist handcraftArtist)
    {
        handcraftArtist.setUpdateTime(DateUtils.getNowDate());
        return handcraftArtistMapper.updateHandcraftArtist(handcraftArtist);
    }

    /**
     * 批量删除匠人档案
     * 
     * @param ids 需要删除的匠人档案主键
     * @return 结果
     */
    @Override
    public int deleteHandcraftArtistByIds(Long[] ids)
    {
        return handcraftArtistMapper.deleteHandcraftArtistByIds(ids);
    }

    /**
     * 删除匠人档案信息
     * 
     * @param id 匠人档案主键
     * @return 结果
     */
    @Override
    public int deleteHandcraftArtistById(Long id)
    {
        return handcraftArtistMapper.deleteHandcraftArtistById(id);
    }

    @Override
    public List<Map<String, Object>> selectRegionCount()
    {
        return handcraftArtistMapper.selectRegionCount();
    }
}
