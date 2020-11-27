package com.goldwater.querycenter.service.ruku;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.common.util.string.StringUtil;
import com.goldwater.querycenter.dao.rw.ruku.RwRelationDao;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.ruku.YcdbRelationDao;
import com.goldwater.querycenter.entity.management.Priviliges;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.ruku.vo.ZqrlRelationVo;
import com.goldwater.querycenter.entity.ruku.vo.ZvarlRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RelationService {
    @Autowired
    private YcdbRelationDao ycdbRelationDao;

    @Autowired
    private RwRelationDao rwRelationDao;

    @Autowired
    private RightDao rightDao;

    public Result queryZqrlList(String stcd, int pageIndex, int length){
        Result rs = new Result();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        List<ZqrlRelationVo> list = new ArrayList<>();

        PageHelper.startPage(pageIndex, length);

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = ycdbRelationDao.queryZqrlList_Level3(rightList.get(0).getPriviligeId(), stcd);
        }
        else {
            list = ycdbRelationDao.queryZqrlList(stcd);
        }

        PageInfo p = new PageInfo<>(list);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result addZqrl(String stcd, String ptno, String z, String q){
        Result rs = new Result();
        int result = 0;

        // 点序号不为空时说明是更新数据
        if(ptno != null && !StringUtil.isBlank(ptno)){
            result = rwRelationDao.updateZqrl(stcd, ptno, z, q);
        }
        else {
            //点序号为空时，说明是新增数据，根据测站编码自增长
            String maxPtno = rwRelationDao.getMaxZqrlPtno(stcd);

            if(null == maxPtno || "".equals(maxPtno)){
                maxPtno="1";
            }else{
                maxPtno=String.valueOf(Integer.valueOf(ptno)+1);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            String yr = sdf.format(date);

            result = rwRelationDao.insertZqrl(stcd, "水位流量曲线", yr, maxPtno, z, q);
        }

        if (result > 0){
            rs.setCode(Result.SUCCESS);
        }
        else{
            rs.setCode(Result.FAILURE);
            rs.setMsg("添加水位流量关系失败！");
        }

        return rs;
    }

    public Result getZqrl(String stcd, String ptno){
        Result rs = new Result();

        //rs.setData(relationDao.getZqrl(stcd,ptno));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result deleteZqrl(String stcd_ptno){
        Result rs = new Result();

        if (!"".equals(stcd_ptno) && !"null".equals(stcd_ptno)) {
            String[] stcd_ptnoarr = stcd_ptno.split(";");
            List<Map> list = new ArrayList<>();

            for (int i = 0; i < stcd_ptnoarr.length; i++) {
                Map m = new HashMap();

                String[] stcdarr = stcd_ptnoarr[i].split(",");

                m.put("stcd", stcdarr[0]);
                m.put("ptno", stcdarr[1]);

                list.add(m);
            }

            if (ycdbRelationDao.deleteZqrl(list) > 0){
                rs.setCode(Result.SUCCESS);
            }
            else{
                rs.setCode(Result.FAILURE);
                rs.setMsg("删除水位流量关系失败！");
            }
        }

        return rs;
    }

    public Result getZvarlList(String stcd, int pageIndex, int length){
        Result rs = new Result();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        List<ZvarlRelationVo> list = new ArrayList<>();

        PageHelper.startPage(pageIndex, length);

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = ycdbRelationDao.getZvarlList_Level3(rightList.get(0).getPriviligeId(), stcd);
        }
        else {
            list = ycdbRelationDao.getZvarlList(stcd);
        }

        PageInfo p = new PageInfo<>(list);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result addZvarl(String stcd, String ptno, String rz, String w, String wsfa){
        Result rs = new Result();
        int result = 0;

        // 点序号不为空时说明是更新数据
        if(ptno != null && !StringUtil.isBlank(ptno)){
            result = rwRelationDao.updateZvarl(stcd, ptno, rz, w, wsfa);
        }
        else {
            //点序号为空时，说明是新增数据，根据测站编码自增长
            String maxPtno = rwRelationDao.getMaxZvarlPtno(stcd);

            if(null == maxPtno || "".equals(maxPtno)){
                maxPtno="1";
            }else{
                maxPtno=String.valueOf(Integer.valueOf(ptno)+1);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            String yr = sdf.format(date);

            result = rwRelationDao.insertZvarl(stcd, maxPtno, rz, w, wsfa);
        }

        if (result > 0){
            rs.setCode(Result.SUCCESS);
        }
        else{
            rs.setCode(Result.FAILURE);
            rs.setMsg("添加水位库容关系失败！");
        }

        return rs;
    }

    public Result getZvarl(String stcd, String ptno){
        Result rs = new Result();

        rs.setData(ycdbRelationDao.getZvarl(stcd, ptno));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result deleteZvarl(String stcd_ptno){
        Result rs = new Result();

        if (!"".equals(stcd_ptno) && !"null".equals(stcd_ptno)) {
            String[] stcd_ptnoarr = stcd_ptno.split(";");
            List<Map> list = new ArrayList<>();

            for (int i = 0; i < stcd_ptnoarr.length; i++) {
                Map m = new HashMap();

                String[] stcdarr = stcd_ptnoarr[i].split(",");

                m.put("stcd", stcdarr[0]);
                m.put("ptno", stcdarr[1]);

                list.add(m);
            }

            if (rwRelationDao.deleteZvarl(list) > 0){
                rs.setCode(Result.SUCCESS);
            }
            else{
                rs.setCode(Result.FAILURE);
                rs.setMsg("删除水位库容关系失败！");
            }
        }

        return rs;
    }
}
