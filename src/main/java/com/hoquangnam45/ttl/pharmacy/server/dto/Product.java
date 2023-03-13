package com.hoquangnam45.ttl.pharmacy.server.dto;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Product {
  private String id;
  private String name;
  private String description;
  private String[] productTypes;
  private UnitConversion[] unitConversions;
  private String baseUnit;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}
