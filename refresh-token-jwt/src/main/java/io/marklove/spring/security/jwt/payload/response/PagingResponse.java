package io.marklove.spring.security.jwt.payload.response;

import io.marklove.spring.security.jwt.payload.response.base.BaseMetaData;

import java.util.List;

/**
 * @author ngupq
 */
public class PagingResponse<T> {
    private BaseMetaData metaData;
    private List<T> records;

    public PagingResponse(BaseMetaData metaData, List<T> records){
        this.metaData = metaData;
        this.records = records;
    }

    public BaseMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(BaseMetaData metaData) {
        this.metaData = metaData;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
