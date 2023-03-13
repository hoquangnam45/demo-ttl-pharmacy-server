package com.hoquangnam45.ttl.pharmacy.server.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderItem {
  private String id;
  private String sellUnit;
  private String productId;
  private Double quantity; // Can be decimal for liquid type
  private BigDecimal price;
  private String eventId;
}
