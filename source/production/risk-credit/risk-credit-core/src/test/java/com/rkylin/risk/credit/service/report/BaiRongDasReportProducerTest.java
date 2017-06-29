package com.rkylin.risk.credit.service.report;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tomalloc on 16-8-24.
 */
public class BaiRongDasReportProducerTest {
  @Test
  public void runFailTest() {
    String testJson =
        "{\"swift_number\":\"3000152_20160824165010_1660\",\"code\":600000,\"product\":"
            + "{\"api_status\":{\"code\":\"0001\",\"description\":\"调用成功,无数据\",\"serial_no\""
            + ":\"2e7b85d108f146f6baf24ae71983d7db\",\"status\":true},\"data\":{\"info\":"
            + "{\"identity_code\":\"420123198109173752\",\"identity_name\":\"杜勇\"},\"result\":"
            + "\"not_found\",\"status\":false},\"costTime\":2009},\"flag\":{\"flag_edulevel\":1}}";
    BaiRongDasReportProducer baiRongDasReportProducer = new BaiRongDasReportProducer(testJson);
    System.out.println(baiRongDasReportProducer.run());
  }

  @Test
  public void runTest() {
    String testJson =
        "{\"swift_number\":\"3000152_20160823185212_8245\",\"code\":600000,\"product\":"
            + "{\"api_status\":{\"code\":\"0000\",\"description\":\"调用成功\",\"serial_no\":"
            + "\"b83d15dc103e4a7ca83e5e1ba1169674\",\"status\":true},\"data\":{\"info\":"
            + "{\"educationDegree\":\"专科\",\"enrolDate\":\"20120901\",\"graduate\":\"广州大学\""
            + ",\"graduateTime\":\"2016\",\"identity_code\":\"44122619921110401X\","
            + "\"identity_name\":\"张煜明\",\"photo\":\"BBAhEEEECF/9k=\",\"specialityName\":"
            + "\"艺术设计(动漫设计)\",\"studyResult\":\"毕业\",\"studyStyle\":\"普通\"},"
            + "\"result\":\"match\",\"status\":true},\"costTime\":3311},\"flag\":"
            + "{\"flag_edulevel\":1}}";
    BaiRongDasReportProducer baiRongDasReportProducer = new BaiRongDasReportProducer(testJson);
    assertThat(baiRongDasReportProducer.run().size()).isGreaterThan(2);
  }
}
