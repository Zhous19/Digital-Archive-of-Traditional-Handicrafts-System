package com.ruoyi.web.controller.handcraft;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.IHandcraftCategoryService;
import com.ruoyi.system.service.IHandcraftArtistService;
import com.ruoyi.system.service.IHandcraftWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手工艺统计数据控制器
 */
@RestController
@RequestMapping("/handcraft/stat")
public class HandcraftStatController {

    @Autowired
    private IHandcraftCategoryService handcraftCategoryService;

    @Autowired
    private IHandcraftArtistService handcraftArtistService;

    @Autowired
    private IHandcraftWorkService handcraftWorkService;

    /**
     * 获取统计数据
     */
    @GetMapping("/getData")
    public AjaxResult getData() {
        Map<String, Object> result = new HashMap<>();
        
        // 获取品类统计数据
        List<Map<String, Object>> categoryStats = handcraftCategoryService.selectCategoryCount();
        
        List<String> categoryNames = new ArrayList<>();
        List<Integer> categoryCounts = new ArrayList<>();
        
        for (Map<String, Object> stat : categoryStats) {
            categoryNames.add((String) stat.get("categoryName"));
            categoryCounts.add(((Number) stat.get("count")).intValue());
        }
        
        // 如果没有数据，使用默认数据
        if (categoryNames.isEmpty()) {
            categoryNames = new ArrayList<>();
            categoryNames.add("陶瓷");
            categoryNames.add("刺绣");
            categoryNames.add("木雕");
            categoryNames.add("竹编");
            categoryNames.add("剪纸");
            
            categoryCounts = new ArrayList<>();
            categoryCounts.add(25);
            categoryCounts.add(18);
            categoryCounts.add(15);
            categoryCounts.add(12);
            categoryCounts.add(10);
        }
        
        result.put("categoryNames", categoryNames);
        result.put("categoryCounts", categoryCounts);
        
        // 获取地域统计数据
        List<Map<String, Object>> regionStats = handcraftArtistService.selectRegionCount();
        
        List<String> regionNames = new ArrayList<>();
        List<Integer> regionCounts = new ArrayList<>();
        
        for (Map<String, Object> stat : regionStats) {
            regionNames.add((String) stat.get("regionName"));
            regionCounts.add(((Number) stat.get("count")).intValue());
        }
        
        // 如果没有数据，使用默认数据
        if (regionNames.isEmpty()) {
            regionNames = new ArrayList<>();
            regionNames.add("云南");
            regionNames.add("贵州");
            regionNames.add("四川");
            regionNames.add("江苏");
            regionNames.add("浙江");
            
            regionCounts = new ArrayList<>();
            regionCounts.add(12);
            regionCounts.add(9);
            regionCounts.add(15);
            regionCounts.add(11);
            regionCounts.add(8);
        }
        
        result.put("regionNames", regionNames);
        result.put("regionCounts", regionCounts);
        
        // 获取作品图片列表
        List<Map<String, Object>> workImages = handcraftWorkService.selectWorkImages();
        result.put("workImages", workImages);
        
        return AjaxResult.success(result);
    }
}
