 package com.game.sploitkeygen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import static com.game.sploitkeygen.config.Phome;
import static com.game.sploitkeygen.config.TAG_DEVICEID;
import static com.game.sploitkeygen.config.TAG_KEY;

 public class AdsActivity extends AppCompatActivity {


    Handler handler = new Handler();
    Connection jsonParserString = new Connection();
    private ColorDrawable background;
    private AdLoader adLoader;
    private RewardedInterstitialAd mRewardedAd;
    private TextView couter;
    private final String TAG = "MainActivity";
    AdRequest adRequest;
    private TextView keyshow ;
    private int game;
    private Button Donate;
    private ImageView tg;
     private InterstitialAd mInterstitialAd;
     public AdsActivity() {
     }

     private native String KeyGen();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        keyshow = findViewById(R.id.username);
        AdView adView = new AdView(this);
        adView = findViewById(R.id.topadView);
        couter = findViewById(R.id.counter);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        tg =findViewById(R.id.telegram);
        Donate =findViewById(R.id.donate);

        new CountDownTimer(getduration(),1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                long sec = (millisUntilFinished / 1000);
                NumberFormat f = new DecimalFormat("00");
                couter.setText(f.format(sec));
                if(sec == 4){
                    loadinter();
                }
                if(sec == 2) {
                    loadreward();
                }
            }
            @Override
            public void onFinish() {
                keyshow.setVisibility(View.VISIBLE);
               getkey();
                }
        }.start();
        tg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(HomeActivity.tg()));
                startActivity(i);
            }
        });
        Donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(HomeActivity.Donate()));
                startActivity(i);
            }
        });
    }

     public void loadinter(){
    InterstitialAd.load(this,"ca-app-pub-2563787493982341/1798809703", adRequest,
            new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd;
             //       Log.i(TAG, "onAdLoaded");
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    // Handle the error
                    Log.i(TAG, loadAdError.getMessage());
                    mInterstitialAd = null;
                }
            });
}
     private void setClipboard(Context context, String text) {
         if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
             android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
             clipboard.setText(text);
         } else {
             android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
             android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
             clipboard.setPrimaryClip(clip);
         }
     }
     private void getkey() {
         final String  deviceid = Helper.getUniqueId(this);

         class key extends AsyncTask<Void, Void, String> {
             ProgressDialog dialog = new ProgressDialog(AdsActivity.this);
             @Override
             protected void onPreExecute() {
                 super.onPreExecute();
                 dialog.setCancelable(false);
                 dialog.setMessage("Loading Key");
                 if(!AdsActivity.this.isFinishing())
                 {
                     dialog.show();
                 }

             }

             @RequiresApi(api = Build.VERSION_CODES.KITKAT)
             @Override
             protected String doInBackground(Void... voids) {

                 JSONObject params = new JSONObject();

                 String rq = null;
                 try {
                     params.put(TAG_DEVICEID,deviceid);
                     params.put(TAG_KEY,Phome);
                     rq = jsonParserString.makeHttpRequest(KeyGen(), params);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 //returing the response
                 return rq;
             }

             @Override
             protected void onPostExecute(String s) {
                 super.onPostExecute(s);
                 handler.postDelayed(new Runnable() {

                     @Override
                     public void run() {
                         if (dialog != null){
                             dialog.dismiss();
                         }
                         if (s == null || s.isEmpty()) {
                             Toast.makeText(AdsActivity.this, "Server Error", Toast.LENGTH_LONG).show();
                             return;
                         }
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 try {
                                     JSONObject ack = new JSONObject(s);
                                        // Log.d("test", String.valueOf(ack));
                                     String decData = Helper.profileDecrypt(ack.get("Data").toString(), ack.get("Hash").toString());
                                     if (!Helper.verify(decData, ack.get("Sign").toString(), Connection.publickey)) {
                                         Toast.makeText(AdsActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                                         return;
                                     } else {
                                         JSONObject obj = new JSONObject(decData);
                                         String key = obj.getString("2000");
                                         if (mInterstitialAd != null) {
                                             mInterstitialAd.show(AdsActivity.this);
                                         } else {
                                             Log.d("TAG", "The interstitial ad wasn't ready yet.");
                                         }
                                         if (mRewardedAd != null) {
                                             Activity activityContext = AdsActivity.this;
                                             mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                 @Override
                                                 public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                                                     couter.setTextSize(15);
                                                     couter.setText("Your Key is Below");
                                                     keyshow.setText(key);;
                                                 }
                                             });
                                         } else {
                                             couter.setTextSize(15);
                                             couter.setText("Your Key is Below");
                                             keyshow.setText(key);;
                                         }

                                     }
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                 }

                             }
                         });
                     }
                 }, 2000);
             }


         }
         key k = new key();
         k.execute();
     }

    public static native int getduration();
    public void loadreward (){

        RewardedInterstitialAd.load(this,
                        "ca-app-pub-2563787493982341/9010752172",
                new AdRequest.Builder().build(),  new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedInterstitialAd ad) {
                        mRewardedAd = ad;
                        Log.e(TAG, "onAdLoaded");
                    }
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.e(TAG, "onAdFailedToLoad");
                    }
                });
    }

 }




