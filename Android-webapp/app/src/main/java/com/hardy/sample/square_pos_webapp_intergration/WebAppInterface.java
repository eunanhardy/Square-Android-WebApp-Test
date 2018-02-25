package com.hardy.sample.square_pos_webapp_intergration;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.squareup.sdk.pos.ChargeRequest;
import com.squareup.sdk.pos.PosClient;
import com.squareup.sdk.pos.PosSdk;

import static com.squareup.sdk.pos.CurrencyCode.GBP;

/**
 * Created by Eunan on 24/02/2018.
 */


public class WebAppInterface {
    public static final int CHARGE_REQUEST_CODE = 1;
    private PosClient posClient;
    Context mContext;
    Activity mActivity;

    WebAppInterface(Context context, Activity activity){
        mContext = context;
        mActivity = activity;
    }

    @JavascriptInterface
    public void makePayment(String amountstr){
        int amount = Integer.valueOf(amountstr.replace(".",""));
        Log.d("debug-amount-value",String.valueOf(amount));
        posClient = PosSdk.createClient(mContext,"CLIENT-APPLICATION-ID");
        // Remember to register your SHA-1 Key with the dashboard
        ChargeRequest request = new ChargeRequest.Builder(amount,GBP).build();

        try{
            Intent intent = posClient.createChargeIntent(request);
            mActivity.startActivityForResult(intent,CHARGE_REQUEST_CODE);
        }catch(ActivityNotFoundException e){
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void showToast(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_LONG).show();
    }





}
