package com.hoquangnam45.ttl.pharmacy.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@Setter
public class Paging<T> {
  private int currentPage;
  private int totalPage;
  private T[] datas;
}
