package com.hoquangnam45.ttl.pharmacy.server.di;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.WebServiceContext;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.hoquangnam45.ttl.pharmacy.server.module.PharmacyModule;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.server.AbstractMultiInstanceResolver;
import com.sun.xml.ws.api.server.WSWebServiceContext;
import com.sun.xml.ws.api.server.WSEndpoint;

public class GuiceManagedInstanceResolver<T> extends AbstractMultiInstanceResolver<T> {
  private static Injector injector;

  public GuiceManagedInstanceResolver(Class<T> clazz) {
    super(clazz);
  }

  @Override
  public T resolve(Packet request) {
    return injector.getInstance(clazz);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void start(WSWebServiceContext wsWebServiceContext, WSEndpoint endpoint) {
    super.start(wsWebServiceContext, endpoint);
    synchronized (GuiceManagedInstanceResolver.class) {
      if (injector == null) {
        List<Module> modules = new ArrayList<Module>() {
          {
            add(new PharmacyModule());
          }
        };
        modules.add(new AbstractModule() {
          @Override
          protected void configure() {
            bind(WebServiceContext.class).toInstance(wsWebServiceContext);
          }
        });

        injector = Guice.createInjector(modules);
      }
    }
  }
}
