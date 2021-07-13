package io.marklove.spring.security.jwt.payloads.responses.base;

/**
 * @author ngupq
 */
public class BaseMetaData {
    private Integer page;
    private Integer pageSize;
    private Integer totalPage;
    private Integer totalRecords;

    public BaseMetaData(Integer page, Integer pageSize, Integer totalPage, Integer totalRecords) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalRecords = totalRecords;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }
}
