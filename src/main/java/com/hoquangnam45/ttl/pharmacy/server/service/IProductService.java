package com.hoquangnam45.ttl.pharmacy.server.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.google.inject.ImplementedBy;
import com.hoquangnam45.ttl.pharmacy.server.dto.Paging;
import com.hoquangnam45.ttl.pharmacy.server.dto.Product;
import com.hoquangnam45.ttl.pharmacy.server.service.impl.ProductService;

@ImplementedBy(ProductService.class)
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.ENCODED)
public interface IProductService {
  @WebMethod
  Paging<Product> getProducts(int page, int pageSize);

  @WebMethod
  Paging<Product> searchProduct(String term, int page, int pageSize);

  @WebMethod
  void removeBatch(String[] productIds);

  @WebMethod
  Product[] insertBatch(Product[] insertedProducts);

  @WebMethod
  Product[] updateBatch(Product[] updatedProducts);

  @WebMethod
  String[] getProductTypes();

  @WebMethod
  String[] getAllUnitTypes();
}
