package com.hardy.sample.square_pos_webapp_intergration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainWebActivity extends AppCompatActivity {
    public static final int CHARGE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_web);
        WebView webView = (WebView)findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this,this),"Android");
        webView.loadUrl("http://192.168.1.64:3000/sq");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("debug",String.valueOf(requestCode));
        if(requestCode == WebAppInterface.CHARGE_REQUEST_CODE){
            Log.d("debug-data",data.toString());
            if(data == null){
                Log.d("debug","Error payment");
                return;
            }

            if(resultCode == Activity.RESULT_OK){
                showDialog("Square","Payment Made",null);
            }

        }
    }



    private void showDialog(String title, String message, DialogInterface.OnClickListener listener) {
        Log.d("MainActivity", title + " " + message);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", listener)
                .show();
    }
}
