package com.hoquangnam45.ttl.pharmacy.server;

import javax.xml.ws.Endpoint;

import com.hoquangnam45.ttl.pharmacy.server.service.impl.AuthService;
import com.hoquangnam45.ttl.pharmacy.server.service.impl.ProductService;

public class PharmacyApplicationServer {
  public static void main(String[] args) throws Exception {
    String url = "http://localhost";
    String port = "8080";
    String publishUrl = url + ":" + port;

    Endpoint.publish(publishUrl + "/product", new ProductService());
    Endpoint.publish(publishUrl + "/auth", new AuthService());
  }
}
