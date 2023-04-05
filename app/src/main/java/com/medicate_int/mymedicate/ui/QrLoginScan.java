package com.medicate_int.mymedicate.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrLoginScan extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView zXingScannerView;
    CacheHelper statics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zXingScannerView = new ZXingScannerView(this);
        zXingScannerView.setBackgroundColor(getResources().getColor(R.color.main));
        setContentView(zXingScannerView);
        getSupportActionBar().hide();
        statics = new CacheHelper(this);
    }

    @Override
    public void handleResult(Result result) {
        statics.savePrefsData("login_qr",result.getText().trim());

        startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }
}