package com.gomsang.lab.publicchain.datas;

/**
 * Created by Gyeongrok Kim on 2017-08-09.
 */

public class SignatureData {
    public String txid;
    public String campaignUUID;
    public String signerToken;
    public String message;
    public String status;
    public double value;

    public Long signTime;

    public SignatureData() {
    }


    public SignatureData(String campaignUUID, String signerToken, String message, double value) {
        this.campaignUUID = campaignUUID;
        this.signerToken = signerToken;
        this.message = message;
        this.value = value;

        this.signTime = System.currentTimeMillis();
        this.status = "pending";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSignTime() {
        return signTime;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setSignTime(Long signTime) {
        this.signTime = signTime;
    }

    public String getCampaignUUID() {
        return campaignUUID;
    }

    public void setCampaignUUID(String campaignUUID) {
        this.campaignUUID = campaignUUID;
    }

    public String getSignerToken() {
        return signerToken;
    }

    public void setSignerToken(String signerToken) {
        this.signerToken = signerToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }
}
