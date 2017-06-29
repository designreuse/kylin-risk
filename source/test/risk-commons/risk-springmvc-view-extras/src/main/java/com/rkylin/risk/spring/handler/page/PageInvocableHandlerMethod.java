package com.rkylin.risk.spring.handler.page;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.View;

/**
 * Created by tomalloc on 16-8-20.
 */
public class PageInvocableHandlerMethod extends InvocableHandlerMethod {

  private HttpStatus responseStatus;

  private String responseReason;
  private HandlerMethodReturnValueHandlerComposite returnValueHandlers;

  public PageInvocableHandlerMethod(HandlerMethod handlerMethod) {
    super(handlerMethod);
  }

  /**
   * Invokes the method and handles the return value through one of the configured {@link
   * HandlerMethodReturnValueHandler}s.
   *
   * @param webRequest the current request
   * @param mavContainer the ModelAndViewContainer for this request
   * @param providedArgs "given" arguments matched by type (not resolved)
   */
  public void invokeAndHandle(ServletWebRequest webRequest,
      ModelAndViewContainer mavContainer, Object... providedArgs) throws Exception {

    Object returnValue = invokeForRequest(webRequest, mavContainer, providedArgs);
    setResponseStatus(webRequest);

    if (returnValue == null) {
      if (isRequestNotModified(webRequest)
          || hasResponseStatus()
          || mavContainer.isRequestHandled()) {
        mavContainer.setRequestHandled(true);
        return;
      }
    } else if (StringUtils.hasText(this.responseReason)) {
      mavContainer.setRequestHandled(true);
      return;
    }

    mavContainer.setRequestHandled(false);
    try {
      this.returnValueHandlers.handleReturnValue(
          returnValue, getReturnValueType(returnValue), mavContainer, webRequest);
    } catch (Exception ex) {
      if (logger.isTraceEnabled()) {
        logger.trace(getReturnValueHandlingErrorMessage("Error handling return value", returnValue),
            ex);
      }
      throw ex;
    }
  }

  /**
   * Set the response status according to the {@link ResponseStatus} annotation.
   */
  private void setResponseStatus(ServletWebRequest webRequest) throws IOException {
    if (this.responseStatus == null) {
      return;
    }
    if (StringUtils.hasText(this.responseReason)) {
      webRequest.getResponse().sendError(this.responseStatus.value(), this.responseReason);
    } else {
      webRequest.getResponse().setStatus(this.responseStatus.value());
    }
    // to be picked up by the RedirectView
    webRequest.getRequest().setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, this.responseStatus);
  }

  /**
   * Does the given request qualify as "not modified"?
   *
   * @see ServletWebRequest#checkNotModified(long)
   * @see ServletWebRequest#checkNotModified(String)
   */
  private boolean isRequestNotModified(ServletWebRequest webRequest) {
    return webRequest.isNotModified();
  }

  /**
   * Does this method have the response status instruction?
   */
  private boolean hasResponseStatus() {
    return (this.responseStatus != null);
  }

  private String getReturnValueHandlingErrorMessage(String message, Object returnValue) {
    StringBuilder sb = new StringBuilder(message);
    if (returnValue != null) {
      sb.append(" [type=").append(returnValue.getClass().getName()).append("]");
    }
    sb.append(" [value=").append(returnValue).append("]");
    return getDetailedErrorMessage(sb.toString());
  }
}
