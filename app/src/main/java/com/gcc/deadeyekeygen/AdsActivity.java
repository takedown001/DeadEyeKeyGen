 package com.gcc.deadeyekeygen;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import burakustun.com.lottieprogressdialog.LottieDialogFragment;

import static com.gcc.deadeyekeygen.AdsActivity.GetKey.TAG_DEVICEID;
import static com.gcc.deadeyekeygen.AdsActivity.GetKey.TAG_KEY;
import static com.gcc.deadeyekeygen.AdsActivity.GetKey.TAG_MSG;
import static com.gcc.deadeyekeygen.Helper.getDeviceId;
import static com.gcc.deadeyekeygen.config.Phome;

 public class AdsActivity extends AppCompatActivity {


    Handler handler = new Handler();
    Connection jsonParserString = new Connection();
    private ColorDrawable background;
    private AdLoader adLoader;
    private InterstitialAd mInterstitialAd;
    private RewardedAd mRewardedAd;
    private TextView couter;
    private final String TAG = "MainActivity";
    AdRequest adRequest;
    private TextInputEditText keyshow ;
    private int game;
    private ImageView TG;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        keyshow = findViewById(R.id.username);
        AdView adView = new AdView(this);
        adView = findViewById(R.id.topadView);
        couter = findViewById(R.id.counter);
        game =getIntent().getIntExtra("game",1) ;
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        TG = findViewById(R.id.tg);

        new CountDownTimer(getduration(),1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                long sec = (millisUntilFinished / 1000);
                NumberFormat f = new DecimalFormat("00");
                couter.setText(f.format(sec));
                if(sec==30){
                    loadreward();
                }
                if (sec==20){
                 loadnative();
                }
                if(sec==10){
                    loadinter();
                }
            }

            @Override
            public void onFinish() {
                keyshow.setVisibility(View.VISIBLE);
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(AdsActivity.this);
                    loadreward();
                    if (game == 1) {
                        getkey();
                    } else if (game == 2) {

                        new GetKey(AdsActivity.this).execute();
                    }
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }
        }.start();


        TG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedAd != null) {
                    Activity activityContext = AdsActivity.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(HomeActivity.tg()));
                            startActivity(i);
                        }
                    });
                } else {
                    Toast.makeText(AdsActivity.this,"Rewards Can't Be Loaded", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


     private void getkey() {
         final String  deviceid = getDeviceId(this);

         class key extends AsyncTask<Void, Void, String> {
         //    final DialogFragment lottieDialog = new LottieDialogFragment().newInstance("loading_state_done.json", true);
             ProgressDialog dialog = new ProgressDialog(AdsActivity.this);
             @Override
             protected void onPreExecute() {
                 super.onPreExecute();

                 dialog.setCancelable(false);
             }

             @RequiresApi(api = Build.VERSION_CODES.KITKAT)
             @Override
             protected String doInBackground(Void... voids) {
                 //creating request handler object
                 //creating request parameters
                 JSONObject params = new JSONObject();

                 String rq = null;
                 try {
                     params.put(TAG_DEVICEID,deviceid);
                     params.put(TAG_KEY,Phome);
                     rq = jsonParserString.makeHttpRequest(config.FreeGen, params);
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
                                                     couter.setTextSize(15);
                                                     couter.setText("Your Key is Below");
                                                     keyshow.setText(key);;
                                                 }
                                             });
                                         } else {
                                             Toast.makeText(AdsActivity.this,"Rewards Can't Be Loaded", Toast.LENGTH_LONG).show();
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

    public void loadnative(){
        adLoader = new AdLoader.Builder(this,
                                "ca-app-pub-9545053006738127/9651168007")
               .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();
                        TemplateView template = findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .build();
        adLoader.loadAd(adRequest);
    }

    public void loadinter(){
        InterstitialAd.load(this,

                                        "ca-app-pub-9545053006738127/7552436519", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


    }
    public static native int getduration();
    public void loadreward (){
        RewardedAd.load(this,
                "ca-app-pub-9545053006738127/6855993019",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });


    }
    public class GetKey extends AsyncTask<String, Void, String> {
        private WeakReference<AdsActivity> weakActivity;
        private ProgressDialog pDialog;
        public static final String TAG_DEVICEID = "2";
        public static final String TAG_KEY = "1";
        JSONParserString jsonParserString = new JSONParserString();
        public static final String TAG_MSG = "8";
        public static final String TAG_TIME = "999";
        Date time = new Date();
        Handler handler = new Handler();
        long reqtime;


        private native String Here();
        public GetKey(AdsActivity activity){
            weakActivity = new WeakReference<>(activity);
            loadreward();
            ProgressDialog dialog = new ProgressDialog(activity);
            dialog.setCancelable(false);
            pDialog = dialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AdsActivity activity = getActivity();
            if (activity == null) {
                return;
            }

            if (getDialog() != null) {
                getDialog().setMessage("Checking your connection ...");
                getDialog().show();
            }
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            final Activity activity = getActivity();
            if (activity == null) {
                return;
            }
            if (getDialog() != null) {
                getDialog().dismiss();
            }

            if(s == null || s.isEmpty()){
                Toast.makeText(activity,"Server Auth error", Toast.LENGTH_LONG).show();
                return;
            }

            if(s.equals("No internet connection")){
                Toast.makeText(activity,s, Toast.LENGTH_LONG).show();
                return;
            }
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                JSONObject obj = new JSONObject(s);
                           //     Log.d("test",obj.getString("2000") );
                                String key = AESUtils.DarKnight.getDecrypted(obj.getString("2000"));
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   if (mRewardedAd != null) {
                                       Activity activityContext = AdsActivity.this;
                                       mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                           @Override
                                           public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                               couter.setTextSize(15);
                                               couter.setText("Your Key is Below");
                                               keyshow.setText(key);
                                           }
                                       });
                                   } else {
                                       Toast.makeText(AdsActivity.this,"Rewards Couldn't Be Loaded", Toast.LENGTH_LONG).show();
                                   }
                               }

                           });
                                Toast.makeText(AdsActivity.this, AESUtils.DarKnight.getDecrypted(obj.getString(TAG_MSG)), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }, 2000);
        }

        @Override
        protected String doInBackground(String... strings) {
            time.setTime(System.currentTimeMillis());
            reqtime = time.getTime();
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put(TAG_DEVICEID, AESUtils.DarKnight.getEncrypted(getUniqueId(getActivity())));
            params.put(TAG_KEY, AESUtils.DarKnight.getEncrypted("9166253127"));
            params.put(TAG_TIME, AESUtils.DarKnight.getEncrypted(String.valueOf(reqtime)));
            String rq = null;
            try {
                rq = jsonParserString.makeHttpRequest(Here(), params);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //returing the response
            return rq;
        }

        private AdsActivity getActivity() {
            return weakActivity.get();
        }

        private ProgressDialog getDialog() {
            return pDialog;
        }

        private PublicKey getPublicKey(byte[] keyBytes) throws Exception {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        }




        private String getUniqueId(Context ctx) {
            String key = (getDeviceName() + Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID) + Build.HARDWARE).replace(" ", "");
            UUID uniqueKey = UUID.nameUUIDFromBytes(key.getBytes());
            return uniqueKey.toString().replace("-", "");
        }


        private String getDeviceName() {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            if (model.startsWith(manufacturer)) {
                return model;
            } else {
                return manufacturer + " " + model;
            }
        }
    }
 }



