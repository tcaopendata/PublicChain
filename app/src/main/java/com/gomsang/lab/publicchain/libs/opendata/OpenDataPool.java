package com.gomsang.lab.publicchain.libs.opendata;

import retrofit.converter.guava.GuavaOptionalConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.guava.GuavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by laino on 2018. 1. 15..
 */

public class OpenDataPool {
    public static final String URL = "http://openapi.assembly.go.kr/openapi/service/BillInfoService/";
    public static final String KEY = "f7OvLcyF%2BqIo4PH%2FzqTrpSVNtrFF98C1iLUhJ4xuOF5N0BuwObQQ%2Fhgp9GfSu%2Fz9HjdQjYJFcvaxnKDPU%2BNQLg%3D%3D";

    private static OpenDataApi openDataApi = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(GuavaCallAdapterFactory.create())
            .addConverterFactory(GuavaOptionalConverterFactory.create())
            .build()
            .create(OpenDataApi.class);

    private OpenDataPool() {

    }

    public static OpenDataApi getOpenDataApi() {
        return openDataApi;
    }
}

