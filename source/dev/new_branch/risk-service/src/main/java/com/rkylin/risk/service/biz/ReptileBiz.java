package com.rkylin.risk.service.biz;

import com.Rop.api.domain.Detail;
import com.Rop.api.domain.Result;
import java.util.List;
import java.util.Map;

/**
 * Created by lina on 2016-8-5.
 */
public interface ReptileBiz {
  /**
   * 发起爬虫审核接口
   *
   * @param type 审核类型，1为个人；2为机构
   * @param name 1类型时为个人名称，2类型时为法人姓名
   * @param identity 1类型时为个人身份证号，2类型时为法人身份证号码
   * @param cellphone 1类型时为个人电话号码，2类型法人电话
   * @param orgname 1类型时为null，2类型时为机构名称
   * @param orgidentity 1类型时为null，2类型时为组织机构代码
   * @param checkorderid 工作流ID
   */
  void requestVerifyReptile(int type, String name, String identity, String cellphone,
      String orgname, String orgidentity, String checkorderid);

  /**
   * 根据审核编号查询汇总信息接口
   *
   * @param checkorderid 工作流ID
   */
  Map pullVerifyResult(String checkorderid, String querytype);

  /**
   * 查询爬虫细节数据接口
   *
   * @param checkorderid 审核编号
   * @param type 类型
   * @param querytype 查询类型：1-个人；2-机构
   */
  List<String> pullVerifyDetail(String checkorderid, String querytype, int type);

  Long verifyReptile(int type, String name, String identity, String cellphone, int timeout);

  List<Result> getReptileList(Long verifyId);

  List<Detail> getReptileDetail(Long verifyId, int type);
}
