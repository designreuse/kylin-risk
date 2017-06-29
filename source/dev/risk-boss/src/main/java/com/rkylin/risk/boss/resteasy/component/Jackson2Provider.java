package com.rkylin.risk.boss.resteasy.component;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

/**
 * Created by tomalloc on 16-11-1.
 */
@Provider
@Consumes({"application/*+json", "text/json","text/html"})
@Produces({"application/*+json", "text/json","text/html"})
public class Jackson2Provider extends ResteasyJackson2Provider {

  @Override
  protected boolean hasMatchingMediaType(MediaType mediaType)
  {
    if (mediaType != null) {
      // Ok: there are also "xxx+json" subtypes, which count as well
      String subtype = mediaType.getSubtype();

      // [Issue#14]: also allow 'application/javascript'
      return "json".equalsIgnoreCase(subtype) || subtype.endsWith("+json")
          || "javascript".equals(subtype)
          // apparently Microsoft once again has interesting alternative types?
          || "x-javascript".equals(subtype)
          || "x-json".equals(subtype) // [Issue#40]
          || "html".equals(subtype)
          ;
    }
        /* Not sure if this can happen; but it seems reasonable
         * that we can at least produce JSON without media type?
         */
    return true;
  }

}
