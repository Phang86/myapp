package com.yyzy.myapp.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yyzy.myapp.R;
import com.yyzy.myapp.entity.CollectEntity;
import com.yyzy.myapp.listener.OnItemClickListener;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<CollectEntity> datas;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    //private

    public void setData(List<CollectEntity> data){
        this.datas = data;
    }

    public CollectAdapter(Context mContext){
        this.mContext = mContext;
    }

    public CollectAdapter(Context mContext, List<CollectEntity> data){
        this.mContext = mContext;
        this.datas = data;
    }

    @Override
    public int getItemViewType(int position) {
        int type = datas.get(position).getType();
        return type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1){
            View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_one,parent,false);
            return new ViewHolderOne(view);
        }else if (viewType == 2){
            View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_two,parent,false);
            return new ViewHolderTwo(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_three,parent,false);
            return new ViewHolderThree(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        CollectEntity collectEntity = datas.get(position);
        if (type == 1){
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            viewHolderOne.collectEntity = collectEntity;
            Glide.with(mContext)
                    .load(collectEntity.getHeaderUrl())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(viewHolderOne.bigImg);
            Glide.with(mContext)
                    .load(collectEntity.getHeaderUrl())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(viewHolderOne.minImg);
            viewHolderOne.bigTv.setText(collectEntity.getNewsTitle()+".");
            viewHolderOne.minTvOne.setText(collectEntity.getAuthorName());
            viewHolderOne.minTvTwo.setText(String.valueOf(collectEntity.getCommentCount()+"评论."));
            viewHolderOne.minTvThree.setText(collectEntity.getReleaseDate());
        }else if (type == 2){
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.collectEntity = collectEntity;
            Glide.with(mContext)
                    .load(collectEntity.getHeaderUrl())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(viewHolderTwo.minImg);
            Glide.with(mContext)
                    .load(collectEntity.getPicx())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(viewHolderTwo.imgOne);
            Glide.with(mContext)
                    .load(collectEntity.getPicxx())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(viewHolderTwo.imgTwo);
            Glide.with(mContext)
                    .load(collectEntity.getPicxxx())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(viewHolderTwo.imgThree);
            viewHolderTwo.bigTv.setText(collectEntity.getNewsTitle()+".");
            viewHolderTwo.minTvOne.setText(collectEntity.getAuthorName());
            viewHolderTwo.minTvTwo.setText(String.valueOf(collectEntity.getCommentCount()+"评论."));
            viewHolderTwo.minTvThree.setText(collectEntity.getReleaseDate());
        }else {
            ViewHolderThree viewHolderThree = (ViewHolderThree) holder;
            viewHolderThree.collectEntity = collectEntity;
            Glide.with(mContext)
                    .load(collectEntity.getHeaderUrl())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(viewHolderThree.minImg);
            Glide.with(mContext)
                    .load(collectEntity.getHeaderUrl())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(viewHolderThree.imgOne);
            viewHolderThree.bigTv.setText(collectEntity.getNewsTitle()+".");
            viewHolderThree.minTvOne.setText(collectEntity.getAuthorName());
            viewHolderThree.minTvTwo.setText(String.valueOf(collectEntity.getCommentCount()+"评论."));
            viewHolderThree.minTvThree.setText(collectEntity.getReleaseDate());
        }
    }

    @Override
    public int getItemCount() {
        if (datas != null && datas.size() > 0){
            return datas.size();
        }else {
            return 0;
        }

    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        private ImageView bigImg,minImg;
        private TextView bigTv,minTvOne,minTvTwo,minTvThree;
        private CollectEntity collectEntity;

        public ViewHolderOne(@NonNull View view) {
            super(view);
            bigImg = view.findViewById(R.id.bigImg);
            minImg = view.findViewById(R.id.minImg);
            bigTv = view.findViewById(R.id.bigTv);
            minTvOne = view.findViewById(R.id.minTv_one);
            minTvTwo = view.findViewById(R.id.minTv_two);
            minTvThree = view.findViewById(R.id.minTv_three);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(collectEntity);
                }
            });
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        private ImageView minImg,imgOne,imgTwo,imgThree;
        private TextView bigTv,minTvOne,minTvTwo,minTvThree;
        private CollectEntity collectEntity;
        public ViewHolderTwo(@NonNull View view) {
            super(view);
            bigTv = view.findViewById(R.id.bigTv);
            minImg = view.findViewById(R.id.minImg);
            imgOne = view.findViewById(R.id.img_one);
            imgTwo = view.findViewById(R.id.img_two);
            imgThree = view.findViewById(R.id.img_three);
            minTvOne = view.findViewById(R.id.minTv_one);
            minTvTwo = view.findViewById(R.id.minTv_two);
            minTvThree = view.findViewById(R.id.minTv_three);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(collectEntity);
                }
            });
        }
    }

    class ViewHolderThree extends RecyclerView.ViewHolder {
        private ImageView minImg,imgOne;
        private TextView bigTv,minTvOne,minTvTwo,minTvThree;
        private CollectEntity collectEntity;
        public ViewHolderThree(@NonNull View view) {
            super(view);
            minImg = view.findViewById(R.id.minImg);
            imgOne = view.findViewById(R.id.img_one);
            bigTv = view.findViewById(R.id.bigTv);
            minTvOne = view.findViewById(R.id.minTv_one);
            minTvTwo = view.findViewById(R.id.minTv_two);
            minTvThree = view.findViewById(R.id.minTv_three);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(collectEntity);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }
}
