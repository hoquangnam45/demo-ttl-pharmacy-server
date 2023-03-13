package com.hoquangnam45.ttl.pharmacy.server.dto;

import com.hoquangnam45.ttl.pharmacy.server.constant.AuthenticateStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@Setter
public class Authentication<T, K> {
  private String username;
  private T credential;
  private K[] roles;
  private boolean authenticated;
  private AuthenticateStatus status;
}
