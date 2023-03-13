package com.hoquangnam45.ttl.pharmacy.server.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
  ADMIN("admin"), USER("user");

  private final String name;

  public static final Role fromValue(String value) {
    for (Role role : Role.values()) {
      if (role.name.equalsIgnoreCase(value.trim())) {
        return role;
      }
    }
    return null;
  }
}
