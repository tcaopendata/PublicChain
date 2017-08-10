package com.gomsang.lab.publicchain.datas;

/**
 * Created by Gyeongrok Kim on 2017-08-09.
 */

public class SignatureData {
    public String campaignUUID;
    public String signerToken;
    public String message;

    public Long signTime;

    public SignatureData() {
    }

    public SignatureData(String campaignUUID, String signerToken, String message) {
        this.signTime = System.currentTimeMillis();
        this.campaignUUID = campaignUUID;
        this.signerToken = signerToken;
        this.message = message;
    }

    public Long getSignTime() {
        return signTime;
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
}
