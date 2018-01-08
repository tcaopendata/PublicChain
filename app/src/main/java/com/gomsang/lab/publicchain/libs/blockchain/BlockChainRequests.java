package com.gomsang.lab.publicchain.libs.blockchain;

import com.gomsang.lab.publicchain.datas.blockchain.SendTransactionResponse;
import com.gomsang.lab.publicchain.datas.blockchain.Transaction;
import com.gomsang.lab.publicchain.datas.blockchain.TransactionResponse;

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
    Call<Transaction> getTransaction(@Body JSONObject body); //디플리케티ㅡ

    @POST
    @Headers({"Content-Type: application/json"})
    Call<SendTransactionResponse> sendTransaction(@Body RequestBody body); //디플리케이트 예정

    @POST
    Call<TransactionResponse> requestNewAccount(@Body RequestBody body);

}