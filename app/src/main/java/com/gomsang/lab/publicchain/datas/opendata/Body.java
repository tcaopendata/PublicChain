package com.gomsang.lab.publicchain.datas.opendata;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class Body {
    @Element(name = "items")
    private ItemArray items;

    @Element(name = "numOfRows")
    private int numOfRows;

    @Element(name = "pageNo")
    private int pageNo;

    @Element(name = "totalCount")
    private int totalCount;

    public ItemArray getItems() {
        return items;
    }

    public void setItems(ItemArray items) {
        this.items = items;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
