package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.web.utils.minio.MinioFileStorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.system.mapper.HandcraftWorkMapper;
import com.ruoyi.system.domain.HandcraftWork;
import com.ruoyi.system.service.IHandcraftWorkService;

/**
 * 作品档案Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
@Service
public class HandcraftWorkServiceImpl implements IHandcraftWorkService {
    @Autowired
    private HandcraftWorkMapper handcraftWorkMapper;

    @Autowired(required = false)
    private MinioFileStorageUtil minioFileStorageUtil;

    private static final String MINIO_BUCKET = "handcraft";

    /**
     * 查询作品档案
     * 
     * @param id 作品档案主键
     * @return 作品档案
     */
    @Override
    public HandcraftWork selectHandcraftWorkById(Long id) {
        return handcraftWorkMapper.selectHandcraftWorkById(id);
    }

    /**
     * 查询作品档案列表
     * 
     * @param handcraftWork 作品档案
     * @return 作品档案
     */
    @Override
    public List<HandcraftWork> selectHandcraftWorkList(HandcraftWork handcraftWork) {
        return handcraftWorkMapper.selectHandcraftWorkList(handcraftWork);
    }

    /**
     * 新增作品档案
     * 
     * @param handcraftWork 作品档案
     * @return 结果
     */
    @Override
    public int insertHandcraftWork(HandcraftWork handcraftWork) {
        handcraftWork.setCreateTime(DateUtils.getNowDate());
        return handcraftWorkMapper.insertHandcraftWork(handcraftWork);
    }

    /**
     * 修改作品档案
     * 
     * @param handcraftWork 作品档案
     * @return 结果
     */
    @Override
    public int updateHandcraftWork(HandcraftWork handcraftWork) {
        handcraftWork.setUpdateTime(DateUtils.getNowDate());
        return handcraftWorkMapper.updateHandcraftWork(handcraftWork);
    }

    /**
     * 批量删除作品档案
     * 
     * @param ids 需要删除的作品档案主键
     * @return 结果
     */
    @Override
    public int deleteHandcraftWorkByIds(Long[] ids) {
        return handcraftWorkMapper.deleteHandcraftWorkByIds(ids);
    }

    /**
     * 删除作品档案信息
     * 
     * @param id 作品档案主键
     * @return 结果
     */
    @Override
    public int deleteHandcraftWorkById(Long id) {
        return handcraftWorkMapper.deleteHandcraftWorkById(id);
    }

    @Override
    public String uploadWorkImage(Long id, MultipartFile file) {
        if (minioFileStorageUtil == null) {
            throw new RuntimeException("MinIO 未配置，无法上传文件");
        }
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }
        HandcraftWork work = selectHandcraftWorkById(id);
        if (work == null) {
            throw new RuntimeException("作品不存在");
        }
        try {
            FileUploadUtils.assertAllowed(file, MimeTypeUtils.IMAGE_EXTENSION);
            String oldUrl = work.getWorkPic();
            String ext = getFileExtension(file.getOriginalFilename());
            String objectName = buildObjectName(id, "image", ext);
            String url = minioFileStorageUtil.uploadObjectWithoutDate(MINIO_BUCKET, objectName, objectName,
                    file.getInputStream(), file.getContentType());
            // 仅保留一个地址（覆盖）
            work.setWorkPic(url);
            updateHandcraftWork(work);
            tryDeleteOldMinioObject(oldUrl, url);
            return url;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String uploadWorkVideo(Long id, MultipartFile file) {
        if (minioFileStorageUtil == null) {
            throw new RuntimeException("MinIO 未配置，无法上传文件");
        }
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }
        HandcraftWork work = selectHandcraftWorkById(id);
        if (work == null) {
            throw new RuntimeException("作品不存在");
        }
        try {
            FileUploadUtils.assertAllowed(file, MimeTypeUtils.VIDEO_EXTENSION);
            String oldUrl = work.getWorkVideo();
            String ext = getFileExtension(file.getOriginalFilename());
            String objectName = buildObjectName(id, "video", ext);
            String url = minioFileStorageUtil.uploadObjectWithoutDate(MINIO_BUCKET, objectName, objectName,
                    file.getInputStream(), file.getContentType());
            work.setWorkVideo(url);
            updateHandcraftWork(work);
            tryDeleteOldMinioObject(oldUrl, url);
            return url;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void tryDeleteOldMinioObject(String oldUrl, String newUrl) {
        if (minioFileStorageUtil == null) {
            return;
        }
        if (StringUtils.isEmpty(oldUrl) || oldUrl.equals(newUrl)) {
            return;
        }
        try {
            minioFileStorageUtil.deleteObjectByUrl(oldUrl);
        } catch (Exception ignore) {
            // 删除旧文件失败不影响本次上传与更新（避免主流程回滚）
        }
    }

    private String buildObjectName(Long id, String type, String ext) {
        String safeExt = StringUtils.isNotEmpty(ext) ? ext : "bin";
        String filename = type + "-" + IdUtils.fastSimpleUUID() + "." + safeExt;
        // 需求：Handcraft/work/id/image 或 video
        return "work/" + id + "/" + type + "/" + filename;
    }

    private String getFileExtension(String originalFilename) {
        if (StringUtils.isEmpty(originalFilename)) {
            return "";
        }
        int idx = originalFilename.lastIndexOf('.');
        if (idx < 0 || idx == originalFilename.length() - 1) {
            return "";
        }
        return originalFilename.substring(idx + 1).toLowerCase();
    }
}
