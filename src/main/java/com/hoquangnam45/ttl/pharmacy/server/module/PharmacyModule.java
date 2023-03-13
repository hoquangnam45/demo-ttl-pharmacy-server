package com.hoquangnam45.ttl.pharmacy.server.module;

import com.google.inject.AbstractModule;
import com.hoquangnam45.ttl.pharmacy.common.module.ComponentScanModule;
import com.hoquangnam45.ttl.pharmacy.server.mapper.IProductMapper;

public class PharmacyModule extends AbstractModule {
  private static final String servicePackage = "com.hoquangnam45.ttl.pharmacy.server.service";

  @Override
  public void configure() {
    install(new SessionFactoryModule());
    install(new ComponentScanModule(servicePackage, false));
    bind(IProductMapper.class).toInstance(IProductMapper.INSTANCE);
  }
}
