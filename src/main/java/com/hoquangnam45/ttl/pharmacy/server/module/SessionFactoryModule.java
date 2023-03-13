package com.hoquangnam45.ttl.pharmacy.server.module;

import javax.persistence.Entity;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.inject.AbstractModule;
import com.hoquangnam45.ttl.pharmacy.common.module.ComponentScanModule;

public class SessionFactoryModule extends AbstractModule {
  private static SessionFactory sessionFactory;

  @Override
  public void configure() {
    bind(SessionFactory.class).toInstance(getInstance());
  }

  public static synchronized SessionFactory getInstance() {
    if (sessionFactory == null) {
      Configuration configuration = new Configuration();
      ComponentScanModule.getClassOfPackage("com.hoquangnam45.ttl.pharmacy", false).stream()
          .filter(item -> item.isAnnotationPresent(Entity.class))
          .forEach(item -> configuration.addAnnotatedClass(item));
      sessionFactory = configuration.configure().buildSessionFactory();
    }
    return sessionFactory;
  }
}
