package com.gomsang.lab.publicchain.libs.opendata;

import android.util.Log;

import com.gomsang.lab.publicchain.datas.opendata.Response;

import java.util.concurrent.ExecutionException;

import retrofit2.HttpException;

import static com.gomsang.lab.publicchain.libs.opendata.OpenDataPool.KEY;

/**
 * Created by laino on 2018. 1. 15..
 */

public class OpenDataReturn {
    public static Response requestRceptData(int numOfRows, int pageNo, String bill_name) {
        try {
            return OpenDataPool.getOpenDataApi()
                    .requestData(numOfRows, pageNo, bill_name)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failed();
        }
    }
}
