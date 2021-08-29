package com.game.sploitkeygen.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.sploitkeygen.AdsActivity;
import com.game.sploitkeygen.R;
import com.game.sploitkeygen.config;
import com.game.sploitkeygen.data.Model;

import java.util.List;

public class StoreAdapter extends PagerAdapter {

    private List<Model> models;
    private LayoutInflater layoutInflater;
    private Context context;
    //store
    private static final String TAG_TITLE = config.TAG_TITLE;
    private static final String TAG_IMG = config.TAG_IMG;

    public StoreAdapter(List<Model> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.ssale, container, false);
        final Model official = models.get(position);
        ImageView imageView;
        TextView title, desc;
        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.stitle);
        desc = view.findViewById(R.id.desc);
        if (official.getImage().contains("png") || official.getImage().contains("jpg")) {

            String img = config.Image + official.getImage();
            Glide.with(context).load(img).placeholder(R.drawable.logo).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        }

        title.setText(models.get(position).getTitle());
        desc.setText(models.get(position).getDesc());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, AdsActivity.class);
                context.startActivity(i);
            }
            });
        container.addView(view, 0);
        return view;
    }

    @Override
   public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View)object);
    }

}