package com.hoquangnam45.ttl.pharmacy.server.repo;

import com.google.inject.ImplementedBy;
import com.hoquangnam45.ttl.pharmacy.server.entity.UserEntity;
import com.hoquangnam45.ttl.pharmacy.server.repo.impl.UserRepo;

@ImplementedBy(UserRepo.class)
public interface IUserRepo {
  UserEntity getByUsername(String username);
}
