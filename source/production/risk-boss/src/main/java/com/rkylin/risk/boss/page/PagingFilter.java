package com.rkylin.risk.boss.page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import com.rkylin.risk.boss.biz.PagingBiz;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.exception.RiskPageException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * DataTables的分页后台实现
 *
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/8/31 version: 1.0
 */
@Slf4j
public class PagingFilter extends OncePerRequestFilter {
  @Setter
  private Map<String, String> urlSqlIdMapping;

  @Resource
  private PagingBiz pagingBiz;

  @Setter
  private ObjectMapper objectMapper;

  /**
   * 是否启用格式化
   */
  @Setter
  private boolean indentOutput;

  private final Map<Class<?>, JsonSerializer<?>> serializers =
      new LinkedHashMap<>();

  private final Map<Class<?>, JsonDeserializer<?>> deserializers =
      new LinkedHashMap<>();

  @Override
  protected void initFilterBean() throws ServletException {
    if (urlSqlIdMapping == null) {
      urlSqlIdMapping = Maps.newConcurrentMap();
    }
    if (objectMapper == null) {
      objectMapper = new ObjectMapper();
    }
    objectMapper.configure(SerializationFeature.INDENT_OUTPUT, indentOutput);

    if (!this.serializers.isEmpty() || !this.deserializers.isEmpty()) {
      SimpleModule module = new SimpleModule();
      addSerializers(module);
      addDeserializers(module);
      objectMapper.registerModule(module);
    }
  }

  private <T> void addSerializers(SimpleModule module) {
    for (Map.Entry<Class<?>, JsonSerializer<?>> entry : this.serializers.entrySet()) {
      Class<?> type = entry.getKey();
      module.addSerializer((Class<? extends T>) type, (JsonSerializer<T>) entry.getValue());
    }
  }

  private <T> void addDeserializers(SimpleModule module) {
    for (Map.Entry<Class<?>, JsonDeserializer<?>> entry : this.deserializers.entrySet()) {
      Class<?> type = entry.getKey();
      module.addDeserializer((Class<T>) type, (JsonDeserializer<? extends T>) entry.getValue());
    }
  }

  private void writeObject(DataTableResultDTO resultDTO, HttpServletResponse response)
      throws JsonProcessingException {
    PrintWriter writer = null;
    try {
      writer = response.getWriter();
      String result = objectMapper.writeValueAsString(resultDTO);
      writer.write(result);
      writer.flush();
    } catch (IOException e) {
      log.info(e.getMessage(), e);
    } finally {
      IOUtils.closeQuietly(writer);
    }
  }

  private Map<String, Object> getParmMap(HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    HashMap<String, Object> map = new HashMap<>();
    Map<String, String[]> orimap = request.getParameterMap();
    for (Map.Entry<String, String[]> entry : orimap.entrySet()) {
      String key = entry.getKey();
      String[] value = entry.getValue();
      if (value.length > 1) {
        map.put(key, value);
      } else {
        map.put(key, value[0]);
      }
    }
    if (auth == null) {
      map.put("nullproducts", "-1");
    } else {
      map.put("products", auth.getProducts());
      if (auth.getProducts() != null && auth.getProducts().length > 0) {
        for (String product : auth.getProducts()) {
          if ("-1".equals(product)) {
            map.remove("products");
          }
        }
      } else {
        map.put("nullproducts", "-1");
      }
    }
    return map;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String queryPath = request.getServletPath();
    String sqlId = urlSqlIdMapping.get(queryPath);
    if (StringUtils.isNoneBlank(sqlId)) {
      Map<String, Object> map = getParmMap(request);

      Object starts = map.get("start");
      Object lengths = map.get("length");
      Object draws = map.get("draw");
      log.info("sqlid={},start={},length={},draw={}", sqlId, starts, lengths, draws);

      DataTableResultDTO resultDTO = null;

      try {
        if (draws == null && starts == null && lengths == null) {
          resultDTO = new DataTableResultDTO();
          List result = pagingBiz.query(sqlId, map);
          resultDTO.setData(result);
        } else if (draws != null && starts != null && lengths != null) {
          int start = 0;
          int length = 10;
          String draw = "";
          try {
            start = Integer.parseInt((String) starts);
            length = Integer.parseInt((String) lengths);
            draw = (String) draws;
          } catch (Exception e) {
            throw new RiskPageException("分页参数错误");
          }

          List result = pagingBiz.queryPage(sqlId, start, length, map);
          long totalSize = 0;
          if (result instanceof Page) {
            Page page = (Page) result;
            totalSize = page.getTotal();
          } else {
            totalSize = result.size();
          }
          DataTablePageResultDTO pageResultDTO = new DataTablePageResultDTO();
          pageResultDTO.setDraw(draw);
          pageResultDTO.setRecordsTotal(totalSize);
          pageResultDTO.setRecordsFiltered(totalSize);
          pageResultDTO.setData(result);

          resultDTO = pageResultDTO;
        } else {
          throw new RiskPageException("分页参数错误");
        }
      } catch (RiskPageException e) {
        if (resultDTO == null) {
          resultDTO = new DataTableResultDTO();
        }
        resultDTO.setError(e.getMessage());
        log.error("分页参数错误", e);
      } catch (Exception e) {
        if (resultDTO == null) {
          resultDTO = new DataTableResultDTO();
        }
        resultDTO.setError("服务器错误");
        log.error("分页错误", e);
      }
      writeObject(resultDTO, response);
    } else {
      filterChain.doFilter(request, response);
    }
  }

  public void setSerializers(JsonSerializer<?>... serializers) {
    if (serializers != null) {
      for (JsonSerializer<?> serializer : serializers) {
        Class<?> handledType = serializer.handledType();
        if (handledType == null || handledType == Object.class) {
          throw new IllegalArgumentException(
              "Unknown handled type in " + serializer.getClass().getName());
        }
        this.serializers.put(serializer.handledType(), serializer);
      }
    }
  }

  public void setDeserializers(Map<Class<?>, JsonDeserializer<?>> deserializers) {
    if (deserializers != null) {
      this.deserializers.putAll(deserializers);
    }
  }
}
