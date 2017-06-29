package com.rkylin.risk.credit.service;

import com.rkylin.gateway.dto.RealTimeDto;
import com.rkylin.gateway.enumtype.TransCode;
import com.rkylin.gateway.service.CreditService;
import com.rkylin.risk.commons.enumtype.CreditProductType;
import javax.annotation.Resource;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * 鹏元api Created by tomalloc on 16-7-28.
 */
abstract class PYApi<T> extends DefaultCreditProductApi<T> {
  @Value("${credit.py.key}")
  private String pyKey;
  @Value("${credit.py.busiNo}")
  private String busiNo;
  @Value("${credit.py.orgNo}")
  private String orgNo;

  @Setter
  private String remark = "风险管理部";
  //运营管理部

  /**
   * 查询证件类型
   */
  protected static final int ID_TYPE = 0;

  @Resource
  protected CreditService creditService;

  protected abstract TransCode transCode();

  protected void setBasicInfo(RealTimeDto baseDto) {
    baseDto.setSysNo(busiNo);
    baseDto.setTransCode(transCode().getTransCode());
    baseDto.setOrgNo(orgNo);
    baseDto.setSignType(SignType.MD5.code());
    String singMessage = baseDto.sign(pyKey);
    baseDto.setSignMsg(singMessage);
  }

  protected CreditProductType productType() {
    return CreditProductType.PY;
  }

  protected String module() {
    return transCode().getTransCode();
  }

  /**
   * 签名类型
   */
  private enum SignType {
    MD5(1);
    private int code;

    SignType(int code) {
      this.code = code;
    }

    public int code() {
      return this.code;
    }
  }
}
