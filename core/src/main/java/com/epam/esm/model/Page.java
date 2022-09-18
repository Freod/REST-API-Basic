package com.epam.esm.model;

import lombok.Data;

import java.util.Collection;

@Data
public class Page<T> {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private Collection<T> collection;

    public Page(int pageNumber, int pageSize, int totalPages, Collection<T> collection) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.collection = collection;
    }
}
