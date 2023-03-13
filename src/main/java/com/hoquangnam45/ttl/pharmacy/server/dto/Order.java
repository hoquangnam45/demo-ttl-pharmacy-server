package com.hoquangnam45.ttl.pharmacy.server.dto;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
  private String id;
  private Date orderDate;
  private String eventId;
  private BigDecimal price;

  private OrderItem[] orderItems;
}
