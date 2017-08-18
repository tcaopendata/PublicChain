package com.gomsang.lab.publicchain.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.DialogExportBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Gyeongrok Kim on 2017-08-19.
 */

public class ExportDialog extends Dialog {


    private Context context;
    private DialogExportBinding binding;

    private ArrayList<String> txids;

    public ExportDialog(Context context, ArrayList<String> txids) {
        super(context);
        this.context = context;
        this.txids = txids;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_export, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        binding.exportTextView.setMovementMethod(new ScrollingMovementMethod());
        loadExport();

        binding.exportButton.setOnClickListener((View view) ->
                Toast.makeText(context, "[export completed]\n" +
                                writeToFile("SIGNATURES-" + System.currentTimeMillis(), binding.exportTextView.getText().toString()),
                        Toast.LENGTH_SHORT).show()
        );
    }

    public void loadExport() {
        String exportText = "signatures export data\n----------------------\n";
        for (String str : txids) {
            exportText = exportText + str + "\n";
        }
        exportText = exportText + "---------------------\nend of signatures";
        binding.exportTextView.setText(exportText);
    }

    public String writeToFile(String fileName, String body) {
        FileOutputStream fos = null;

        try {
            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/publicchain/");

            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.e("ALERT", "could not create the directories");
                }
            }
            final File myFile = new File(dir, fileName + ".txt");

            if (!myFile.exists()) {
                myFile.createNewFile();
            }

            fos = new FileOutputStream(myFile);
            fos.write(body.getBytes());
            fos.close();
            return myFile.getAbsolutePath();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}
