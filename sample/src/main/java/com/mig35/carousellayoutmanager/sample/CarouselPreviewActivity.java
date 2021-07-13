package com.mig35.carousellayoutmanager.sample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.mig35.carousellayoutmanager.DefaultChildSelectionListener;
import com.mig35.carousellayoutmanager.sample.databinding.ActivityCarouselPreviewBinding;
import com.mig35.carousellayoutmanager.sample.databinding.ItemViewBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CarouselPreviewActivity extends AppCompatActivity {

    private ArrayList<Integer> images;
    private GalleryAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_carousel_preview);

        final ActivityCarouselPreviewBinding binding = ActivityCarouselPreviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        adapter = new GalleryAdapter(getApplicationContext(), images);

        images = new ArrayList<>();

        images.add(R.drawable.ic_baseline_4k_24);

        // create layout manager with needed params: vertical, cycle
        initRecyclerView(binding.listHorizontal, new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true), adapter);

    }

    private void initRecyclerView(final RecyclerView recyclerView, final CarouselLayoutManager layoutManager, final GalleryAdapter adapter) {
        // enable zoom effect. this line can be customized
        //layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        layoutManager.setMaxVisibleItems(0);

        recyclerView.setLayoutManager(layoutManager);
        // we expect only fixed sized item for now
        recyclerView.setHasFixedSize(true);
        // sample adapter with random data
        recyclerView.setAdapter(adapter);
        // enable center post scrolling
        recyclerView.addOnScrollListener(new CenterScrollListener());
        // enable center post touching on item and item click listener

    }

    public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

        private List<Integer> images;
        private Context mContext;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView thumbnail;

            public MyViewHolder(View itemView) {
                super(itemView);
                thumbnail = itemView.findViewById(R.id.thumbnail);
            }
        }

        public GalleryAdapter(Context context, List<Integer> images) {
            mContext = context;
            this.images = images;
        }

        @Override
        public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_view, parent, false);

            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
            Integer image = images.get(position);

            Glide.with(mContext).load(images.get(position))
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.thumbnail);
        }


        @Override
        public int getItemCount() {

            return images.size();
        }

    }
}