package com.hoquangnam45.ttl.pharmacy.server.mapper;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.hoquangnam45.ttl.pharmacy.server.dto.Paging;
import com.hoquangnam45.ttl.pharmacy.server.dto.Product;
import com.hoquangnam45.ttl.pharmacy.server.dto.UnitConversion;
import com.hoquangnam45.ttl.pharmacy.server.entity.ProductEntity;
import com.hoquangnam45.ttl.pharmacy.server.entity.ProductTypeEntity;
import com.hoquangnam45.ttl.pharmacy.server.entity.UnitConversionEntity;

@Mapper
public interface IProductMapper {
  IProductMapper INSTANCE = Mappers.getMapper(IProductMapper.class);

  @Mappings({
      @Mapping(target = "baseUnit", source = "baseUnit.id")
  })
  Product map(ProductEntity product);

  default OffsetDateTime fromTimestamp(Timestamp timestamp) {
    return Optional.ofNullable(timestamp).map(item -> OffsetDateTime.ofInstant(item.toInstant(), ZoneId.of("UTC")))
        .orElse(null);
  }

  default String map(ProductTypeEntity type) {
    return type.getId();
  };

  default Paging<Product> mapPage(Paging<ProductEntity> paging) {
    return Paging.<Product>builder()
        .currentPage(paging.getCurrentPage())
        .currentPage(paging.getCurrentPage())
        .datas(
            Stream.of(paging.getDatas()).map(item -> map(item)).collect(Collectors.toList()).toArray(new Product[] {}))
        .build();
  }

  @Mappings({
      @Mapping(target = "baseUnit.id", source = "baseUnit"),
      @Mapping(target = "createdAt", ignore = true),
      @Mapping(target = "updatedAt", ignore = true),
      @Mapping(target = "productTypes", ignore = true),
      @Mapping(target = "unitConversions", ignore = true),
  })
  ProductEntity insertMap(Product product);

  @Mappings({
      @Mapping(target = "unitId", source = "id.unitId", ignore = true),
      @Mapping(target = "productId", source = "id.productId", ignore = true),
  })
  UnitConversion mapUnitConversionEntity(UnitConversionEntity UnitConversionEntity);
}
