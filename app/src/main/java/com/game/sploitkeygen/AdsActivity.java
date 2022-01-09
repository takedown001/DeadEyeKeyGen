 package com.game.sploitkeygen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.game.sploitkeygen.HomeActivity.deviceid;
import static com.game.sploitkeygen.config.Phome;
import static com.game.sploitkeygen.config.TAG_DEVICEID;
import static com.game.sploitkeygen.config.TAG_KEY;

 public class AdsActivity extends AppCompatActivity {

    Handler handler = new Handler();
    Connection jsonParserString = new Connection();
    private RewardedInterstitialAd mRewardedAd;
    private TextView couter;
    private final String TAG = "MainActivity";
    AdRequest adRequest;
    private TextView keyshow ;
    private Button Donate,Copy;
    private ImageView tg;
    private AdView AdmobView;
    private InterstitialAd mInterstitialAd;
     public AdsActivity() {
     }
     private native String KeyGen();
     private LinearLayout banner;

     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        keyshow = findViewById(R.id.username);
        couter = findViewById(R.id.counter);
        tg =findViewById(R.id.telegram);
        banner = findViewById(R.id.bottombanner);
        Donate =findViewById(R.id.donate);
        Copy =findViewById(R.id.copy);
        AdmobView = new AdView(this);
        adRequest = new AdRequest.Builder().build();
        new CountDownTimer(getduration(),1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                long sec = (millisUntilFinished / 1000);
                NumberFormat f = new DecimalFormat("00");
                couter.setText(f.format(sec));
                if(sec == 25){
                   loadinter();
                }
                if(sec == 8) {
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

         Copy.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (mInterstitialAd != null) {
                     mInterstitialAd.show(AdsActivity.this);
                     setClipboard(AdsActivity.this,keyshow.getText().toString());
                 } else {
                     setClipboard(AdsActivity.this,keyshow.getText().toString());
                 }
             }
         });
        showadmob();
     }

    public String time () throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String time1 = "23:59";
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        Date date1 = format.parse(time1);
        Date date2 = format.parse(currentTime);
        long millis = date1.getTime() - date2.getTime();
        int hours = (int) (millis / (1000 * 60 * 60));
        int mins = (int) ((millis / (1000 * 60)) % 60);

       return hours + " Hrs : " + mins+" Min";
    }
     private void setClipboard(Context context, String text) {
         if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
             android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
             clipboard.setText(text);
         } else {
             android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
             android.content.ClipData clip = android.content.ClipData.newPlainText("Copied key", text);
             clipboard.setPrimaryClip(clip);
         }
     }
//
//private void showinterfb(){
//    interstitialAd = new com.facebook.ads.InterstitialAd(this, "IMG_16_9_APP_INSTALL#968761180358361_1013304622570683");
//    // Create listeners for the Interstitial Ad
//    InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
//        @Override
//        public void onInterstitialDisplayed(Ad ad) {
//            // Interstitial ad displayed callback
//            Log.e(TAG, "Interstitial ad displayed.");
//        }
//
//        @Override
//        public void onInterstitialDismissed(Ad ad) {
//            // Interstitial dismissed callback
//            Log.e(TAG, "Interstitial ad dismissed.");
//        }
//
//        @Override
//        public void onError(Ad ad, AdError adError) {
//            // Ad error callback
//            Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
//        }
//
//        @Override
//        public void onAdLoaded(Ad ad) {
//            // Interstitial ad is loaded and ready to be displayed
//            Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
//            // Show the ad
//            interstitialAd.show();
//        }
//
//        @Override
//        public void onAdClicked(Ad ad) {
//            // Ad clicked callback
//            Log.d(TAG, "Interstitial ad clicked!");
//        }
//
//        @Override
//        public void onLoggingImpression(Ad ad) {
//            // Ad impression logged callback
//            Log.d(TAG, "Interstitial ad impression logged!");
//        }
//    };
//
//    // For auto play video ads, it's recommended to load the ad
//    // at least 30 seconds before it is shown
//    interstitialAd.loadAd(
//            interstitialAd.buildLoadAdConfig()
//                    .withAdListener(interstitialAdListener)
//                    .build());
//}
//
//    private void showFbAd(){
//    fb = new com.facebook.ads.AdView(this,"IMG_16_9_APP_INSTALL#968761180358361_1013302512570894", AdSize.BANNER_HEIGHT_50);
//    banner.addView(fb);
//    fb.loadAd();
//
//    }

     private void showadmob() {

         AdmobView.setAdUnitId("ca-app-pub-2563787493982341/4728518891");
         AdmobView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
         AdmobView.setAdListener(new AdListener() {
             @Override
             public void onAdClosed() {
                 super.onAdClosed();
             }

             @Override
             public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                 super.onAdFailedToLoad(loadAdError);
                 //   showFbAd();
             }

             @Override
             public void onAdOpened() {
                 super.onAdOpened();
             }

             @Override
             public void onAdLoaded() {
                 super.onAdLoaded();
             }

             @Override
             public void onAdClicked() {
                 super.onAdClicked();
             }

             @Override
             public void onAdImpression() {
                 super.onAdImpression();
             }
         });
         AdmobView.loadAd(adRequest);
         banner.addView(AdmobView);

     }


     @Override
     protected void onPause() {
         super.onPause();
         if(AdmobView!=null){
             AdmobView.pause();
         }
     }

     public void loadinter(){
    InterstitialAd.load(this,"ca-app-pub-2563787493982341/2767542293", adRequest,
            new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                    mInterstitialAd = interstitialAd;
            //      Log.i(TAG, "onAdLoaded");
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    // Handle the error
             //       showinterfb();
                    Log.i(TAG, loadAdError.getMessage());
                    mInterstitialAd = null;
                }
            });
}



     private void getkey() {


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
                                         if (mRewardedAd != null) {
                                             Activity activityContext = AdsActivity.this;
                                             mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                                 @Override
                                                 public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        if(key.equals("0")) {
                                                            try {
                                                                new AlertDialog.Builder(AdsActivity.this)
                                                                        .setTitle("Daily Limit Reached")
                                                                        .setMessage("Due To Increasing Number Of Users Our Server Are Overloading With Requests So To Counter That We Have Limited Free Key Creation To 3 Per Day.\n" +
                                                                                "Generate Your Next 3 Keys in "+ time())
                                                                        .setCancelable(false)
                                                                        .setPositiveButton("ok", (dialog, which) -> finish()).show();
                                                            } catch (ParseException e) {
                                                                e.printStackTrace();
                                                            }

                                                        }else {
                                                            couter.setTextSize(15);
                                                            couter.setText("Copy Your Key ");
                                                            keyshow.setText(key);
                                                        }
                                                 }
                                             });
                                         } else {
                                             if(key.equals("0")) {
                                                 try {
                                                     new AlertDialog.Builder(AdsActivity.this)
                                                             .setTitle("Daily Limit Reached")
                                                             .setMessage("Due To Increasing Number Of Users Our Server Are Overloading With Requests So To Counter That We Have Limited Free Key Creation To 3 Per Day.\n" +
                                                                     "Generate Your Next 3 Keys in "+ time())
                                                             .setCancelable(false)
                                                             .setPositiveButton("ok", (dialog, which) -> finish()).show();
                                                 } catch (ParseException e) {
                                                     e.printStackTrace();
                                                 }

                                             }else {
                                                 couter.setTextSize(15);
                                                 couter.setText("Copy Your Key ");
                                                 keyshow.setText(key);
                                             }
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
                                        "ca-app-pub-2563787493982341/9439927705",
                adRequest,  new RewardedInterstitialAdLoadCallback() {
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




