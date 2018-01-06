package com.gomsang.lab.publicchain.libs.blockchain;

import android.util.Log;

import com.gomsang.lab.publicchain.datas.blockchain.SendTransactionResponse;
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

public class BlockChain {
    static public BlockChainRequests getBlockchainModel() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_BLOCKCHAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(BlockChainRequests.class);
    }

    public Call<SendTransactionResponse> sendTransaction(String from, String to, String data) {
        try {
            JSONObject param = new JSONObject();
            param.put("from", from);
            param.put("to", to);
            param.put("gas", 400000);
            param.put("gasPrice", "0x9184e72a000");
            param.put("value", "0");
            //param.put("data", "0x" + toHex(data));

            JSONObject req = new JSONObject();
            req.put("jsonrpc", "2.0");
            req.put("method", "eth_sendTransaction");
            req.put("params", new JSONArray().put(param));
            Log.d("parameter", req.toString());

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), req.toString());
            return BlockChain.getBlockchainModel().sendTransaction(body);
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
