package com.hoquangnam45.ttl.pharmacy.server.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.google.inject.Inject;
import com.hoquangnam45.ttl.pharmacy.server.component.TransactionManager;
import com.hoquangnam45.ttl.pharmacy.server.constant.AuthenticateStatus;
import com.hoquangnam45.ttl.pharmacy.server.constant.Role;
import com.hoquangnam45.ttl.pharmacy.server.di.GuiceManaged;
import com.hoquangnam45.ttl.pharmacy.server.dto.Authentication;
import com.hoquangnam45.ttl.pharmacy.server.entity.UserEntity;
import com.hoquangnam45.ttl.pharmacy.server.repo.IUserRepo;
import com.hoquangnam45.ttl.pharmacy.server.service.IAuthService;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletResponse;

@GuiceManaged
@WebService(endpointInterface = "com.hoquangnam45.ttl.pharmacy.server.service.IAuthService")
public class AuthService implements IAuthService {
  @Inject
  private IUserRepo userRepo;
  @Inject
  private TransactionManager transactionManager;
  @Resource
  private WebServiceContext ctx;

  @Override
  public Authentication<String, Role> checkAuthentication(String username, String password) {
    return transactionManager.runInTransactionF(session -> {
      UserEntity user = userRepo.getByUsername(username);
      if (user == null) {
        return new Authentication<String, Role>(username, null, null, false, AuthenticateStatus.NOT_FOUND);
      } else if (!BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified) {
        return new Authentication<String, Role>(username, null, null, false, AuthenticateStatus.WRONG_PASSWORD);
      }
      Role[] roles = user.getRoles().stream().map(item -> item.getId()).map(item -> Role.fromValue(item))
          .collect(Collectors.toList()).toArray(new Role[] {});
      return new Authentication<String, Role>(username, user.getPassword(), roles, true, AuthenticateStatus.SUCCESS);
    });
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> void requireAuthenticated(Runnable runnable) {
    Map<String, List<String>> headers = Optional
        .ofNullable(ctx.getMessageContext().get(MessageContext.HTTP_REQUEST_HEADERS))
        .map(item -> (Map<String, List<String>>) item).orElse(new HashMap<>());
    String username = headers.get("authorization").get(0);
    String password = headers.get("authorization").get(1);
    Authentication<String, Role> authentication = checkAuthentication(username, password);
    if (authentication.getStatus() == AuthenticateStatus.SUCCESS) {
      runnable.run();
    } else {
      HttpServletResponse response = (HttpServletResponse) ctx.getMessageContext().get(MessageContext.SERVLET_RESPONSE);
      try {
        response.sendError(401, "unauthenticated");
      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T requireAuthenticatedF(Supplier<T> supplier) {
    Map<String, List<String>> headers = Optional
        .ofNullable(ctx.getMessageContext().get(MessageContext.HTTP_REQUEST_HEADERS))
        .map(item -> (Map<String, List<String>>) item).orElse(new HashMap<>());
    String username = headers.get("authorization").get(0);
    String password = headers.get("authorization").get(1);
    Authentication<String, Role> authentication = checkAuthentication(username, password);
    if (authentication.getStatus() == AuthenticateStatus.SUCCESS) {
      return supplier.get();
    } else {
      HttpServletResponse response = (HttpServletResponse) ctx.getMessageContext().get(MessageContext.SERVLET_RESPONSE);
      try {
        response.sendError(401, "unauthenticated");
      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
      return null;
    }
  }
}
