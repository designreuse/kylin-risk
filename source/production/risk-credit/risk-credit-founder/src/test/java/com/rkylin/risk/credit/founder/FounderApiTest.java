package com.rkylin.risk.credit.founder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rkylin.risk.credit.founder.dto.BankCardFactorData;
import com.rkylin.risk.credit.founder.dto.BankCardFactorRequestParam;
import com.rkylin.risk.credit.founder.dto.BankCardFactorUltimateResult;
import com.rkylin.risk.credit.founder.dto.IDCardQueryData;
import com.rkylin.risk.credit.founder.dto.IDCardQueryParam;
import com.rkylin.risk.credit.founder.dto.VerifyResult;
import com.rkylin.risk.credit.founder.jackson.DesEncryptor;
import com.rkylin.risk.credit.founder.jackson.Encryptor;
import com.rkylin.risk.credit.founder.jackson.JacksonConverterFactory;
import com.rkylin.risk.credit.founder.jackson.deserialize.DateTimeDeserializer;
import com.rkylin.risk.credit.founder.jackson.deserialize.EnumVerifyTypeDeserializer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-6-30.
 */
@Slf4j
public class FounderApiTest {
  private static final String BASEURL = "http://115.182.232.120/nuapi/";
  private static final String APPID = "EAF84163984CF7C";
  private static final String FOUR_SERVICE = "qsiyss";
  private static final String THREE_SERVICE = "qsyss";
  private static final String DES_KEY = "03E6F6A3CC4E1DD6EAF84163984CF7C9";

  private static FounderApi founderApi;

  @BeforeClass
  public static void beforeClass() {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(VerifyResult.class, new EnumVerifyTypeDeserializer());
    module.addDeserializer(DateTime.class, new DateTimeDeserializer());
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(module);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    Encryptor encryptor = new DesEncryptor(DES_KEY.getBytes(StandardCharsets.UTF_8));
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASEURL)
        .addConverterFactory(JacksonConverterFactory.create(objectMapper, encryptor))
        .build();
    founderApi = retrofit.create(FounderApi.class);
  }

  private void verifyBankCard(BankCardFactorRequestParam param) {
    String service = FOUR_SERVICE;
    if (param.getBankMobile() == null || "".equals(param.getBankMobile().trim())) {
      service = THREE_SERVICE;
    }
    Call<BankCardFactorData> call = founderApi.verifyBankCard(APPID, service, param);
    try {
      Response<BankCardFactorData> bodyResponse = call.execute();
      BankCardFactorData body = bodyResponse.body();
      log.info("body={}", body.toString());
      assertThat(body).isNotNull();
      assertThat(param.getIdCard()).isEqualTo(body.getIdCard());
      BankCardFactorUltimateResult ultimateResult = body.getResult().getData().getResult();
      assertThat(param.getIdCard()).isEqualTo(ultimateResult.getIdCard());
      assertThat(ultimateResult.getVerifyResult()).isEqualTo(VerifyResult.T);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 三要素测试
   */
  @Test
  public void verifyThreeBankCardTest() {
    BankCardFactorRequestParam param = new BankCardFactorRequestParam();
    param.setName("吉洋鑫");
    param.setAccountNo("6212260200101309629");
    param.setIdCard("131002199408044212");
    verifyBankCard(param);
  }

  /**
   * 四要素测试
   */
  @Test
  public void verifyFourBankCardTest() {
    BankCardFactorRequestParam param = new BankCardFactorRequestParam();
    param.setName("吉洋鑫");
    param.setAccountNo("6212260200101309629");
    param.setIdCard("131002199408044212");
    param.setBankMobile("13691275161");
    verifyBankCard(param);
  }

  /**
   * 银行卡查询
   */
  @Test
  public void verifyIDCardTest() {
    IDCardQueryParam param = new IDCardQueryParam();
    param.setIdCard("131002199408044212");
    param.setName("吉洋鑫");

    String service = "qfzs";
    Call<IDCardQueryData> call = founderApi.verifyIDCard(APPID, service, param);
    try {
      Response<IDCardQueryData> bodyResponse = call.execute();
      IDCardQueryData body = bodyResponse.body();
      log.info("body={}", body.toString());
      assertThat(body).isNotNull();
      assertThat(body.getName()).isEqualTo(param.getName());
      assertThat(body.getIdCard()).isEqualTo(param.getIdCard());

      byte[] images = DatatypeConverter.parseBase64Binary(body.getResult().getData().getPhoto());
      Path p = Paths.get(System.getProperty("java.io.tmpdir")).resolve(body.getIdCard() + ".jpg");
      Files.write(p, images);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
