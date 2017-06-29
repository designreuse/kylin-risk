package com.rkylin.risk.test.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.core.dto.RestResultDTO;
import com.rkylin.risk.core.exception.model.ErrorDesc;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

import static com.rkylin.risk.core.exception.model.ReturnCodeMetadata.SUCCESS;

/**
 * 执行成功的json增加返回状态码
 *
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 15-1-6 下午4:39 version: 1.0
 */
public class AppendJsonReturnCodeHttpMessageConverter extends MappingJackson2HttpMessageConverter {

  public AppendJsonReturnCodeHttpMessageConverter() {
    super();
  }

  public AppendJsonReturnCodeHttpMessageConverter(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  /**
   * 覆盖官方定义的jackson ObjectMapper配置
   */
  @Override
  protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {
    //如果正常返回则对对象包装，生成统一格式的json数据
    if (object.getClass() != ErrorDesc.class) {
      RestResultDTO restResultDTO = new RestResultDTO();
      restResultDTO.setCode(SUCCESS.getCode());
      restResultDTO.setMessage(SUCCESS.getMessage());
      restResultDTO.setData(object);
      object = restResultDTO;
    }
    super.writeInternal(object, type, outputMessage);
  }
}
