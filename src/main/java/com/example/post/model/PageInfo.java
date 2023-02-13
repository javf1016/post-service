package com.example.post.model;

import lombok.Data;

@Data
public class PageInfo {

    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public PageInfo(int currentPage, int pageSize, long totalElements, int totalPages) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

}