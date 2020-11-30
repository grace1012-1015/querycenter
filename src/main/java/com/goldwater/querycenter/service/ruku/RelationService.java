package com.goldwater.querycenter.service.ruku;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.common.util.string.StringUtil;
import com.goldwater.querycenter.dao.rtuex.RtuStationDao;
import com.goldwater.querycenter.dao.rw.ruku.RwRelationDao;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.ruku.CosstDao;
import com.goldwater.querycenter.dao.ycdb.ruku.YcdbRelationDao;
import com.goldwater.querycenter.entity.management.Priviliges;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.ruku.Cosst;
import com.goldwater.querycenter.entity.ruku.RtuStation;
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
    private CosstDao cosstDao;

    @Autowired
    private RightDao rightDao;

    @Autowired
    private RtuStationDao rtuStationDao;

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

    public Result addCosst(String id, String stcd, String stnm, String arnm, String  ordr){
        Result rs = new Result();

        try {
            Cosst cosst = new Cosst();

            if (!StringUtil.isBlank(id)) {
                cosst.setId(id);
            }
            if (!StringUtil.isBlank(stcd)) {
                cosst.setStcd(stcd);
            }
            if (!StringUtil.isBlank(stnm)) {
                cosst.setStnm(stnm);
            }
            if (!StringUtil.isBlank(arnm)) {
                cosst.setArnm(arnm);
            }

            // 顺序号不为空时说明是更新数据
            if (!StringUtil.isBlank(ordr)) {
                cosst.setOrdr(ordr);

                cosstDao.updateCosst(id, stcd, stnm, arnm, ordr);
            } else {
                String ptnosql = cosstDao.getMaxOrdr(stcd, id);

                if (null == ptnosql || "".equals(ordr)) {
                    ptnosql = "1";
                } else {
                    ptnosql = String.valueOf(Integer.valueOf(ptnosql) + 1);
                }

                cosst.setOrdr(ptnosql);

                cosstDao.insertSelective(cosst);
            }

            rs.setCode(Result.SUCCESS);
        }
        catch(Exception ex)
        {
            rs.setMsg("新增测站和分类失败！");
            rs.setCode(Result.FAILURE);
        }

        return rs;
    }

    public Result getCosst(String stcd, String id){
        Result rs = new Result();

        Cosst c = new Cosst();

        c.setId(id);
        c.setStcd(stcd);

        rs.setCode(Result.SUCCESS);
        rs.setData(cosstDao.selectOne(c));

        return rs;
    }

    public Result deleteCosst(String stcd_id) {
        Result rs = new Result();

        if (!StringUtil.isBlank(stcd_id)) {
            List<Map> list = new ArrayList<>();
            String[] stcd_ptnoarr = stcd_id.split(";");

            for (int i = 0; i < stcd_ptnoarr.length; i++) {
                String[] stcdarr = stcd_ptnoarr[i].split(",");
                Map m = new HashMap();

                m.put("stcd", stcdarr[0]);
                m.put("id", stcdarr[1]);

                list.add(m);
            }

            cosstDao.deleteCosst(list);
            rs.setCode(Result.SUCCESS);
        }
        else{
            rs.setMsg("删除测站和分类失败！");
            rs.setCode(Result.FAILURE);
        }

        return rs;
    }


    public Result checkZqrl(String stcd, String yr, String ptno){
        Result rs = new Result();
        Map<String,Object> map = new HashMap<String,Object>();

        ZqrlRelationVo zqrl = ycdbRelationDao.getZqrl(stcd, ptno, yr);

        if (zqrl != null){
            map.put("STATUS", false);
        }
        else {
            map.put("STATUS", true);
        }

        rs.setData(map);
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getStationList(String stcd, String protocol, String sttp, int pageIndex, int length){
        Result rs = new Result();
        List<RtuStation> list = new ArrayList<>();

        PageHelper.startPage(pageIndex, length);

        list = rtuStationDao.getStationList(stcd, protocol, sttp);

        PageInfo p = new PageInfo<>(list);

        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }
}
