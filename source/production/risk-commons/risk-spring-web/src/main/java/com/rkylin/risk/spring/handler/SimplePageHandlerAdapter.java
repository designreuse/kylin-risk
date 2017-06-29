package com.rkylin.risk.spring.handler;

import com.rkylin.risk.spring.handler.page.DataTableBaseResultDTO;
import com.rkylin.risk.spring.handler.page.DataTablePageResultDTO;
import com.rkylin.risk.spring.handler.page.PageInvocableHandlerMethod;
import com.rkylin.risk.spring.handler.page.PageParam;
import com.rkylin.risk.spring.handler.page.PagingServiceHandler;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.ui.ModelMap;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Created by tomalloc on 16-7-20.
 */
public class SimplePageHandlerAdapter implements HandlerAdapter {
  private List<HttpMessageConverter<?>> messageConverters;
  private ContentNegotiationManager contentNegotiationManager;

  private PagingServiceHandler serviceHandler;

  public void setContentNegotiationManager(ContentNegotiationManager contentNegotiationManager) {
    this.contentNegotiationManager = contentNegotiationManager;
  }

  public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
    this.messageConverters = messageConverters;
  }

  @Override public boolean supports(Object handler) {
    return handler instanceof PageParam;
  }

  private void handleResult(HttpServletResponse response, DataTableBaseResultDTO resultDTO) {

  }

  @Override public ModelAndView handle(HttpServletRequest request, HttpServletResponse response,
      Object handler) throws Exception {
    PageParam pageParam = (PageParam) handler;
    DataTableBaseResultDTO baseResultDTO = null;
    if (pageParam.getErrorDesc() != null) {
      baseResultDTO = new DataTableBaseResultDTO();
      baseResultDTO.setError(pageParam.getErrorDesc().getMessage());
      handleResult(response, baseResultDTO);
      return null;
    }
    List list;
    if (pageParam.isDaemon()) {
      DataTablePageResultDTO pageResultDTO = new DataTablePageResultDTO();
      list =
          serviceHandler.query(pageParam.getSqlID(), pageParam.getStart(), pageParam.getPageSize(),
              pageParam.getRequestParam());
      int size = list.size();
      pageResultDTO.setRecordsFiltered(size);
      pageResultDTO.setRecordsTotal(size);
      pageResultDTO.setDraw(pageParam.getDraw());
      baseResultDTO = pageResultDTO;
    } else {
      baseResultDTO = new DataTableBaseResultDTO();
      list = serviceHandler.query(pageParam.getSqlID(), pageParam.getRequestParam());
    }
    baseResultDTO.setData(list);
    handleResult(response, baseResultDTO);

    ServletWebRequest webRequest = new ServletWebRequest(request, response);

    //WebDataBinderFactory binderFactory = getDataBinderFactory(handlerMethod);
    //ModelFactory modelFactory = getModelFactory(handlerMethod, binderFactory);
    //
    //ServletInvocableHandlerMethod invocableMethod = createInvocableHandlerMethod(handlerMethod);
    //invocableMethod.setHandlerMethodArgumentResolvers(this.argumentResolvers);
    //invocableMethod.setHandlerMethodReturnValueHandlers(this.returnValueHandlers);
    //invocableMethod.setDataBinderFactory(binderFactory);
    //invocableMethod.setParameterNameDiscoverer(this.parameterNameDiscoverer);
    //
    //ModelAndViewContainer mavContainer = new ModelAndViewContainer();
    //mavContainer.addAllAttributes(RequestContextUtils.getInputFlashMap(request));
    //modelFactory.initModel(webRequest, mavContainer, invocableMethod);
    //mavContainer.setIgnoreDefaultModelOnRedirect(this.ignoreDefaultModelOnRedirect);

    //invocableMethod.invokeAndHandle(webRequest, mavContainer);
    //return getModelAndView(mavContainer, modelFactory, webRequest);
    return null;
  }

  private ModelAndView getModelAndView(ModelAndViewContainer mavContainer,
      ModelFactory modelFactory, NativeWebRequest webRequest) throws Exception {

    modelFactory.updateModel(webRequest, mavContainer);
    if (mavContainer.isRequestHandled()) {
      return null;
    }
    ModelMap model = mavContainer.getModel();
    ModelAndView mav = new ModelAndView(mavContainer.getViewName(), model);
    if (!mavContainer.isViewReference()) {
      mav.setView((View) mavContainer.getView());
    }
    if (model instanceof RedirectAttributes) {
      Map<String, ?> flashAttributes = ((RedirectAttributes) model).getFlashAttributes();
      HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
      RequestContextUtils.getOutputFlashMap(request).putAll(flashAttributes);
    }
    return mav;
  }

  @Override public long getLastModified(HttpServletRequest request, Object handler) {
    return -1;
  }

  protected PageInvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handlerMethod) {
    return new PageInvocableHandlerMethod(handlerMethod);
  }
}
