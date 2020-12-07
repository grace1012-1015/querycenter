package com.goldwater.querycenter.dao.ycdb.ruku;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface YcdbJktjDao {
    String getSttp(@Param("stcd") String stcd);
}
