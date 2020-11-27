package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relation")
public class RelationController {
    @Autowired
    private RelationService relationService;

    /**
     * 水位流量关系数据查询
     */
    @PostMapping("/queryZqrlList")
    @ResponseBody
    public Result queryZqrlList(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                                @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                                @RequestParam(name = "limit", defaultValue = "10") int length){
        return relationService.queryZqrlList(stcd, pageIndex, length);
    }

    /**
     * 添加水位流量关系
     *
     */
    @PostMapping("/addZqrl")
    @ResponseBody
    public Result addZqrl(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                          @RequestParam(name = "PTNO", defaultValue = "") String ptno,
                          @RequestParam(name = "Z", defaultValue = "") String z,
                          @RequestParam(name = "Q", defaultValue = "") String q){
        return relationService.addZqrl(stcd, ptno, z, q);
    }

    /**
     * 单条水位流量关系数据查询
     *
     * @author qpp 2017-04-17
     */
    @PostMapping("/getZqrl")
    @ResponseBody
    public Result getZqrl(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                          @RequestParam(name = "PTNO", defaultValue = "") String ptno){
        return relationService.getZqrl(stcd, ptno);
    }

    /**
     * 删除水位流量关系
     *
     */
    @PostMapping("/deleteZqrl")
    @ResponseBody
    public Result deleteZqrl(@RequestParam(name = "STCD_PTNO", defaultValue = "") String stcd_ptno){
        return relationService.deleteZqrl(stcd_ptno);
    }

    /**
     * 水位库容关系数据查询
     */
    @PostMapping("/getZvarlList")
    @ResponseBody
    public Result getZvarlList(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                               @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                               @RequestParam(name = "limit", defaultValue = "10") int length){
        return relationService.getZvarlList(stcd, pageIndex, length);
    }

    /**
     * 添加水位库容关系
     *
     */
    @PostMapping("/addZvarl")
    @ResponseBody
    public Result addZvarl(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                           @RequestParam(name = "PTNO", defaultValue = "") String ptno,
                           @RequestParam(name = "RZ", defaultValue = "") String rz,
                           @RequestParam(name = "W", defaultValue = "") String w,
                           @RequestParam(name = "WSFA", defaultValue = "") String wsfa){
        return relationService.addZvarl(stcd, ptno, rz, w, wsfa);
    }

    /**
     * 单条水位库容关系数据查询
     *
     * @author qpp 2017-04-17
     */
    @PostMapping("/getZvarl")
    @ResponseBody
    public Result getZvarl(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                           @RequestParam(name = "PTNO", defaultValue = "") String ptno){
        return relationService.getZvarl(stcd, ptno);
    }

    /**
     * 删除水位库容关系
     *
     */
    @PostMapping("/deleteZvarl")
    @ResponseBody
    public Result deleteZvarl(@RequestParam(name = "STCD_PTNO", defaultValue = "") String stcd_ptno){
        return relationService.deleteZvarl(stcd_ptno);
    }
}
