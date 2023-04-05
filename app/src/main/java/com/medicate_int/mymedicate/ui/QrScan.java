package com.medicate_int.mymedicate.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.Result;
import com.medicate_int.mymedicate.module.CacheHelper;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScan extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView zXingScannerView;
    CacheHelper statics;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);
        getSupportActionBar().hide();
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent();
        intent.putExtra("card_num",result.getText().trim());
        setResult(5,intent);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent.putExtra("card_num","");
        this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        statics = new CacheHelper(getApplicationContext());
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