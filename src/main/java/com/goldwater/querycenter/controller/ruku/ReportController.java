package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 数据查询
     */
    @PostMapping("/getSqjbList")
    @ResponseBody
    public Result getSqjbList(@RequestParam(name = "RID", defaultValue = "") String rid,
                              @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
                              @RequestParam(name = "limit", defaultValue = "10") int limit){
        return reportService.getSqjbList(rid, pageIndex, limit);
    }
}
