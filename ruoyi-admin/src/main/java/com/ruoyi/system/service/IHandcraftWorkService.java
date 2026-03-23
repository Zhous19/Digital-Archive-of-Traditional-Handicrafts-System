package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.HandcraftWork;
import org.springframework.web.multipart.MultipartFile;

/**
 * 作品档案Service接口
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
public interface IHandcraftWorkService {
    /**
     * 查询作品档案
     * 
     * @param id 作品档案主键
     * @return 作品档案
     */
    public HandcraftWork selectHandcraftWorkById(Long id);

    /**
     * 查询作品档案列表
     * 
     * @param handcraftWork 作品档案
     * @return 作品档案集合
     */
    public List<HandcraftWork> selectHandcraftWorkList(HandcraftWork handcraftWork);

    /**
     * 新增作品档案
     * 
     * @param handcraftWork 作品档案
     * @return 结果
     */
    public int insertHandcraftWork(HandcraftWork handcraftWork);

    /**
     * 修改作品档案
     * 
     * @param handcraftWork 作品档案
     * @return 结果
     */
    public int updateHandcraftWork(HandcraftWork handcraftWork);

    /**
     * 批量删除作品档案
     * 
     * @param ids 需要删除的作品档案主键集合
     * @return 结果
     */
    public int deleteHandcraftWorkByIds(Long[] ids);

    /**
     * 删除作品档案信息
     * 
     * @param id 作品档案主键
     * @return 结果
     */
    public int deleteHandcraftWorkById(Long id);

    /**
     * 上传作品图片并写入 work_pic
     *
     * @param id   作品ID
     * @param file 图片文件
     * @return 图片访问URL
     */
    public String uploadWorkImage(Long id, MultipartFile file);

    /**
     * 上传作品视频并写入 work_video
     *
     * @param id   作品ID
     * @param file 视频文件
     * @return 视频访问URL
     */
    public String uploadWorkVideo(Long id, MultipartFile file);
}
