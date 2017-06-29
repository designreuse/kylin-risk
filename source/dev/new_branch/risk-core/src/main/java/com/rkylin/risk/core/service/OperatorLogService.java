package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Operatorlog;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by 201508240185 on 2015/8/26.
 */
public interface OperatorLogService {
  /*
  根据OperLog查找操作日志列表
   */
  List<Operatorlog> query(Operatorlog log, DateTime endTime);

  Operatorlog insert(Short operatorid, String username, String operateobj, String operatedes);
}
