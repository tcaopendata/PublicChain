package com.gomsang.lab.publicchain.libs.opendata;

import com.gomsang.lab.publicchain.datas.opendata.RceptData;
import com.google.common.util.concurrent.ListenableFuture;
;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by laino on 2018. 1. 15..
 */

public interface OpenDataApi {
    @GET("getRecentRceptList")
    ListenableFuture<RceptData> requestData(@Query("numOfRows") int numOfRows,
                                            @Query("pageNo") int pageNo,
                                            @Query("bill_name") String bill_name,
                                            @Query("ServiceKey") String key);
}
