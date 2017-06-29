package com.rkylin.risk.service.resteasy.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Created by tomalloc on 16-12-15.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonObjectMapperFinder implements ContextResolver<ObjectMapper> {

  private ObjectMapper objectMapper;

  public JacksonObjectMapperFinder(ObjectMapper objectMapper){
    this.objectMapper=objectMapper;
  }

  @Override public ObjectMapper getContext(Class<?> type) {
    return objectMapper;
  }
}
