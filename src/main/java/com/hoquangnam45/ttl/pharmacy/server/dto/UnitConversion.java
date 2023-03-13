package com.hoquangnam45.ttl.pharmacy.server.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@Setter
public class UnitConversion {
  private String unitId;
  private String productId;
  private BigDecimal conversionFactor;
  private BigDecimal listingPrice;
}
