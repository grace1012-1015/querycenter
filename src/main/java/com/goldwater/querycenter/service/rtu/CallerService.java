package com.goldwater.querycenter.service.rtu;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.dao.rtuex.CallerDao;
import com.goldwater.querycenter.entity.rtu.RtuCaller;
import com.goldwater.querycenter.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class CallerService {
    @Autowired
    private CallerDao callerDao;

    @Autowired
    private RedisService redisService;

    public Result getSolidStateData(String rtucds, String func, String params){
        Result rs = new Result();

        if (!rtucds.equals("")) {
            String rtucdslist[] = rtucds.split(",");

            for (int i = 0; i < rtucdslist.length; i++) {
                RtuCaller rc = new RtuCaller();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                String id = UUID.randomUUID().toString();
                String rtucd = rtucdslist[i];

                rc.setId(id);
                rc.setRtucd(rtucd);
                rc.setFunc(func);
                rc.setParams(params);
                rc.setCmdTime(df.format(new Date()));

                callerDao.insertSelective(rc);

                String tempStr = params.replace("-","");
                String start = tempStr.substring(2,8);
                String end = tempStr.substring(11,17);

                //雨量固态数据提取
                setCallerRedisData(rtucd, id, func, "1$" + start + "-" + end);
                //水位固态数据提取
                setCallerRedisData(rtucd, id, func, "4$"+ start + "-" + end);
            }

            rs.setCode(Result.SUCCESS);
        }
        else{
            rs.setMsg("RTUCDS不能为空！");
            rs.setCode(Result.FAILURE);
        }

        return rs;
    }

    public Result jiaoshi(String params){
        Result rs = new Result();

        if (!params.equals("")) {
            String[] paramslist = params.split(";"); // 多选

            for (int i = 0; i < paramslist.length; i++) {
                String param = paramslist[i];

                if (!"".equals(param)) {
                    addJiaoshiOrRealTime(param, "3");
                }
            }

            rs.setCode(Result.SUCCESS);
        }
        else{
            rs.setMsg("PARAMS不能为空！");
            rs.setCode(Result.FAILURE);
        }

        return rs;
    }

    public Result realTime(String params){
        Result rs = new Result();

        if (!params.equals("")) {
            String[] paramslist = params.split(";"); // 多选

            for (int i = 0; i < paramslist.length; i++) {
                String param = paramslist[i];

                if (!"".equals(param)) {
                    addJiaoshiOrRealTime(param, "2");
                }
            }

            rs.setCode(Result.SUCCESS);
        }
        else{
            rs.setMsg("PARAMS不能为空！");
            rs.setCode(Result.FAILURE);
        }

        return rs;
    }

    private void addJiaoshiOrRealTime(String param, String fileContent){
        String[] cds = param.split(",");
        RtuCaller rc = new RtuCaller();

        String id = UUID.randomUUID().toString();
        String rtucd = cds[0];
        String func = cds[1];

        rc.setId(id);
        rc.setRtucd(rtucd);
        rc.setFunc(func);

        callerDao.insertSelective(rc);

        setCallerRedisData(rtucd, id, func, fileContent);
    }

    private void setCallerRedisData(String stcd, String id, String funcCode, String hexMessage){
        String content = stcd + "#" + id + "#" + funcCode + "#" + hexMessage;

        redisService.set(stcd + "YYCallder", content);
    }
}
