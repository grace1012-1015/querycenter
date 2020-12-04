package com.goldwater.querycenter.controller.ruku;


import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.JktjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jktj")
public class JktjController {
    @Autowired
    private JktjService jktjService;

    /**
     * 墒情监控(限站)
     */
    @PostMapping("/querySoil")
    @ResponseBody
    public Result querySoil(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                            @RequestParam(name = "time", defaultValue = "") String time){
        return jktjService.querySoil(stcd, time);
    }

    /**
     * 水文雨情监控(限站)
     */
    @PostMapping("/querySwRainJk")
    @ResponseBody
    public Result querySwRainJk(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                                @RequestParam(name = "time", defaultValue = "") String time,
                                @RequestParam(name = "addvcd", defaultValue = "") String addvcd,
                                @RequestParam(name = "type", defaultValue = "") String type,
                                @RequestParam(name = "custom", defaultValue = "") String custom){
        return jktjService.querySwRainJk(stcd, time, type, custom, addvcd);
    }
}
