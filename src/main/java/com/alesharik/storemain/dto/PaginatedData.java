package com.alesharik.storemain.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PaginatedData<T> {
    private final int draw;
    private final long recordsTotal;
    private final long recordsFiltered;
    private final List<T> data;

    public <Q> PaginatedData(int draw, Page<Q> page, Function<Q, T> mapper) {
        this.draw = draw;
        this.recordsTotal = page.getTotalElements();
        this.recordsFiltered = page.getTotalElements();
        this.data = page.getContent().stream().map(mapper).collect(Collectors.toList());
    }
}
