package com.younchen.younsampleproject.ui.view.adapter;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.adapter.BaseAdapter;
import com.younchen.younsampleproject.commons.holder.ViewHolder;
import com.younchen.younsampleproject.material.bean.AppBean;

/**
 * Created by Administrator on 2017/5/9.
 */

public class DragViewAdapter extends BaseAdapter<AppBean> {

    private static final String IMAGEVIEW_TAG = "icon bitmap";
    private static final int KEY_PRE = 0x88888;
    private ItemDragListener mItemDragListener;

    public DragViewAdapter(Context context, ItemDragListener itemDragListener) {
        super(context, R.layout.item_option_menu_grid_style);
        this.mItemDragListener = itemDragListener;
    }

    @Override
    public void covert(ViewHolder holder, final AppBean item, final int position) {
        final ImageView imageView = (ImageView) holder.getView(R.id.img_icon);
        imageView.setImageResource(R.color.colorAccent);
        holder.setText(R.id.txt_app_name, item.appName);
        imageView.setTag(KEY_PRE + position);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {

            public boolean onLongClick(View v) {

                ClipData.Item clipDataItem = new ClipData.Item(imageView.getTag().toString());
                ClipData dragData = new ClipData(v.getTag().toString(), new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, clipDataItem);
                View.DragShadowBuilder myShadow = new MyDragShadowBuilder(imageView);
                v.setOnDragListener(new DragEventListener(v, position));
                v.startDrag(dragData,  // the data to be dragged
                        myShadow,  // the drag shadow builder
                        null,      // no need to use local data
                        0          // flags (not currently used, set to 0)
                );
                return true;
            }
        });

    }

    private static class MyDragShadowBuilder extends View.DragShadowBuilder {

        // The drag shadow image, defined as a drawable thing
        private static Drawable shadow;
        private ImageView mDrawView;

        // Defines the constructor for myDragShadowBuilder
        public MyDragShadowBuilder(View v) {

            // Stores the View parameter passed to myDragShadowBuilder.
            super(v);
            mDrawView = (ImageView) v;
            // Creates a draggable image that will fill the Canvas provided by the system.
            shadow = mDrawView.getDrawable();
        }

        // Defines a callback that sends the drag shadow dimensions and touch point back to the
        // system.
        @Override
        public void onProvideShadowMetrics(Point size, Point touch) {
            int width, height;
            width = getView().getWidth();
            height = getView().getHeight();
            shadow.setBounds(0, 0, width, height);

            // Sets the size parameter's width and height values. These get back to the system
            // through the size parameter.
            size.set(width, height);

            // Sets the touch point's position to be in the middle of the drag shadow
            touch.set(width / 2, height / 2);
        }

        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
        @Override
        public void onDrawShadow(Canvas canvas) {

            // Draws the ColorDrawable in the Canvas passed in from the system.
            //shadow.draw(canvas);
            mDrawView.draw(canvas);
        }
    }

    protected class DragEventListener implements View.OnDragListener {

        private View mView;
        private int mItemPosition;

        public DragEventListener(View view, int itemPosition) {
            this.mView = view;
            this.mItemPosition = itemPosition;
        }

        public boolean onDrag(View v, DragEvent event) {
            // Defines a variable to store the action type for the incoming event
            final int action = event.getAction();
            // Handles each of the expected events
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getClipDescription().getLabel().equals(mView.getTag().toString())) {
                        mView.setVisibility(View.INVISIBLE);
                        mView.invalidate();
                        mItemDragListener.onDragStart(v, mItemPosition);
                        return true;
                    }
                    return false;
                case DragEvent.ACTION_DRAG_ENTERED:
                case DragEvent.ACTION_DRAG_LOCATION:
                case DragEvent.ACTION_DRAG_EXITED:
                    mItemDragListener.onDragging(v, mItemPosition, event.getX(), event.getY());
                    // Ignore the event
                    return true;
                case DragEvent.ACTION_DROP:
                    mItemDragListener.onDrop(v, mItemPosition);
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    mView.setVisibility(View.VISIBLE);
                    mView.invalidate();
                    mItemDragListener.onDragEnd(v, mItemPosition);
                    return true;
                default:
                    break;
            }
            return false;
        }
    }

    public interface ItemDragListener {

        void onDragStart(View v, int position);

        void onDragging(View v, int position, float x, float y);

        void onDragEnd(View v, int position);

        void onDrop(View v, int mItemPosition);
    }
}
