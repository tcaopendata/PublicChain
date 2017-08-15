package com.gomsang.lab.publicchain.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.ItemSignatureBinding;
import com.gomsang.lab.publicchain.datas.SignatureData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gyeongrok Kim on 2017-08-16.
 */

public class SignatureAdpater extends BaseAdapter {

    private Context context;
    private ArrayList<SignatureData> signatureDatas = new ArrayList<>();

    public SignatureAdpater(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return signatureDatas.size();
    }

    @Override
    public SignatureData getItem(int i) {
        return signatureDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemSignatureBinding binding = DataBindingUtil.inflate((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                R.layout.item_signature, null, false);
        final SignatureData signatureData = signatureDatas.get(i);

        binding.messageTextView.setText(signatureData.getMessage().length() == 0 ? "no message" : signatureData.getMessage());

        final Date signTime = new Date(signatureData.getSignTime());
        final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        binding.dateTextView.setText(sdf.format(signTime));

        binding.statusTextView.setText(signatureData.getStatus());

        binding.donationTextView.setText(signatureData.getValue() + " ETH");
        return binding.getRoot();
    }

    public void add(SignatureData signatureData) {
        signatureDatas.add(signatureData);
    }
}
