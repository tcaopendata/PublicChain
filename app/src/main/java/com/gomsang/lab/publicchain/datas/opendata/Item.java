package com.gomsang.lab.publicchain.datas.opendata;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.time.LocalDate;

@Root(name = "item")
public class Item {
    @Element(name = "billId")
    private String billId;

    @Element(name = "billName")
    private String billName;

    @Element(name = "billNo")
    private int billNo;

    @Element(name = "proposeDt")
    private String proposeDt;

    @Element(name = "proposerKind")
    private String proposerKind;

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public void setBillNo(int billNo) {
        this.billNo = billNo;
    }

    public void setProposeDt(String proposeDt) {
        this.proposeDt = proposeDt;
    }

    public void setProposerKind(String proposerKind) {
        this.proposerKind = proposerKind;
    }

    public String getBillId() {
        return billId;
    }

    public String getBillName() {
        return billName;
    }

    public int getBillNo() {
        return billNo;
    }

    public String getProposeDt() {
        return proposeDt;
    }

    public String getProposerKind() {
        return proposerKind;
    }
}
