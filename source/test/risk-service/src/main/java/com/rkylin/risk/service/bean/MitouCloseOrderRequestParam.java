package com.rkylin.risk.service.bean;

import com.rkylin.risk.service.resteasy.ClientEncoded;
import com.rkylin.risk.service.resteasy.EncryptParam;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by chenfumin on 2017/1/13.
 */
@Setter
@Getter
public class MitouCloseOrderRequestParam implements EncryptParam, ClientEncoded {
  private String orderNo;
  private Integer dataSource;
}
