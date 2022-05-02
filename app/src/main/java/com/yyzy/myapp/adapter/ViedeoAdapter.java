package com.yyzy.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;
import com.yyzy.myapp.R;
import com.yyzy.myapp.entity.ViedeoDataEntity;

import java.util.ArrayList;
import java.util.List;

public class ViedeoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ViedeoDataEntity> listDatas = new ArrayList<>();

    public void setDatas(List<ViedeoDataEntity> datas) {
        this.listDatas = datas;
    }

    public ViedeoAdapter(Context context, List<ViedeoDataEntity> datas) {
        this.mContext = context;
        this.listDatas = datas;
    }

    public ViedeoAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_viedeo_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        ViedeoDataEntity dataEntity = listDatas.get(position);
        vh.tvTitle.setText(dataEntity.getTitle());
        vh.tvTitleName.setText(dataEntity.getAuthor());
        vh.tvComment.setText(String.valueOf(dataEntity.getCommentNum()));
        vh.tvCollect.setText(String.valueOf(dataEntity.getCollectNum()));
        vh.tvDz.setText(String.valueOf(dataEntity.getLikeNum()));
        Glide.with(mContext)
                .load(dataEntity.getCoverUrl())
                //设置圆角为30像素
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(40)))
                .into(vh.ivEnjoy);
        Glide.with(mContext)
                .load(dataEntity.getHeadurl())
                //设置占位符图片,当网络请求的网路图未加载出来,便使用占位图片;//设置网络图片为圆形展示出来
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(vh.ivHeader);
        vh.flagCollect = dataEntity.isFlagCollect();
        vh.flagLike = dataEntity.isFlagLike();
    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle,tvTitleName,tvComment,tvCollect,tvDz;
        private ImageView ivHeader,ivEnjoy,imgCollect,imgLike;
        private boolean flagCollect,flagLike;
        public ViewHolder(@NonNull View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_title);
            tvTitleName = v.findViewById(R.id.tv_title_name);
            tvComment = v.findViewById(R.id.tv_comment);
            tvCollect = v.findViewById(R.id.tv_collect);
            tvDz = v.findViewById(R.id.tv_dz);
            ivHeader = v.findViewById(R.id.iv_header);
            ivEnjoy = v.findViewById(R.id.iv_enjoy);
            imgCollect = v.findViewById(R.id.img_collect);
            imgLike = v.findViewById(R.id.img_like);
            imgCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int collectNum = Integer.parseInt(tvCollect.getText().toString());
                    if (flagCollect){
                        if (collectNum > 0){  //已收藏
                            tvCollect.setText(String.valueOf(--collectNum));
                            imgCollect.setImageResource(R.mipmap.collect);
                            Toast.makeText(imgLike.getContext(), "已取消收藏！", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        //未收藏
                        tvCollect.setText(String.valueOf(++collectNum));
                        imgCollect.setImageResource(R.mipmap.collect_select);
                        Toast.makeText(imgLike.getContext(), "已收藏！", Toast.LENGTH_SHORT).show();
                    }
                    flagCollect = !flagCollect;
                }
            });
            imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int likeNum = Integer.parseInt(tvDz.getText().toString());
                    if (flagLike){
                        if (likeNum > 0){  //已收藏
                            tvDz.setText(String.valueOf(--likeNum));
                            imgLike.setImageResource(R.mipmap.dianzan);
                            Toast.makeText(imgLike.getContext(), "已取消点赞！", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        //未收藏
                        tvDz.setText(String.valueOf(++likeNum));
                        imgLike.setImageResource(R.mipmap.dianzan_select);
                        Toast.makeText(imgLike.getContext(), "已点赞！", Toast.LENGTH_SHORT).show();
                    }
                    flagLike = !flagLike;
                }
            });
        }

    }


}
