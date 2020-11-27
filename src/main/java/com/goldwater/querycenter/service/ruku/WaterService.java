package com.goldwater.querycenter.service.ruku;

import com.github.pagehelper.PageHelper;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.ruku.WaterDao;
import com.goldwater.querycenter.entity.management.Priviliges;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.ruku.vo.PumpR5Vo;
import com.goldwater.querycenter.entity.ruku.vo.RiverR5Vo;
import com.goldwater.querycenter.entity.ruku.vo.RsvrR5Vo;
import com.goldwater.querycenter.entity.ruku.vo.WasR5Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WaterService {
    @Autowired
    private RightDao rightDao;

    @Autowired
    private WaterDao waterDao;

    public Result queryRiver_R5(String stdm, String startTm, String endTm, int pageIndex, int length){
        Result rs = new Result();
        List<RiverR5Vo> list = new ArrayList<>();
        List<Priviliges> rightList = getRightList();

        PageHelper.startPage(pageIndex, length);

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = waterDao.queryRiver_R5_Level3(rightList.get(0).getPriviligeId(), stdm, startTm, endTm);
        }
        else {
            list = waterDao.queryRiver_R5(stdm, startTm, endTm);
        }

        rs.setData(list);
        rs.setTotal(Integer.parseInt(list.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result queryRsvr_R5(String stdm, String startTm, String endTm, int pageIndex, int length){
        Result rs = new Result();
        List<RsvrR5Vo> list = new ArrayList<>();
        List<Priviliges> rightList = getRightList();

        PageHelper.startPage(pageIndex, length);

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = waterDao.queryRsvr_R5_Level3(rightList.get(0).getPriviligeId(), stdm, startTm, endTm);
        }
        else {
            list = waterDao.queryRsvr_R5(stdm, startTm, endTm);
        }

        rs.setData(list);
        rs.setTotal(Integer.parseInt(list.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result queryWas_R5(String stdm, String startTm, String endTm, int pageIndex, int length){
        Result rs = new Result();
        List<WasR5Vo> list = new ArrayList<>();
        List<Priviliges> rightList = getRightList();

        PageHelper.startPage(pageIndex, length);

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = waterDao.queryWas_R5_Level3(rightList.get(0).getPriviligeId(), stdm, startTm, endTm);
        }
        else {
            list = waterDao.queryWas_R5(stdm, startTm, endTm);
        }

        rs.setData(list);
        rs.setTotal(Integer.parseInt(list.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result queryPump_R5(String stdm, String startTm, String endTm, int pageIndex, int length){
        Result rs = new Result();
        List<PumpR5Vo> list = new ArrayList<>();
        List<Priviliges> rightList = getRightList();

        PageHelper.startPage(pageIndex, length);

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = waterDao.queryPump_R5_Level3(rightList.get(0).getPriviligeId(), stdm, startTm, endTm);
        }
        else {
            list = waterDao.queryPump_R5(stdm, startTm, endTm);
        }

        rs.setData(list);
        rs.setTotal(Integer.parseInt(list.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    private List<Priviliges> getRightList(){
        User u = SessionCache.get();
        String userId = u.getUserCode();

        return rightDao.getRightByUserId(userId);
    }
}
