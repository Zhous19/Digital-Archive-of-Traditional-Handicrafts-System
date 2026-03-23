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
import com.ruoyi.system.domain.HandcraftTech;
import com.ruoyi.system.service.IHandcraftTechService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 技艺档案Controller
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
@RestController
@RequestMapping("/system/tech")
public class HandcraftTechController extends BaseController
{
    @Autowired
    private IHandcraftTechService handcraftTechService;

    /**
     * 查询技艺档案列表
     */
    @PreAuthorize("@ss.hasPermi('system:tech:list')")
    @GetMapping("/list")
    public TableDataInfo list(HandcraftTech handcraftTech)
    {
        startPage();
        List<HandcraftTech> list = handcraftTechService.selectHandcraftTechList(handcraftTech);
        return getDataTable(list);
    }

    /**
     * 导出技艺档案列表
     */
    @PreAuthorize("@ss.hasPermi('system:tech:export')")
    @Log(title = "技艺档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HandcraftTech handcraftTech)
    {
        List<HandcraftTech> list = handcraftTechService.selectHandcraftTechList(handcraftTech);
        ExcelUtil<HandcraftTech> util = new ExcelUtil<HandcraftTech>(HandcraftTech.class);
        util.exportExcel(response, list, "技艺档案数据");
    }

    /**
     * 获取技艺档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:tech:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(handcraftTechService.selectHandcraftTechById(id));
    }

    /**
     * 新增技艺档案
     */
    @PreAuthorize("@ss.hasPermi('system:tech:add')")
    @Log(title = "技艺档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HandcraftTech handcraftTech)
    {
        return toAjax(handcraftTechService.insertHandcraftTech(handcraftTech));
    }

    /**
     * 修改技艺档案
     */
    @PreAuthorize("@ss.hasPermi('system:tech:edit')")
    @Log(title = "技艺档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HandcraftTech handcraftTech)
    {
        return toAjax(handcraftTechService.updateHandcraftTech(handcraftTech));
    }

    /**
     * 删除技艺档案
     */
    @PreAuthorize("@ss.hasPermi('system:tech:remove')")
    @Log(title = "技艺档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(handcraftTechService.deleteHandcraftTechByIds(ids));
    }
}
