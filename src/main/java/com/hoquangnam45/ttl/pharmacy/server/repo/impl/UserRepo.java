package com.hoquangnam45.ttl.pharmacy.server.repo.impl;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;
import com.hoquangnam45.ttl.pharmacy.server.entity.UserEntity;
import com.hoquangnam45.ttl.pharmacy.server.repo.IUserRepo;

public class UserRepo implements IUserRepo {
  private final SessionFactory sessionFactory;

  @Inject
  public UserRepo(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public UserEntity getByUsername(String username) {
    return sessionFactory.getCurrentSession()
        .createQuery("FROM UserEntity AS u WHERE u.username = :username", UserEntity.class)
        .setParameter("username", username)
        .uniqueResult();
  }
}
