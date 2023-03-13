package com.hoquangnam45.ttl.pharmacy.server.component;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.inject.Inject;

public class TransactionManager {
  @Inject
  private SessionFactory sessionFactory;

  public void runInTransaction(Consumer<Session> consumer) {
    runInTransactionF(session -> {
      consumer.accept(session);
      return session;
    });
  }

  public <T> T runInTransactionF(Function<Session, T> fn) {
    Session session = null;
    Transaction tx = null;
    boolean outerMostTransaction = false;
    try {
      session = sessionFactory.getCurrentSession();
      tx = session.getTransaction();
      if (!tx.isActive()) {
        tx.begin();
        outerMostTransaction = true;
      }
      T ret = fn.apply(session);
      if (outerMostTransaction) {
        tx.commit();
      }
      return ret;
    } catch (Exception e) {
      if (outerMostTransaction) {
        e.printStackTrace();
        Optional.ofNullable(tx).ifPresent(item -> item.rollback());
      }
      throw e;
    } finally {
      if (outerMostTransaction) {
        Optional.ofNullable(session).ifPresent(item -> item.close());
      }
    }
  }
}
