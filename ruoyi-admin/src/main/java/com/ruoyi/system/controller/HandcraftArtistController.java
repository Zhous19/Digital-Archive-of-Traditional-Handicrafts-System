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
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.HandcraftArtist;
import com.ruoyi.system.service.IHandcraftArtistService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 匠人档案Controller
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
@RestController
@RequestMapping("/system/artist")
public class HandcraftArtistController extends BaseController
{
    @Autowired
    private IHandcraftArtistService handcraftArtistService;

    /**
     * 查询匠人档案列表
     */
    @PreAuthorize("@ss.hasPermi('system:artist:list')")
    @GetMapping("/list")
    public TableDataInfo list(HandcraftArtist handcraftArtist)
    {
        startPage();
        List<HandcraftArtist> list = handcraftArtistService.selectHandcraftArtistList(handcraftArtist);
        return getDataTable(list);
    }

    /**
     * 导出匠人档案列表
     */
    @PreAuthorize("@ss.hasPermi('system:artist:export')")
    @Log(title = "匠人档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HandcraftArtist handcraftArtist)
    {
        List<HandcraftArtist> list = handcraftArtistService.selectHandcraftArtistList(handcraftArtist);
        ExcelUtil<HandcraftArtist> util = new ExcelUtil<HandcraftArtist>(HandcraftArtist.class);
        util.exportExcel(response, list, "匠人档案数据");
    }

    /**
     * 获取匠人档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:artist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(handcraftArtistService.selectHandcraftArtistById(id));
    }

    /**
     * 新增匠人档案
     */
    @PreAuthorize("@ss.hasPermi('system:artist:add')")
    @Log(title = "匠人档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HandcraftArtist handcraftArtist)
    {
        return toAjax(handcraftArtistService.insertHandcraftArtist(handcraftArtist));
    }

    /**
     * 修改匠人档案
     */
    @PreAuthorize("@ss.hasPermi('system:artist:edit')")
    @Log(title = "匠人档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HandcraftArtist handcraftArtist)
    {
        return toAjax(handcraftArtistService.updateHandcraftArtist(handcraftArtist));
    }

    /**
     * 删除匠人档案
     */
    @PreAuthorize("@ss.hasPermi('system:artist:remove')")
    @Log(title = "匠人档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(handcraftArtistService.deleteHandcraftArtistByIds(ids));
    }
}
