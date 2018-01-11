package com.gomsang.lab.publicchain.libs.blockchain;

import android.util.Log;

import com.gomsang.lab.publicchain.datas.blockchain.TransactionResponse;
import com.gomsang.lab.publicchain.libs.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gyeongrok Kim on 2017-08-19.
 */

public class BlockChainFactory {
    static public BlockChainApi getBlockchainModel() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_BLOCKCHAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(BlockChainApi.class);
    }


    public Call<TransactionResponse> requestNewAccount(String password) {
        try {
            JSONObject req = new JSONObject();
            req.put("jsonrpc", "2.0");
            req.put("method", "personal_newAccount");
            req.put("params", new JSONArray().put(password));
            req.put("id", 10);

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), req.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Call<TransactionResponse> getAccountBalance (String address) {
        try {
            JSONArray params = new JSONArray();
            params.put(address);
            params.put("latest");

            JSONObject req = new JSONObject();
            req.put("jsonrpc", "2.0");
            req.put("method", "eth_getBalance");
            req.put("params", params);
            req.put("id", 10);

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), req.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Call<TransactionResponse> requestUnlockAccount (String address, String password) {
        try {
            JSONArray params = new JSONArray();
            params.put(address);
            params.put(password);
            params.put(300);

            JSONObject req = new JSONObject();
            req.put("jsonrpc", "2.0");
            req.put("method", "personal_unlockAccount");
            req.put("params", params);
            req.put("id", 10);

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), req.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Call<TransactionResponse> requestTransaction (String from, String to, String value) {
        try {
            JSONObject params = new JSONObject();
            params.put("from", from);
            params.put("value", value);
            params.put("to", to);

            JSONObject req = new JSONObject();
            req.put("jsonrpc", "2.0");
            req.put("method", "personal_unlockAccount");
            req.put("params", new JSONArray().put(params));
            req.put("id", 10);

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), req.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    public String toHex(String arg) {
        Log.d("jsonhash", String.format("%x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/))));
        return String.format("%x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }
}
