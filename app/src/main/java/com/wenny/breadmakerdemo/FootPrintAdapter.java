package com.wenny.breadmakerdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${wenny} on 2017/6/10.
 */

public class FootPrintAdapter extends RecyclerView.Adapter<FootPrintAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<FootPrintEntity> footPrintEntities;

    public FootPrintAdapter(Context context) {
        this.context = context;
        footPrintEntities = new ArrayList<>();
    }


    public List<FootPrintEntity> getFootPrintEntities() {
        return footPrintEntities;
    }

    public void setFootPrintEntities(List<FootPrintEntity> footPrintEntities) {
        this.footPrintEntities = footPrintEntities;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_footprint, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FootPrintEntity footPrintEntity = footPrintEntities.get(position);
        holder.tv_time.setText(footPrintEntity.getImgTime());
        if (footPrintEntity.getType() == 2){
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(context).load(footPrintEntity.getImgUrl()).asBitmap().centerCrop().placeholder(R.mipmap.cover).into(holder.imageView);

        } else {
            holder.imageView.setVisibility(View.GONE);
        }

        if(footPrintEntity.isShowDays()){
            holder.ll_date.setVisibility(View.VISIBLE);
            holder.tv_days.setText("第"+footPrintEntity.getDays()+"天 "+ TimeUtil.getDates(footPrintEntity.getTimes()));
        } else {
            holder.ll_date.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(footPrintEntity.getAddress())){
            holder.tv_address.setVisibility(View.VISIBLE);
            holder.tv_address.setText(footPrintEntity.getAddress());
        } else {
            holder.tv_address.setVisibility(View.GONE);
        }
        if(TextUtils.isEmpty(footPrintEntity.getDescribe())){
            holder.tv_content.setText("没有图片描述");
        } else {
            holder.tv_content.setText(footPrintEntity.getDescribe());
        }
    }

    @Override
    public int getItemCount() {
        return footPrintEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time,tv_days,tv_content,tv_address;
        ImageView imageView;
        LinearLayout ll_date;

        public MyViewHolder(final View itemView) {
            super(itemView);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_days = (TextView) itemView.findViewById(R.id.tv_days);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            ll_date = (LinearLayout) itemView.findViewById(R.id.ll_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView, getAdapterPosition(),footPrintEntities.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position,FootPrintEntity footPrintEntity);
    }
}

