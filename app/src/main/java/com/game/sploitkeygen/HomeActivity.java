package com.game.sploitkeygen;

import android.animation.ArgbEvaluator;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.game.sploitkeygen.Adapter.StoreAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.game.sploitkeygen.data.Model;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import burakustun.com.lottieprogressdialog.LottieDialogFragment;


public class HomeActivity extends AppCompatActivity {

    ViewPager viewPager;
    StoreAdapter adapter;
    private String deviceid;
    //user
    static {
        System.loadLibrary("tersafe2");
    }
    private static final String TAG_DEVICEID = config.TAG_DEVICEID;
    private static final String TAG_SUCCESS = config.TAG_SUCCESS;
    private static final String TAG_STORE = config.TAG_STORE;
    private final Connection jsonParser = new Connection();
    //plan
    private ColorDrawable background;
    private static final String TAG_TITLE = config.TAG_TITLE;
    private static final String TAG_DESC = config.TAG_DESC;
    private static final String TAG_IMG = config.TAG_IMG;
    private static final String url = config.Main +"keygenhome.php";
    private JSONArray jsonarray = null;
    private ArrayList<HashMap<String, String>> offersList;
    private int success;
    private String updateurl;
    private List<Model> Model = new ArrayList<>();
    private String newversion ;
    JSONObject json = new JSONObject();
    Integer[] colors = null;
    AdRequest adRequest;
    String data ;
    final DialogFragment lottieDialog = new LottieDialogFragment().newInstance("loadingdone.json",true);
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    public static native String tg();
    public static native String Donate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        offersList = new ArrayList<>();

        Model = new ArrayList<>();
        AdView adView = new AdView(this);
        adapter = new StoreAdapter(Model, this);
        adView = findViewById(R.id.bottom);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        new OneLoadAllProducts().execute();
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }





    class OneLoadAllProducts extends AsyncTask<String, String, String> {


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(String file_url) {
           lottieDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                        if (success == 1) {
                            for (int i = 0; i < offersList.size(); i++) {
                                Model store = new Model();
                                store.setTitle(offersList.get(i).get(TAG_TITLE));
                                store.setDesc(offersList.get(i).get(TAG_DESC));
                                store.setImage(offersList.get(i).get(TAG_IMG));
                                Model.add(store);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(HomeActivity.this, "SomeThing Went Wrong", Toast.LENGTH_LONG).show();
                        }
                    }


            });
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lottieDialog.setCancelable(false);
            lottieDialog.show(getFragmentManager(),"lol");
            deviceid = Helper.getUniqueId(HomeActivity.this);
        //    lottieDialog.show(getFragmentManager(),"lol");
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject params = new JSONObject();

            try {
                String rq = null;
                params.put(TAG_DEVICEID, deviceid);
                rq = jsonParser.makeHttpRequest(url, params);
                JSONObject ack = new JSONObject(rq);
                String decData = Helper.profileDecrypt(ack.get("Data").toString(), ack.get("Hash").toString());
                if (!Helper.verify(decData, ack.get("Sign").toString(), Connection.publickey)) {
                    Toast.makeText(HomeActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                    return null;
                } else {
                    json = new JSONObject(decData);
                    Log.d("All jsonarray: ", String.valueOf(json));
                    success = json.getInt(TAG_SUCCESS);
                    newversion = json.getString("newversion");
                    data = json.getString("data");
                    updateurl = json.getString("update");
                    // Log.d("test", String.valueOf(success));
                    jsonarray = json.getJSONArray(TAG_STORE);


                    String version = BuildConfig.VERSION_NAME;
                    if (Float.parseFloat(version) < Float.parseFloat(newversion)) {
                        Intent intent = new Intent(HomeActivity.this, AppUpdaterActivity.class);
                        intent.putExtra("newversion", newversion);
                        intent.putExtra("data", data);
                        intent.putExtra("update", updateurl);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject c = jsonarray.getJSONObject(i);
                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<>();
                            map.put(TAG_IMG, c.getString(TAG_IMG));
                            map.put(TAG_TITLE, c.getString(TAG_TITLE));
                            map.put(TAG_DESC, c.getString(TAG_DESC));

                            offersList.add(map);
                        }
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
