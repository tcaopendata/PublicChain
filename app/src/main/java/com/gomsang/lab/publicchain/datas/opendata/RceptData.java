package com.gomsang.lab.publicchain.datas.opendata;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import java.time.LocalDate;

/**
 * Created by laino on 2018. 1. 15..
 */

@Root
public class RceptData {
    @Element
    private Header header;

    @Element
    private Body body;

    public RceptData response; // ?

    public static RceptData failed() {
        return new RceptData();
    }
}

@Root
class Header {
    @Element
    private int resultCode;

    @Element
    private String resultMsg;

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }
}

@Root
class Body {
    @ElementArray
    private Item[] items;

    @Element
    private int numOfRows;

    @Element
    private int pageNo;

    @Element
    private int totalCount;
}

@Root
class Item {
    @Element
    private String billId;

    @Element
    private String billName;

    @Element
    private int billNo;

    @Element
    private LocalDate proposeDt;

    @Element
    private String proposerKind;
}