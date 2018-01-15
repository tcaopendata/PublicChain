package com.gomsang.lab.publicchain.datas.opendata;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by laino on 2018. 1. 15..
 */

@Root(name = "items")
public class ItemArray {
    @ElementList(inline = true, name = "item")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
