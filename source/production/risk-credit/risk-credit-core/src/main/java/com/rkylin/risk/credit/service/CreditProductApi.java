package com.rkylin.risk.credit.service;

import com.rkylin.risk.credit.service.report.ReportItem;
import java.io.IOException;
import java.util.Map;

/**
 * Created by tomalloc on 16-7-28.
 */
interface CreditProductApi {
  Map<ReportItem, ?> request(CreditRequestParam requestParam) throws IOException;
}
