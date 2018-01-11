package com.gomsang.lab.publicchain.libs.blockchain;

import com.gomsang.lab.publicchain.datas.blockchain.TransactionResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Gyeongrok Kim on 2017-08-19.
 */

public interface BlockChainApi {
    @POST
    Call<TransactionResponse> requestNewAccount(@Body RequestBody body);

    @POST
    Call<TransactionResponse> requestUnlockAccount(@Body RequestBody body);

    @POST
    Call<TransactionResponse> requestTransaction(@Body RequestBody body);

    @POST
    Call<TransactionResponse> getAccountBalance(@Body RequestBody body);
}