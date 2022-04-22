package com.yyzy.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yyzy.myapp.R;
import com.yyzy.myapp.entity.ViedeoDataEntity;
import com.yyzy.myapp.listener.OnItemChildClickListener;
import com.yyzy.myapp.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import xyz.doikki.videocontroller.component.PrepareView;

public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ViedeoDataEntity> listDatas;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemClickListener mOnItemClickListener;

    public void setDatas(List<ViedeoDataEntity> datas) {
        this.listDatas = datas;
    }

    public RecommendAdapter(Context context) {
        this.mContext = context;
    }

    public RecommendAdapter(Context context, List<ViedeoDataEntity> datas) {
        this.mContext = context;
        this.listDatas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recommend_layout, parent, false);
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
                .load(dataEntity.getHeadurl())
                //设置圆角为30像素
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(vh.mThumb);
        Glide.with(mContext)
                .load(dataEntity.getHeadurl())
                //设置占位符图片,当网络请求的网路图未加载出来,便使用占位图片;//设置网络图片为圆形展示出来
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(vh.ivHeader);
        vh.mPosition = position;
    }

    @Override
    public int getItemCount() {
        if (listDatas != null && listDatas.size() > 0) {
            return listDatas.size();
        } else {
            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle, tvTitleName, tvComment, tvCollect, tvDz;
        private ImageView ivHeader, ivEnjoy, mThumb;
        public FrameLayout mPlayerContainer;
        public PrepareView mPrepareView;
        public int mPosition;

        public ViewHolder(@NonNull View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_title);
            tvTitleName = v.findViewById(R.id.tv_title_name);
            tvComment = v.findViewById(R.id.tv_comment);
            tvCollect = v.findViewById(R.id.tv_collect);
            tvDz = v.findViewById(R.id.tv_dz);
            ivHeader = v.findViewById(R.id.iv_header);
            ivEnjoy = v.findViewById(R.id.iv_enjoy);
            mPlayerContainer = v.findViewById(R.id.player_container);
            mPrepareView = v.findViewById(R.id.prepare_view);
            mThumb = mPrepareView.findViewById(R.id.thumb);
            if (mOnItemChildClickListener != null) {
                mPlayerContainer.setOnClickListener(this);
            }
            if (mOnItemClickListener != null) {
                v.setOnClickListener(this);
            }
            //通过tag将ViewHolder和itemView绑定
            v.setTag(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.player_container) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onItemChildClick(mPosition);
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mPosition);
                }
            }
        }
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
