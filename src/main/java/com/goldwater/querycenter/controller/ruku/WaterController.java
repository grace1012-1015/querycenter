package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/water")
public class WaterController {
    @Autowired
    private WaterService waterService;

    /**
     * 查询五分钟河道水情
     */
    @PostMapping("/queryRiverFm")
    @ResponseBody
    public Result queryRiverFm(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                              @RequestParam(name = "starttm", defaultValue = "") String startTm,
                              @RequestParam(name = "endtm", defaultValue = "") String endTm,
                              @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                              @RequestParam(name = "limit", defaultValue = "10") int length){
        return waterService.queryRiver_R5(stcd, startTm, endTm, pageIndex, length);
    }

    /**
     * 查询五分钟水库水情
     */
    @PostMapping("/queryRsvrFm")
    @ResponseBody
    public Result queryRsvrFm(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                               @RequestParam(name = "starttm", defaultValue = "") String startTm,
                               @RequestParam(name = "endtm", defaultValue = "") String endTm,
                               @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                               @RequestParam(name = "limit", defaultValue = "10") int length){
        return waterService.queryRsvr_R5(stcd, startTm, endTm, pageIndex, length);
    }

    /**
     * 查询5分钟堰闸水情数据
     */
    @PostMapping("/queryWasFm")
    @ResponseBody
    public Result queryWasFm(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                               @RequestParam(name = "starttm", defaultValue = "") String startTm,
                               @RequestParam(name = "endtm", defaultValue = "") String endTm,
                               @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                               @RequestParam(name = "limit", defaultValue = "10") int length){
        return waterService.queryWas_R5(stcd, startTm, endTm, pageIndex, length);
    }

    /**
     * 查询5分钟泵站水情数据
     */
    @PostMapping("/queryPumpFm")
    @ResponseBody
    public Result queryPumpFm(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                               @RequestParam(name = "starttm", defaultValue = "") String startTm,
                               @RequestParam(name = "endtm", defaultValue = "") String endTm,
                               @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                               @RequestParam(name = "limit", defaultValue = "10") int length){
        return waterService.queryPump_R5(stcd, startTm, endTm, pageIndex, length);
    }
}
