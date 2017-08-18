package com.gomsang.lab.publicchain.libs.blockchain;

import com.gomsang.lab.publicchain.datas.blockchain.SendTransactionResponse;
import com.gomsang.lab.publicchain.datas.blockchain.Transaction;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Gyeongrok Kim on 2017-08-19.
 */

public interface BlockChainRequests {
    @POST("eth_getTransactionByHash")
    Call<Transaction> getTransaction(@Body JSONObject body);

    @POST("eth_sendTransaction")
    @Headers({"Content-Type: application/json"})
    Call<SendTransactionResponse> sendTransaction(@Body RequestBody body);
}