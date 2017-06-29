package com.rkylin.risk.credit.report;

/**
 * Created by tomalloc on 16-6-28.
 */
public class Stream<T> {
  private T t;

  public Stream(T t) {
    this.t = t;
  }

  public T getData() {
    return t;
  }
}
