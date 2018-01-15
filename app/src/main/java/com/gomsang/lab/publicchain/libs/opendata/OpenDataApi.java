package com.gomsang.lab.publicchain.libs.opendata;

import com.gomsang.lab.publicchain.datas.opendata.Response;
import com.google.common.util.concurrent.ListenableFuture;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by laino on 2018. 1. 15..
 */

public interface OpenDataApi {

    @GET("getRecentRceptList?ServiceKey=f7OvLcyF%2BqIo4PH%2FzqTrpSVNtrFF98C1iLUhJ4xuOF5N0BuwObQQ%2Fhgp9GfSu%2Fz9HjdQjYJFcvaxnKDPU%2BNQLg%3D%3D")
    ListenableFuture<Response> requestData(
                                           @Query("numOfRows") int numOfRows,
                                           @Query("pageNo") int pageNo,
                                           @Query("bill_name") String bill_name);
}
