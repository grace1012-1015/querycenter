package com.goldwater.querycenter.dao.rw.ruku;

import com.goldwater.querycenter.entity.ruku.vo.RiverVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface RwJktjDao {
    List<Map> querySoil(@Param("list") List<Map> list, @Param("stcd") String stcd);

    List<Map> querySwRainJk(@Param("list") List<Map> list, @Param("stcd") String stcd, @Param("time") String time, @Param("tomorTime") String tomorTime);
}
