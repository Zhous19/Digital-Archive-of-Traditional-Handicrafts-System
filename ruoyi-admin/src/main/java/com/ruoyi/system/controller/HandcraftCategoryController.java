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
import com.ruoyi.system.domain.HandcraftCategory;
import com.ruoyi.system.service.IHandcraftCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 手工艺品类Controller
 * 
 * @author ruoyi
 * @date 2026-02-03
 */
@RestController
@RequestMapping("/system/category")
public class HandcraftCategoryController extends BaseController
{
    @Autowired
    private IHandcraftCategoryService handcraftCategoryService;

    /**
     * 查询手工艺品类列表
     */
    @PreAuthorize("@ss.hasPermi('system:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(HandcraftCategory handcraftCategory)
    {
        startPage();
        List<HandcraftCategory> list = handcraftCategoryService.selectHandcraftCategoryList(handcraftCategory);
        return getDataTable(list);
    }

    /**
     * 导出手工艺品类列表
     */
    @PreAuthorize("@ss.hasPermi('system:category:export')")
    @Log(title = "手工艺品类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HandcraftCategory handcraftCategory)
    {
        List<HandcraftCategory> list = handcraftCategoryService.selectHandcraftCategoryList(handcraftCategory);
        ExcelUtil<HandcraftCategory> util = new ExcelUtil<HandcraftCategory>(HandcraftCategory.class);
        util.exportExcel(response, list, "手工艺品类数据");
    }

    /**
     * 获取手工艺品类详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(handcraftCategoryService.selectHandcraftCategoryById(id));
    }

    /**
     * 新增手工艺品类
     */
    @PreAuthorize("@ss.hasPermi('system:category:add')")
    @Log(title = "手工艺品类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HandcraftCategory handcraftCategory)
    {
        return toAjax(handcraftCategoryService.insertHandcraftCategory(handcraftCategory));
    }

    /**
     * 修改手工艺品类
     */
    @PreAuthorize("@ss.hasPermi('system:category:edit')")
    @Log(title = "手工艺品类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HandcraftCategory handcraftCategory)
    {
        return toAjax(handcraftCategoryService.updateHandcraftCategory(handcraftCategory));
    }

    /**
     * 删除手工艺品类
     */
    @PreAuthorize("@ss.hasPermi('system:category:remove')")
    @Log(title = "手工艺品类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(handcraftCategoryService.deleteHandcraftCategoryByIds(ids));
    }
}
