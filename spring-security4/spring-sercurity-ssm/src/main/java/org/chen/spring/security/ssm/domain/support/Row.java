package org.chen.spring.security.ssm.domain.support;

import java.io.Serializable;

public class Row implements Serializable {

    private static final long serialVersionUID = -2345403372879875867L;
    /**
    * 当前页数
    */
    private Integer currentPage;
    /**
    * 每页显示数据条数
    */
    private Integer pageSize;
    /**
    * 排序字段
    */
    private String sort;
    /**
    * 排序方法 asc desc
    */
    private String order;

    public Row() {
    }

    public Row(Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Row(Integer currentPage, Integer pageSize, String sort, String order) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.sort = sort;
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}