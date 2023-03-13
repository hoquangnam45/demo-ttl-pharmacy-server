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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_unit", schema = "pharmacy")
public class UnitEntity {
  @Id
  @Column(name = "id", nullable = false)
  private String id;
}
