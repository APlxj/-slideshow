package com.ping.bannerdemo.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ping.bannerdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.sorrow on 2017/5/3.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private View mHeaderView;
    private String[] mData;
    private Context mContext;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    public MyAdapter(Context context, String[] data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new MyViewHolder(mHeaderView);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(holder);
        final String data = mData[pos];
        if (holder != null) {
            //holder.mTvItem.setTag(position, data);
            holder.itemView.setTag(position - 1);
            holder.mTvItem.setText(data);
        }
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mData.length : mData.length + 1;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag(), "");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item)
        TextView mTvItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener mOnItemClickListener = null;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String data);
    }
}
