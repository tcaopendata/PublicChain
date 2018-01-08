package com.gomsang.lab.publicchain.datas.blockchain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gyeongrok Kim on 2017-08-19.
 */


public class TransactionResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("result")
    @Expose
    private SendEthTransaction result;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public SendEthTransaction getResult() {
        return result;
    }

    public void setResult(SendEthTransaction result) {
        this.result = result;
    }

}