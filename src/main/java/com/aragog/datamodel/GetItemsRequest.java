package com.aragog.datamodel;

public class GetItemsRequest {
    private String searchKey;
    
    private int pageSize;
    
    public GetItemsRequest(String searchKey, int pageSize) {
        this.searchKey = searchKey;
        this.pageSize = pageSize;
    }
    
    public String getSearchKey() {
        return searchKey;
    }

    public int getPageSize() {
        return pageSize;
    }
}
