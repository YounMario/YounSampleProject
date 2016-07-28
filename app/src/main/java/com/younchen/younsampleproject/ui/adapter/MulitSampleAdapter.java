package com.younchen.younsampleproject.ui.adapter;

import android.content.Context;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.MulitTypeAdapter;
import com.younchen.younsampleproject.commons.adapter.MulitTypeAdapterSupport;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.ui.bean.ChatMessage;

/**
 * Created by 龙泉 on 2016/7/27.
 */
public class MulitSampleAdapter extends MulitTypeAdapter<ChatMessage> {


    public MulitSampleAdapter(Context context){
        super(context, null);
        init();
    }

    private void init() {
        MulitTypeAdapterSupport support = new MulitTypeAdapterSupport() {
            @Override
            public int getType(int position) {
                return getItem(position).getType() ;
            }

            @Override
            public int layoutId(int type) {
                switch (type){
                    case ChatMessage.TYPE_IMG:
                        return R.layout.item_image;
                    case ChatMessage.TYPE_TEXT:
                        return R.layout.item_txt;
                    default:
                        return -1;
                }
            }
        };
        setMulitTypeAdapterSupport(support);
    }


    @Override
    public void covert(ViewHolder holder, ChatMessage item) {
        if (item.getType() == ChatMessage.TYPE_IMG) {
            holder.setImage(R.id.img_view,R.drawable.tab);
        } else if (item.getType() == ChatMessage.TYPE_TEXT) {
            holder.setText(R.id.txt_content,item.getMsg());
        }
    }
}
