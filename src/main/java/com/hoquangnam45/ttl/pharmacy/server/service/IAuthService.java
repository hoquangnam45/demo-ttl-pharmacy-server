package com.hoquangnam45.ttl.pharmacy.server.service;

import java.util.function.Supplier;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.google.inject.ImplementedBy;
import com.hoquangnam45.ttl.pharmacy.server.constant.Role;
import com.hoquangnam45.ttl.pharmacy.server.dto.Authentication;
import com.hoquangnam45.ttl.pharmacy.server.service.impl.AuthService;

@ImplementedBy(AuthService.class)
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.ENCODED)
public interface IAuthService {
  @WebMethod
  Authentication<String, Role> checkAuthentication(String username, String password);

  <T> void requireAuthenticated(Runnable runnable);

  <T> T requireAuthenticatedF(Supplier<T> supplier);
}
