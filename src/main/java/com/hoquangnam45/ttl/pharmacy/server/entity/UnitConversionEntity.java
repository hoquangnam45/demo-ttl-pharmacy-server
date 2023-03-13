package com.hoquangnam45.ttl.pharmacy.server.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.common.base.Objects;

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
@Table(name = "product_unit_conversion", schema = "pharmacy")
public class UnitConversionEntity {
  @EmbeddedId
  private UnitConversionId id;

  @Column(name = "conversion_factor")
  private BigDecimal conversionFactor;

  @Column(name = "listing_price")
  private BigDecimal listingPrice;

  @Embeddable
  @Getter
  public static final class UnitConversionId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "unit_id", nullable = false)
    private String unitId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((unitId == null) ? 0 : unitId.hashCode());
      result = prime * result + ((productId == null) ? 0 : productId.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      UnitConversionId other = (UnitConversionId) obj;
      return Objects.equal(other.getUnitId(), unitId) && Objects.equal(other.getProductId(), productId);
    }
  }
}
