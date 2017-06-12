package com.younchen.younsampleproject.sys.loader.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.sys.loader.bean.CleanContactItem;

/**
 * Created by Administrator on 2017/6/12.
 */

public class CleanItemAdapter extends BaseAdapter<CleanContactItem> {

    private OnItemClickedListener mItemClickedListener;

    public CleanItemAdapter(Context context) {
        super(context, R.layout.item_contact_style1);
    }

    @Override
    public void covert(ViewHolder holder, final CleanContactItem item, final int position) {
        String contactName = item.title;
        holder.setText(R.id.txt_title, contactName);
        holder.setText(R.id.contact_count, "(" + item.count + ")");
        bindIcon((ImageView) holder.getView(R.id.icon_image), item);
        holder.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickedListener != null) {
                    mItemClickedListener.onItemSelected(position, item);
                }
            }
        });
    }

    private void bindIcon(ImageView imageView, CleanContactItem item) {
        switch (item.type) {
            case CleanContactItem.CLEAN_TYPE_NO_PHONE:
                imageView.setImageResource(R.color.colorPrimaryDark);
                break;
            case CleanContactItem.CLEAN_TYPE_NO_NAME:
                imageView.setImageResource(R.color.colorAccent);
                break;
            case CleanContactItem.CLEAN_TYPE_BLOCKED:
                imageView.setImageResource(R.color.colorPrimary);
                break;
            case CleanContactItem.CLEAN_TYPE_ALL_CONTACT:
                imageView.setImageResource(R.color.colorAccent);
                break;
            default:
                imageView.setImageResource(R.color.gray);
                break;
        }
    }

    public void setItemClickedListener(OnItemClickedListener listener) {
        this.mItemClickedListener = listener;
    }

    public interface OnItemClickedListener {
        void onItemSelected(int position, CleanContactItem item);
    }

}
