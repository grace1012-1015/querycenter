package com.goldwater.querycenter.entity.ruku;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "RTU_STATION")
public class RtuStation {
    @Column(name = "STCD")
    private String stcd;

    @Column(name = "STCD8")
    private String stcd8;

    @Column(name = "STNM")
    private String stnm;

    @Column(name = "STTP")
    private String sttp;

    @Column(name = "RVNM")
    private String rvnm;

    @Column(name = "PROTOCOL")
    private String protocol;
}
