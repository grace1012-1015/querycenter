package com.goldwater.querycenter.dao.rw.ruku;

import com.goldwater.querycenter.entity.ruku.SingleHourRain;
import com.goldwater.querycenter.entity.ruku.SingleHourRiver;
import com.goldwater.querycenter.entity.ruku.SingleRZWRelation;
import com.goldwater.querycenter.entity.ruku.SingleZqrlRelation;
import com.goldwater.querycenter.entity.ruku.vo.SingleRvffchVo;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ChartDao {
    List<SingleHourRain> getSingleHourRain(String stcd, String sttm, String entm);

    List<SingleHourRiver> getSingleHourRiver(String stcd, String sttm, String entm);

    List<SingleRvffchVo> getSingleRvffch(String stcd);

    List<SingleRZWRelation> getSingleRZWRelation(String stcd);

    List<SingleZqrlRelation> getSingleZqrlRelation(String stcd);
}
