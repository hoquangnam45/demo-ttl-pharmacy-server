package com.hoquangnam45.ttl.pharmacy.server.repo;

import java.util.List;
import java.util.Optional;

import com.google.inject.ImplementedBy;
import com.hoquangnam45.ttl.pharmacy.server.dto.Paging;
import com.hoquangnam45.ttl.pharmacy.server.entity.ProductEntity;
import com.hoquangnam45.ttl.pharmacy.server.entity.ProductTypeEntity;
import com.hoquangnam45.ttl.pharmacy.server.entity.UnitEntity;
import com.hoquangnam45.ttl.pharmacy.server.repo.impl.ProductRepo;

@ImplementedBy(ProductRepo.class)
public interface IProductRepo {
  Optional<ProductEntity> getProduct(String id);

  List<ProductEntity> getProductPage(int page, int pageSize);

  int getTotalPage(int pageSize);

  void updateProduct(ProductEntity product);

  void removeProduct(String id);

  ProductEntity insertProduct(ProductEntity product);

  List<ProductTypeEntity> getAllProductTypes();

  List<UnitEntity> getAllUnitTypes();

  Optional<UnitEntity> getUnitEntity(String id);

  Paging<ProductEntity> searchProduct(String term, int page, int pageSize);
}
