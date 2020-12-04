package com.goldwater.querycenter.service.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.common.util.date.DateToolkits;
import com.goldwater.querycenter.common.util.string.StringUtil;
import com.goldwater.querycenter.dao.rw.ruku.RwJktjDao;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.ruku.CosstDao;
import com.goldwater.querycenter.entity.management.Priviliges;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.ruku.vo.CosstVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class JktjService {
    @Autowired
    private CosstDao cosstDao;

    @Autowired
    private RightDao rightDao;

    @Autowired
    private RwJktjDao rwJktjDao;

    public Result querySoil(String stcd, String time){
        Result rs = new Result();
        List<Map> result = new ArrayList<>();
        List<CosstVo> listCosst = new ArrayList<>();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3) {
            listCosst = cosstDao.getSoilCosst_Level3(rightList.get(0).getPriviligeId(), stcd);
        }
        else{
            listCosst = cosstDao.getSoilCosst(stcd);
        }

        try {
            if (listCosst.size() > 0) {
                for (CosstVo csvo : listCosst){
                    List<Map> list = new ArrayList<>();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(time));
                    int thisMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    int today = calendar.get(Calendar.DATE);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int lastMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                    calendar.add(Calendar.MONTH, -1);
                    String monthStr = month < 10 ? "0" + month : "" + month;
                    String lastMonthStr = month - 1 < 10 ? "0" + (month - 1) : "" + (month - 1);

                    if (today == thisMaxDay || today >= lastMaxDay) {
                        int dayIndex = 1;

                        for (int i = 1; i <= thisMaxDay; i++) {
                            Map m = new HashMap();
                            String day;
                            dayIndex++;

                            if (i < 10) {
                                day = "0" + i;
                            } else {
                                day = "" + i;
                            }

                            m.put("drp", "day" + dayIndex);
                            m.put("date", year + monthStr + day + "08");

                            list.add(m);
                        }
                    } else {
                        String day;
                        int dayIndex = 0;

                        for (int i = today; i <= lastMaxDay; i++) {
                            Map m = new HashMap();
                            dayIndex++;

                            if (i < 10) {
                                day = "0" + i;
                            } else {
                                day = "" + i;
                            }


                            m.put("drp", "day" + dayIndex);
                            m.put("date", year + lastMonthStr + day + "08");

                            list.add(m);
                        }

                        for(int i=1;i<=today;i++){
                            Map m = new HashMap();
                            dayIndex++;

                            if(i<10){
                                day="0"+i;
                            }else{
                                day=""+i;
                            }

                            m.put("drp", "day" + dayIndex);
                            m.put("date", year + monthStr + day + "08");

                            list.add(m);
                        }
                    }

                    result.addAll(rwJktjDao.querySoil(list, csvo.getStcd()));
                }

                rs.setCode(Result.SUCCESS);
                rs.setData(result);
            }
        }
        catch (Exception ex){
            rs.setCode(Result.FAILURE);
            rs.setMsg(ex.getMessage());
        }

        return rs;
    }

    public Result querySwRainJk(String stcd, String time, String type, String custom, String addvcd){
        Result rs = new Result();
        List<Map> result = new ArrayList<>();
        List<CosstVo> listCosst = new ArrayList<>();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3) {
            listCosst = cosstDao.getSwRainCosst_Level3(rightList.get(0).getPriviligeId(), stcd, type, custom, addvcd);
        }
        else{
            listCosst = cosstDao.getSwRainCosst(stcd, type, custom, addvcd);
        }

        try {
            if (listCosst.size() > 0) {
                for (CosstVo csvo : listCosst) {
                    List<Map> list = new ArrayList<>();

                    for(int i=0;i<24;i++){
                        String index;
                        Map m = new HashMap();

                        if(i<10){
                            index="0"+i;
                        }else{
                            index=""+i;
                        }

                        m.put("index", index);
                        m.put("drp", "tm" + i);

                        list.add(m);
                    }

                    result.addAll(rwJktjDao.querySwRainJk(list, csvo.getStcd(), time, StringUtil.isBlank(time) ? "" : DateToolkits.StrDateAdd(time,DateToolkits.Day ,1)));
                }

                rs.setCode(Result.SUCCESS);
                rs.setData(result);
            }
        }
        catch (Exception ex){
            rs.setCode(Result.FAILURE);
            rs.setMsg(ex.getMessage());
        }

        return rs;
    }
}
