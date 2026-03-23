package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.HandcraftWork;
import com.ruoyi.system.service.IHandcraftWorkService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 作品档案Controller
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
@RestController
@RequestMapping("/system/work")
public class HandcraftWorkController extends BaseController {
    @Autowired
    private IHandcraftWorkService handcraftWorkService;

    /**
     * 查询作品档案列表
     */
    @PreAuthorize("@ss.hasPermi('system:work:list')")
    @GetMapping("/list")
    public TableDataInfo list(HandcraftWork handcraftWork) {
        startPage();
        List<HandcraftWork> list = handcraftWorkService.selectHandcraftWorkList(handcraftWork);
        return getDataTable(list);
    }

    /**
     * 导出作品档案列表
     */
    @PreAuthorize("@ss.hasPermi('system:work:export')")
    @Log(title = "作品档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HandcraftWork handcraftWork) {
        List<HandcraftWork> list = handcraftWorkService.selectHandcraftWorkList(handcraftWork);
        ExcelUtil<HandcraftWork> util = new ExcelUtil<HandcraftWork>(HandcraftWork.class);
        util.exportExcel(response, list, "作品档案数据");
    }

    /**
     * 获取作品档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:work:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(handcraftWorkService.selectHandcraftWorkById(id));
    }

    /**
     * 新增作品档案
     */
    @PreAuthorize("@ss.hasPermi('system:work:add')")
    @Log(title = "作品档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HandcraftWork handcraftWork) {
        return toAjax(handcraftWorkService.insertHandcraftWork(handcraftWork));
    }

    /**
     * 修改作品档案
     */
    @PreAuthorize("@ss.hasPermi('system:work:edit')")
    @Log(title = "作品档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HandcraftWork handcraftWork) {
        return toAjax(handcraftWorkService.updateHandcraftWork(handcraftWork));
    }

    /**
     * 删除作品档案
     */
    @PreAuthorize("@ss.hasPermi('system:work:remove')")
    @Log(title = "作品档案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(handcraftWorkService.deleteHandcraftWorkByIds(ids));
    }

    /**
     * 上传作品图片（关联 work 表 id，仅允许图片格式）
     */
    @PreAuthorize("@ss.hasPermi('system:work:edit')")
    @Log(title = "作品档案-上传图片", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadImage")
    public AjaxResult uploadImage(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) {
        try {
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", handcraftWorkService.uploadWorkImage(id, file));
            return ajax;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 上传作品视频（关联 work 表 id，仅允许视频格式）
     */
    @PreAuthorize("@ss.hasPermi('system:work:edit')")
    @Log(title = "作品档案-上传视频", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadVideo")
    public AjaxResult uploadVideo(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) {
        try {
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", handcraftWorkService.uploadWorkVideo(id, file));
            return ajax;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
}
