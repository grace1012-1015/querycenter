package com.goldwater.querycenter.dao.rw.ruku;

import com.goldwater.querycenter.entity.ruku.vo.*;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface QbtjDao {
    List<Map<String, Object>> queryRain(List<QueryRainPo> list, String stdm);
}
