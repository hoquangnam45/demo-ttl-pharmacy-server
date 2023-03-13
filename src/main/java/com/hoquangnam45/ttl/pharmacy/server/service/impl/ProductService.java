package com.hoquangnam45.ttl.pharmacy.server.service.impl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import com.google.inject.Inject;
import com.hoquangnam45.ttl.pharmacy.server.component.TransactionManager;
import com.hoquangnam45.ttl.pharmacy.server.di.GuiceManaged;
import com.hoquangnam45.ttl.pharmacy.server.dto.Paging;
import com.hoquangnam45.ttl.pharmacy.server.dto.Product;
import com.hoquangnam45.ttl.pharmacy.server.mapper.IProductMapper;
import com.hoquangnam45.ttl.pharmacy.server.repo.IProductRepo;
import com.hoquangnam45.ttl.pharmacy.server.service.IAuthService;
import com.hoquangnam45.ttl.pharmacy.server.service.IProductService;

@GuiceManaged
@WebService(endpointInterface = "com.hoquangnam45.ttl.pharmacy.server.service.IProductService")
public class ProductService implements IProductService {
  @Resource
  private WebServiceContext ctx;
  @Inject
  private IProductRepo productRepo;
  @Inject
  private IProductMapper productMapper;
  @Inject
  private TransactionManager transactionManager;
  @Inject
  private IAuthService authService;

  @Override
  public Paging<Product> getProducts(int page, int pageSize) {
    return transactionManager.runInTransactionF(session -> searchProduct(null, page, pageSize));
  }

  @Override
  public void removeBatch(String[] productIds) {
    authService.requireAuthenticated(() -> transactionManager
        .runInTransaction(session -> Stream.of(productIds).forEach(productRepo::removeProduct)));
  }

  @Override
  public Product[] insertBatch(Product[] insertedProducts) {
    return authService.requireAuthenticatedF(() -> transactionManager
        .runInTransactionF(session -> Stream.of(insertedProducts)
            .map(productMapper::insertMap)
            .map(productRepo::insertProduct)
            .map(productMapper::map)
            .collect(Collectors.toList()))
        .toArray(new Product[] {}));

  }

  @Override
  public String[] getProductTypes() {
    return transactionManager.runInTransactionF(
        session -> productRepo.getAllProductTypes().stream().map(item -> item.getId()).collect(Collectors.toList()))
        .toArray(new String[] {});
  }

  @Override
  public String[] getAllUnitTypes() {
    return transactionManager.runInTransactionF(
        session -> productRepo.getAllUnitTypes().stream().map(item -> item.getId()).collect(Collectors.toList()))
        .toArray(new String[] {});
  }

  @Override
  public Product[] updateBatch(Product[] updatedProducts) {
    return authService
        .requireAuthenticatedF(() -> transactionManager.runInTransactionF(session -> Stream.of(updatedProducts)
            .map(item -> {
              return productRepo.getProduct(item.getId()).map(entity -> {
                productRepo.getUnitEntity(item.getBaseUnit()).ifPresent(baseUnit -> entity.setBaseUnit(baseUnit));
                entity.setName(item.getName());
                entity.setDescription(item.getDescription());
                return entity;
              }).orElse(null);
            })
            .filter(item -> item != null)
            .map(productMapper::map)
            .collect(Collectors.toList()))
            .toArray(new Product[] {}));
  }

  @Override
  public Paging<Product> searchProduct(String term, int page, int pageSize) {
    return transactionManager
        .runInTransactionF(session -> productMapper.mapPage(productRepo.searchProduct(term, page, pageSize)));
  }
}
