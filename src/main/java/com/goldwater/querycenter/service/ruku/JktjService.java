package com.goldwater.querycenter.service.ruku;

import com.goldwater.querycenter.common.util.BeanUtil;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.common.util.date.DateToolkits;
import com.goldwater.querycenter.common.util.string.StringUtil;
import com.goldwater.querycenter.dao.rw.ruku.RwJktjDao;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.ruku.CosstDao;
import com.goldwater.querycenter.dao.ycdb.ruku.YcdbJktjDao;
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

    @Autowired
    private YcdbJktjDao ycdbJktjDao;

    public Result querySoil(String stcd, String time){
        Result rs = new Result();
        List<Map> result = new ArrayList<>();
        List<CosstVo> listCosst = new ArrayList<>();

        List<Priviliges> rightList = getRights();

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3) {
            listCosst = cosstDao.getSoilCosst_Level3(rightList.get(0).getPriviligeId(), stcd);
        }
        else{
            listCosst = cosstDao.getSoilCosst(stcd);
        }

        try {
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
        catch (Exception ex){
            rs.setCode(Result.FAILURE);
            rs.setMsg(ex.getMessage());
        }

        return rs;
    }

    public Result querySwRainJk(String stcd, String time, String type, String custom, String addvcd){
        Result rs = new Result();
        List<Map> result = new ArrayList<>();
        List<CosstVo> listCosst = getSwjkCosst(stcd, type, custom, addvcd);

        try {
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
        catch (Exception ex){
            rs.setCode(Result.FAILURE);
            rs.setMsg(ex.getMessage());
        }

        return rs;
    }

    public Result querySwWaterlevelJk(String stcd, String time, String type, String custom, String addvcd){
        Result rs = new Result();
        List<Map> result = new ArrayList<>();
        List<CosstVo> listCosst = getSwjkCosst(stcd, type, custom, addvcd);

        try {
            for (CosstVo csvo : listCosst) {
                List<Map> list = new ArrayList<>();

                if("61074750".equals(csvo.getStcd())){
                    System.out.println(csvo.getStcd());

                    continue;
                }

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

                String sttp = ycdbJktjDao.getSttp(csvo.getStcd());
                String upOrDown = "R.DWZ";
                String ppupOrPpdown = "R.PPDWZ";

                if( !StringUtil.isBlank(sttp) && sttp.contains("闸上") ) {
                    upOrDown = "R.UPZ";
                }
                if(!StringUtil.isBlank(sttp)&&sttp.contains("泵上")) {
                    ppupOrPpdown = " R.PPUPZ";
                }

                result.addAll(rwJktjDao.querySwWaterJk(list, csvo.getStcd(), time, StringUtil.isBlank(time) ? "" : DateToolkits.StrDateAdd(time,DateToolkits.Day ,1), upOrDown, ppupOrPpdown));
            }

            rs.setCode(Result.SUCCESS);
            rs.setData(result);
        }
        catch (Exception ex){
            rs.setCode(Result.FAILURE);
            rs.setMsg(ex.getMessage());
        }

        return rs;
    }

    public Result querySwRainTJ(String stcd, String searchTime, String type, String interval){
        Result rs = new Result();
        List<CosstVo> listCosst = getSwjkCosst(stcd, type, "", "");

        try {
            for (CosstVo csvo : listCosst) {
                String time = searchTime.replaceAll("-", "").replaceAll(" ", "");

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                calendar.setTime(simpleDateFormat.parse(time));
                calendar.add(Calendar.DAY_OF_MONTH, -1);

                if (!StringUtil.isBlank(csvo.getStcd())){
                    if(!"00".equals(interval)){
                        csvo.setListPptn(rwJktjDao.querySwRainTJ(stcd, time, time + interval));
                    }
                    else {
                        csvo.setListPptn(rwJktjDao.querySwRainTJ_00(stcd, (Integer.parseInt(time)) + "08"));
                    }
                }
            }

            rs.setCode(Result.SUCCESS);
            rs.setData(sortMap((List<Map<String, Object>>) BeanUtil.objectToMap(listCosst), 1, 1));
        }
        catch (Exception ex){
            rs.setCode(Result.FAILURE);
            rs.setMsg(ex.getMessage());
        }

        return rs;
    }

    public Result querySwRainTJ2(String stcd, String type, String interval){
        Result rs = new Result();
        List<CosstVo> listCosst = getSwjkCosst(stcd, type, "", "");

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");
        // 当前时间
        Date nowdate = new Date();
        String nowTime = df.format(nowdate);
        System.out.println("当前时间" + nowTime);
        //获取前几个小时的时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(interval));
        String oldTime = df.format(calendar.getTime());

        for (CosstVo csvo : listCosst) {
            if (!StringUtil.isBlank(csvo.getStcd())){
                if(!"00".equals(interval)) {
                    csvo.setListPptn(rwJktjDao.querySwRainTJ2(stcd, nowTime, oldTime));
                }
                else{
                    csvo.setListPptn(rwJktjDao.querySwRainTJ2_00(stcd, nowTime));
                }
            }
        }

        rs.setCode(Result.SUCCESS);
        rs.setData(sortMap((List<Map<String, Object>>) BeanUtil.objectToMap(listCosst), 1, 1));

        return rs;
    }

    private List<Map<String,Object>> sortMap(List<Map<String,Object>> map,int start, int limit){
        Collections.sort(map,new Comparator<Map<String,Object>>() {
            public int compare(Map<String, Object> o1,Map<String, Object> o2) {
                //o1，o2是list中的Map，可以在其内取得值，按其排序，此例为升序，s1和s2是排序字段值
                Double s1=0.0,s2=0.0;
                Iterator<Map.Entry<String,Object>> it1 = o1.entrySet().iterator();
                while (it1.hasNext()) {
                    Map.Entry<String, Object> entry = it1.next();
                    if("TJ".equals(entry.getKey().trim())){
                        if(entry.getValue()!=null){
                            s1=Double.parseDouble(entry.getValue().toString());
                        }
                    }
                }
                Iterator<Map.Entry<String,Object>> it2 = o2.entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry<String, Object> entry = it2.next();
                    if("TJ".equals(entry.getKey().trim())){
                        if(entry.getValue()!=null){
                            s2=Double.parseDouble(entry.getValue().toString());
                        }
                    }
                }
                if(s2!=null){
                    return s2.compareTo(s1);
                }
                return -1;
            }
        });
        int end=start*limit==0?limit:(start+1)*limit;
        if(end>=map.size()){
            end=map.size();
        }
        start=start*limit==0?0:start*limit-1;
        return map.subList(start,end);
    }

    private List<Priviliges> getRights(){
        User u = SessionCache.get();
        String userId = u.getUserCode();

        return rightDao.getRightByUserId(userId);
    }

    private List<CosstVo> getSwjkCosst(String stcd, String type, String custom, String addvcd){
        List<Priviliges> rightList = getRights();

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3) {
            return cosstDao.getSwRainCosst_Level3(rightList.get(0).getPriviligeId(), stcd, type, custom, addvcd);
        }

        return cosstDao.getSwRainCosst(stcd, type, custom, addvcd);

    }
}
