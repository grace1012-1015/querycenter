package com.goldwater.querycenter.dao.rtuex;

import com.goldwater.querycenter.entity.ruku.RtuStation;
import com.goldwater.querycenter.entity.ruku.vo.StationMessageVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface RtuStationDao extends Mapper<RtuStation> {
    List<RtuStation> queryStcdStnm(@Param("stcd8") String stcd8, @Param("stcd") String stcd, @Param("rtucd") String rtucd);

    StationMessageVo selectTop1StationMessage(@Param("stcd8") String stcd8, @Param("sendTime") String sendTime);

    List<RtuStation> getStationList(@Param("stcd") String stcd, @Param("protocol") String protocol, @Param("sttp") String sttp);

    List<RtuStation> getStationSelect();

    int updateStation(String stcd, String rtucd, String stcd8, String stnm, String rvnm, String bsnm, String hnnm, String protocol, Double dtmel, String sttp, String telphone, String flag_hd, String center, String borrow, String flag_rain, String flag_water);

    int delStation(@Param("list") List<Map> list);

    int customStation(@Param("list") List<Map> list);

    int deleteCustom(@Param("list") List<Map> list);
}
