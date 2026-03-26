package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.HandcraftArtist;

/**
 * 匠人档案Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
public interface HandcraftArtistMapper 
{
    /**
     * 查询匠人档案
     * 
     * @param id 匠人档案主键
     * @return 匠人档案
     */
    public HandcraftArtist selectHandcraftArtistById(Long id);

    /**
     * 查询匠人档案列表
     * 
     * @param handcraftArtist 匠人档案
     * @return 匠人档案集合
     */
    public List<HandcraftArtist> selectHandcraftArtistList(HandcraftArtist handcraftArtist);

    /**
     * 新增匠人档案
     * 
     * @param handcraftArtist 匠人档案
     * @return 结果
     */
    public int insertHandcraftArtist(HandcraftArtist handcraftArtist);

    /**
     * 修改匠人档案
     * 
     * @param handcraftArtist 匠人档案
     * @return 结果
     */
    public int updateHandcraftArtist(HandcraftArtist handcraftArtist);

    /**
     * 删除匠人档案
     * 
     * @param id 匠人档案主键
     * @return 结果
     */
    public int deleteHandcraftArtistById(Long id);

    /**
     * 批量删除匠人档案
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHandcraftArtistByIds(Long[] ids);

    /**
     * 查询地域统计数据
     * 
     * @return 地域统计数据
     */
    public List<Map<String, Object>> selectRegionCount();
}
