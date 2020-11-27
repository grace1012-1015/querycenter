package com.goldwater.querycenter.service.ruku;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.dao.rw.ruku.ReportDao;
import com.goldwater.querycenter.entity.ruku.ReportConfigMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private ReportDao reportDao;

    public Result getSqjbList(String rid, int pageIndex, int length){
        Result rs = new Result();

        List<ReportConfigMetaData> metaList = reportDao.getReportConfigMetaData(rid);

        PageHelper.startPage(pageIndex, length);

        List<Map<String, Object>> list = reportDao.getSqjbList(rid, metaList);

        PageInfo p = new PageInfo<>(list);
        rs.setData(p);
        rs.setData(list);
        rs.setCode(Result.SUCCESS);

        return rs;
    }
}
