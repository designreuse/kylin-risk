package com.rkylin.risk.kie.spring;

import org.kie.api.builder.KieScanner;
import org.kie.api.builder.KieScannerFactoryService;
import org.kie.internal.utils.KieService;
import org.kie.scanner.RiskKieRepositoryScannerImpl;

/**
 * Created by tomalloc on 16-8-10.
 */
public class RiskKieScannerFactoryServiceImpl implements KieService, KieScannerFactoryService {
  @Override public Class getServiceInterface() {
    return KieScannerFactoryService.class;
  }

  @Override public KieScanner newKieScanner() {
    return new RiskKieRepositoryScannerImpl();
  }
}
