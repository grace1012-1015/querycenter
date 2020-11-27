package com.goldwater.querycenter.dao.rtuex;

import com.goldwater.querycenter.entity.ruku.RtuStation;
import com.goldwater.querycenter.entity.ruku.vo.StationMessageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RtuStationDao {
    List<RtuStation> queryStcdStnm(@Param("stcd8") String stcd8);

    StationMessageVo selectTop1StationMessage(@Param("stcd8") String stcd8, @Param("sendTime") String sendTime);
}
