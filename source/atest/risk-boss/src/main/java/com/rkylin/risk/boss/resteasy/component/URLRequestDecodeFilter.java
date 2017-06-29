package com.rkylin.risk.boss.resteasy.component;

import java.io.IOException;
import java.net.URI;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import org.jboss.resteasy.specimpl.ResteasyUriBuilder;

/**
 * 使HttpMethod支持url query parm,取消resteasy 默认的url encode
 * Created by tomalloc on 16-11-3.
 */
public class URLRequestDecodeFilter implements ClientRequestFilter {
  @Override public void filter(ClientRequestContext requestContext) throws IOException {
    URI uri = requestContext.getUri();
    requestContext.setUri(ResteasyUriBuilder.fromUri(uri.getPath())
        .fragment(uri.getFragment())
        .scheme(uri.getScheme())
        .port(uri.getPort())
        .host(uri.getHost())
        .build());
  }
}
