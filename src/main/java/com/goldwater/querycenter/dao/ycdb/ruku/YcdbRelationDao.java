package com.goldwater.querycenter.dao.ycdb.ruku;

import com.goldwater.querycenter.entity.ruku.vo.ZqrlRelationVo;
import com.goldwater.querycenter.entity.ruku.vo.ZvarlRelationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface YcdbRelationDao {
    List<ZqrlRelationVo> queryZqrlList_Level3(@Param("priviligeId") String priviligeId, @Param("stcd") String stcd);

    List<ZqrlRelationVo> queryZqrlList(@Param("stcd") String stcd);

    ZqrlRelationVo getZqrl(@Param("stcd") String stcd, @Param("ptno") String ptno, @Param("yr") String yr);

    int deleteZqrl(@Param("list") List<Map> list);

    List<ZvarlRelationVo> getZvarlList_Level3(@Param("priviligeId") String priviligeId, @Param("stcd") String stcd);

    List<ZvarlRelationVo> getZvarlList(@Param("stcd") String stcd);

    ZvarlRelationVo getZvarl(@Param("stcd") String stcd, @Param("ptno") String ptno);
}
