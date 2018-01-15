package com.gomsang.lab.publicchain.datas.opendata;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

@Root
public class Body {
    @ElementArray(name = "items")
    private Item[] items;

    @Element(name = "numOfRows")
    private int numOfRows;

    @Element(name = "pageNo")
    private int pageNo;

    @Element(name = "totalCount")
    private int totalCount;

    public void setItems(Item[] items) {
        this.items = items;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Item[] getItems() {
        return items;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
