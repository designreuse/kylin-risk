package com.rkylin.risk.spring.handler;

import com.rkylin.risk.spring.handler.page.PageParam;
import com.rkylin.risk.spring.handler.page.PageType;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

/**
 * spring 分页处理 Created by tomalloc on 16-7-20.
 */
public class SimplePageHandlerMapping extends AbstractHandlerMapping {
  @Setter
  private Map<String, UrlSqlConfig> pageSqlMapParam = new LinkedHashMap<>();

  private void handleRequestParam(HttpServletRequest request, Map<String, String> requestParam) {

  }

  @Override protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
    String url = null;
    UrlSqlConfig configParam = pageSqlMapParam.get(url);
    if (configParam == null) {
      return null;
    }

    int pageSize = 0;
    int start = 0;
    boolean bowserDaemon = false;

    String s = request.getParameter("start");
    String p = request.getParameter("length");
    String draw = request.getParameter("draw");
    String errorMessage = null;

    boolean noError = true;
    if (s == null && p == null && draw == null) {
      bowserDaemon = false;
    } else if (s != null && p != null && draw != null) {
      bowserDaemon = true;
      start = Integer.valueOf(s);
      pageSize = Integer.valueOf(p);
    } else {
      noError = false;
      errorMessage = "分页请求参数错误";
    }

    boolean daemon = false;

    if (noError) {
      int serverPageSize = configParam.getMaxPageSize();

      PageType pageType = configParam.getServerDaemon();

      if (pageType == PageType.DEFAULT) {
        if (bowserDaemon) {
          if (pageSize > serverPageSize) {
            noError = false;
            errorMessage = "分页大小超过最大值";
          }
          daemon = true;
        }
      } else if (pageType == PageType.PHYSICAL) {
        if (bowserDaemon) {
          daemon = true;
        } else {
          noError = false;
          errorMessage = "分页方式错误";
        }
        if (noError && pageSize > serverPageSize) {
          noError = false;
          errorMessage = "分页大小超过最大值";
        }
      }
    }

    PageParam pageParam = new PageParam();
    pageParam.setSqlID(configParam.getSqlID());

    pageParam.setStart(start);
    pageParam.setPageSize(pageSize);
    pageParam.setDraw(draw);
    pageParam.setDaemon(daemon);

    if (errorMessage != null) {
      PageParam.ErrorDesc errorDesc = new PageParam.ErrorDesc();
      errorDesc.setMessage(errorMessage);
      pageParam.setErrorDesc(errorDesc);
    }

    Map<String, String> requestParam = new LinkedHashMap<>();
    handleRequestParam(request, requestParam);
    pageParam.setRequestParam(requestParam);

    return pageParam;
  }
}
