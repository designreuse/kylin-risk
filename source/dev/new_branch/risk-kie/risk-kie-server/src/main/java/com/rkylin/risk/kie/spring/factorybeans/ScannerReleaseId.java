package com.rkylin.risk.kie.spring.factorybeans;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.kie.api.builder.ReleaseId;

/**
 * Created by tomalloc on 16-8-10.
 */

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "releaseId")
public class ScannerReleaseId {
  private ReleaseId releaseId;
  private boolean scannerEnabled = false;
  private long scannerInterval = 1000;
  private long delay;
  private String scannerId;
}
