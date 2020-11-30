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

    /**
     * 添加测站和分类关系信息
     */
    @PostMapping("/addCosst")
    @ResponseBody
    public Result addCosst(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                           @RequestParam(name = "STNM", defaultValue = "") String stnm,
                           @RequestParam(name = "ARNM", defaultValue = "") String arnm,
                           @RequestParam(name = "ORDR", defaultValue = "") String ordr,
                           @RequestParam(name = "ID", defaultValue = "") String id){
        return relationService.addCosst(id, stcd, stnm, arnm, ordr);
    }

    /**
     * 单条测站和分类关系数据查询
     */
    @PostMapping("/getCosst")
    @ResponseBody
    public Result getCosst(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                           @RequestParam(name = "ID", defaultValue = "") String id){
        return relationService.getCosst(stcd, id);
    }

    /**
     * 删除测站和分类关系信息
     */
    @PostMapping("/deleteCosst")
    @ResponseBody
    public Result deleteCosst(@RequestParam(name = "STCD_ID", defaultValue = "") String stcd_id){
        return relationService.deleteCosst(stcd_id);
    }

    /**
     * 检查新增水位流量关系数据是否存在
     */
    @PostMapping("/checkZqrl")
    @ResponseBody
    public Result checkZqrl(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                            @RequestParam(name = "yr", defaultValue = "") String yr,
                            @RequestParam(name = "ptno", defaultValue = "") String ptno){
        return relationService.checkZqrl(stcd, yr, ptno);
    }

    /**
     * 测站配置表的维护
     *
     */
    @PostMapping("/getStationList")
    @ResponseBody
    public Result getStationList(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                                 @RequestParam(name = "protocol", defaultValue = "") String protocol,
                                 @RequestParam(name = "sttp", defaultValue = "") String sttp,
                                 @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                                 @RequestParam(name = "limit", defaultValue = "10") int length){
        return relationService.getStationList(stcd, protocol, sttp, pageIndex, length);
    }
}
