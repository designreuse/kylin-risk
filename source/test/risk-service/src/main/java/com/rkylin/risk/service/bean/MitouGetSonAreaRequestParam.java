package com.rkylin.risk.service.bean;

import com.rkylin.risk.service.resteasy.ClientEncoded;
import com.rkylin.risk.service.resteasy.EncryptParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author qiuxian
 * @create 2016-11-04 14:26
 **/
@Setter
@Getter
public class MitouGetSonAreaRequestParam implements EncryptParam,ClientEncoded {
  private Integer areaId;
}
