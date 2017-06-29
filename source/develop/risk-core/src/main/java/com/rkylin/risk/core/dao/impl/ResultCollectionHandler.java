package com.rkylin.risk.core.dao.impl;

/**
 * Created by tomalloc on 16-7-11.
 */
public interface ResultCollectionHandler<T> {
  void handle(Object obj);
  T result();
}
