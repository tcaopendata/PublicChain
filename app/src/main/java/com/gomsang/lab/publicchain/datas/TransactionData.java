package com.gomsang.lab.publicchain.datas;

/**
 * Created by Gyeongrok Kim on 2017-08-19.
 */

public class TransactionData {
    private String signer;
    private String campaign;
    private String message;

    public TransactionData(){

    }

    public TransactionData(String signer, String campaign, String message) {
        this.signer = signer;
        this.campaign = campaign;
        this.message = message;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
