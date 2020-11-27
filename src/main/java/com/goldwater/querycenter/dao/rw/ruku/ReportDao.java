package com.goldwater.querycenter.dao.rw.ruku;

import com.goldwater.querycenter.entity.ruku.ReportConfigMetaData;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface ReportDao {
    List<ReportConfigMetaData> getReportConfigMetaData(String rid);

    List<Map<String, Object>> getSqjbList(String rid, List<ReportConfigMetaData> list);
}