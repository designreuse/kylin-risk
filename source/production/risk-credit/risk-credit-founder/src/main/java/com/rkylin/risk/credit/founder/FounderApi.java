package com.rkylin.risk.credit.founder;

import com.rkylin.risk.credit.founder.dto.BankCardFactorData;
import com.rkylin.risk.credit.founder.dto.BankCardFactorRequestParam;
import com.rkylin.risk.credit.founder.dto.IDCardQueryData;
import com.rkylin.risk.credit.founder.dto.IDCardQueryParam;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by tomalloc on 16-6-30.
 */
public interface FounderApi {

  /**
   * 银行卡四要素和三要素验证
   */
  @POST("UService.do") Call<BankCardFactorData> verifyBankCard(@Query("appid") String appid,
      @Query("service") String service, @Body BankCardFactorRequestParam param);

  /**
   * 身份证
   */
  @POST("UService.do") Call<IDCardQueryData> verifyIDCard(@Query("appid") String appid,
      @Query("service") String service, @Body IDCardQueryParam param);
}
