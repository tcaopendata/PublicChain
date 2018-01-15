package com.gomsang.lab.publicchain.datas.opendata;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Header {
    @Element(name = "resultCode")
    private int resultCode;

    @Element(name = "resultMsg")
    private String resultMsg;

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }
}
