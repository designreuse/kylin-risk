package com.rkylin.risk.service.resteasy.component;

import java.io.IOException;
import java.net.URI;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.UriBuilder;
import org.jboss.resteasy.specimpl.ResteasyUriBuilder;

/**
 * 使HttpMethod支持url query parm,取消resteasy 默认的url encode
 * FIXME url matrixParam 待处理
 * Created by tomalloc on 16-11-3.
 */
public class URLRequestDecodeFilter implements ClientRequestFilter {
  @Override public void filter(ClientRequestContext requestContext) throws IOException {
    URI uri = requestContext.getUri();
    String uriPath=uri.getPath();
    int queryParamBeginIndex=uriPath.indexOf("?");
    if(queryParamBeginIndex==-1){
      return;
    }
    String path=uriPath.substring(0,queryParamBeginIndex);
    String query=uriPath.substring(queryParamBeginIndex+1);
    String originQuery=uri.getQuery();
    if(originQuery!=null){
      if(query!=null){
        query+="&"+originQuery;
      }else{
        query=originQuery;
      }
    }
    UriBuilder uriBuilder=ResteasyUriBuilder.fromUri(path)
        .fragment(uri.getFragment())
        .scheme(uri.getScheme())
        .port(uri.getPort())
        .schemeSpecificPart(uri.getSchemeSpecificPart())
        .userInfo(uri.getUserInfo())
        .replaceQuery(query)
        .host(uri.getHost());
    requestContext.setUri(uriBuilder.build());
  }

  public static void main(String[] args) {
    URI uri=ResteasyUriBuilder.fromUri("qapi/run.php?act%3dsys%26mod%3dgetFirstArea").queryParam("platid",2).scheme(
        "http").host("testapi.mitou.cn").build();
  }
}
