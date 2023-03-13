package com.hoquangnam45.ttl.pharmacy.server.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.google.inject.ImplementedBy;
import com.hoquangnam45.ttl.pharmacy.server.dto.Order;
import com.hoquangnam45.ttl.pharmacy.server.service.impl.OrderService;

@ImplementedBy(OrderService.class)
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IOrderService {
  @WebMethod
  void createOrder(Order order);

  @WebMethod
  void transactOrder(Order order);

  @WebMethod
  void cancelOrder(Order order);
}
