package com.gomsang.lab.publicchain.datas.blockchain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gyeongrok Kim on 2017-08-19.
 */

public class Transaction {
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("nonce")
    @Expose
    private String nonce;
    @SerializedName("blockHash")
    @Expose
    private String blockHash;
    @SerializedName("blockNumber")
    @Expose
    private String blockNumber;
    @SerializedName("transactionIndex")
    @Expose
    private String transactionIndex;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("gas")
    @Expose
    private String gas;
    @SerializedName("gasPrice")
    @Expose
    private String gasPrice;
    @SerializedName("input")
    @Expose
    private String input;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
