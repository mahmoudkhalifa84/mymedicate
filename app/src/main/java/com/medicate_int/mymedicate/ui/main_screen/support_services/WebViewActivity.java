package com.medicate_int.mymedicate.ui.main_screen.support_services;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.R;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    String Sousse = "https://bit.ly/3gbxR56";
    String Hammamet = "https://bit.ly/3fajFaX";
    String Mahdia = "https://bit.ly/30eug0B";
    String Monastir = "https://bit.ly/338p8wL";
    String Djerba = "https://bit.ly/39DghEq";
    String Tabarka = "https://bit.ly/3hNMZG8";
    ImageView ref, home, back;
    int place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        place = getIntent().getIntExtra("place", 0);
        webView = findViewById(R.id.os_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        home = findViewById(R.id.wv_home);
        back = findViewById(R.id.wv_back);
        ref = findViewById(R.id.wv_refe);
        getSupportActionBar().hide();
        findViewById(R.id.wv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WebViewActivity.this, HomeActivity.class));
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Click(2);
            }
        });
        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Click(3);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Click(1);
            }
        });
        loadURL();
    }

    public void Click(int i) {
        switch (i) {
            case 1:
                if (webView.canGoBack()) {
                    webView.goBack();
                }
                break;
            case 2:

                loadURL();
                break;
            case 3:
                webView.reload();

                break;
        }

    }

    private void loadURL() {
        switch (place) {
            case 0:
                webView.loadUrl(Sousse);
                break;
            case 1:
                webView.loadUrl(Hammamet);
                break;
            case 2:
                webView.loadUrl(Monastir);
                break;
            case 3:
                webView.loadUrl(Djerba);
                break;
            case 4:
                webView.loadUrl(Tabarka);
                break;
            case 5:
                webView.loadUrl(Mahdia);
                break;
        }
        Toast toast = Toast.makeText(this, getString(R.string.plz_wait), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else {
            this.finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}

class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }
}