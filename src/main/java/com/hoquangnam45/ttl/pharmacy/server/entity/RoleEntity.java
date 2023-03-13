package com.hoquangnam45.ttl.pharmacy.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "role", schema = "pharmacy")
public class RoleEntity {
  @Id
  @Column(name = "id", nullable = false)
  private String id;
}
