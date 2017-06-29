package com.rkylin.risk.credit.report;

import com.rkylin.risk.credit.xml.CreditBaiRongRuleElement;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by tomalloc on 16-6-28.
 */
public class ReportRunner {
  private Source source;
  private LinkedHashSet<Filter> fiters;
  private Slink slink;

  private ReportRunner(CreditBaiRongRuleElement rongRuleElement) {
    this.fiters = new LinkedHashSet<>();
  }

  public ReportRunner create(CreditBaiRongRuleElement rongRuleElement) {
    return new ReportRunner(rongRuleElement);
  }

  public ReportRunner source(Source source) {
    if (this.source != null) {
      throw new RuntimeException("不能同时存在两个source");
    }
    this.source = source;
    return this;
  }

  public ReportRunner filter(Filter filter) {
    this.fiters.add(filter);
    return this;
  }

  public ReportRunner slink(Slink slink) {
    if (this.source != null) {
      throw new RuntimeException("不能同时存在两个slink");
    }
    this.slink = slink;
    return this;
  }

  //TODO
  public void run() {
    List<Stream> streamList = source.flow();
    for (Iterator<Filter> filterIterator = fiters.iterator(); filterIterator.hasNext();) {
      //TODO
    }
    this.slink.write(streamList);
  }
}
