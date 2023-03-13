package com.hoquangnam45.ttl.pharmacy.server.repo.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;
import com.hoquangnam45.ttl.pharmacy.server.dto.Paging;
import com.hoquangnam45.ttl.pharmacy.server.entity.ProductEntity;
import com.hoquangnam45.ttl.pharmacy.server.entity.ProductTypeEntity;
import com.hoquangnam45.ttl.pharmacy.server.entity.UnitEntity;
import com.hoquangnam45.ttl.pharmacy.server.repo.IProductRepo;

public class ProductRepo implements IProductRepo {
  private final SessionFactory sessionFactory;

  @Inject
  public ProductRepo(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public List<ProductEntity> getProductPage(int page, int pageSize) {
    return sessionFactory.getCurrentSession()
        .createQuery("FROM ProductEntity", ProductEntity.class)
        .setFirstResult((page - 1) * pageSize)
        .setMaxResults(pageSize)
        .list();
  }

  @Override
  public int getTotalPage(int pageSize) {
    int count = ((Long) sessionFactory.getCurrentSession()
        .createQuery("SELECT COUNT(*) FROM ProductEntity")
        .uniqueResult()).intValue();
    return (count / pageSize) + (count % pageSize > 0 ? 1 : 0);
  }

  @Override
  public void removeProduct(String id) {
    sessionFactory.getCurrentSession().createQuery("DELETE FROM ProductEntity p WHERE p.id = :id")
        .setParameter("id", id)
        .executeUpdate();
  }

  @Override
  public Optional<ProductEntity> getProduct(String id) {
    return Optional.ofNullable(sessionFactory.getCurrentSession().get(ProductEntity.class, id));
  }

  @Override
  public ProductEntity insertProduct(ProductEntity product) {
    product.setId(UUID.randomUUID().toString());
    sessionFactory.getCurrentSession().save(product);
    return product;
  }

  @Override
  public void updateProduct(ProductEntity product) {
    sessionFactory.getCurrentSession().save(product);
  }

  @Override
  public List<ProductTypeEntity> getAllProductTypes() {
    return sessionFactory.getCurrentSession().createQuery("FROM ProductTypeEntity", ProductTypeEntity.class).list();
  }

  @Override
  public List<UnitEntity> getAllUnitTypes() {
    return sessionFactory.getCurrentSession().createQuery("FROM UnitEntity", UnitEntity.class).list();
  }

  @Override
  public Optional<UnitEntity> getUnitEntity(String id) {
    return sessionFactory.getCurrentSession().createQuery("FROM UnitEntity WHERE id=:id", UnitEntity.class)
        .setParameter("id", id).uniqueResultOptional();
  }

  @Override
  public Paging<ProductEntity> searchProduct(String term, int page, int pageSize) {
    String condition = null;
    term = term.trim();
    if (StringUtils.isNotBlank(term)) {
      condition = "FROM ProductEntity p WHERE p.name LIKE '" + term + "%' OR p.description LIKE '" + term + "%'";
    } else {
      condition = "FROM ProductEntity";
    }
    String countQuery = "SELECT COUNT(*) " + condition;
    String getQuery = condition;
    int totalResultCount = ((Long) sessionFactory.getCurrentSession()
        .createQuery(countQuery)
        .uniqueResult()).intValue();
    if (totalResultCount == 0) {
      return Paging.<ProductEntity>builder()
          .currentPage(0)
          .totalPage(0)
          .datas(new ProductEntity[] {})
          .build();
    }
    int totalPage = (totalResultCount / pageSize) + (totalResultCount % pageSize > 0 ? 1 : 0);
    int currentPage = 0;
    if (page > totalPage) {
      currentPage = totalPage;
    } else {
      currentPage = page;
    }
    return Paging.<ProductEntity>builder()
        .currentPage(currentPage)
        .totalPage(totalPage)
        .datas(sessionFactory.getCurrentSession()
            .createQuery(getQuery, ProductEntity.class)
            .setFirstResult((currentPage - 1) * pageSize)
            .setMaxResults(pageSize)
            .list()
            .toArray(new ProductEntity[] {}))
        .build();
  }
}
