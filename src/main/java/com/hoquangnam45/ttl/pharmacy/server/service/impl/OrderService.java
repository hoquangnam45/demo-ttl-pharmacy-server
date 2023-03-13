package com.hoquangnam45.ttl.pharmacy.server.service.impl;

import javax.jws.WebService;

import com.hoquangnam45.ttl.pharmacy.server.di.GuiceManaged;
import com.hoquangnam45.ttl.pharmacy.server.dto.Order;
import com.hoquangnam45.ttl.pharmacy.server.service.IOrderService;

@GuiceManaged
@WebService(endpointInterface = "com.hoquangnam45.ttl.pharmacy.server.service.IOrderService")
public class OrderService implements IOrderService {

  @Override
  public void createOrder(Order order) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createOrder'");
  }

  @Override
  public void transactOrder(Order order) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'transactOrder'");
  }

  @Override
  public void cancelOrder(Order order) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cancelOrder'");
  }

}
