package com.hoquangnam45.ttl.pharmacy.server.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "product", schema = "pharmacy")
public class ProductEntity {
  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @Column(name = "description")
  private String description;

  @Column(name = "name")
  private String name;

  @JoinTable(name = "product_type_association", schema = "pharmacy", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "product_type_id"))
  @ManyToMany(fetch = FetchType.LAZY)
  private List<ProductTypeEntity> productTypes;

  @OneToOne
  @JoinColumn(name = "base_unit_id")
  private UnitEntity baseUnit;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.productId")
  private List<UnitConversionEntity> unitConversions;

  @Column(name = "created_at", nullable = false)
  @CreationTimestamp
  private Timestamp createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Timestamp updatedAt;
}
