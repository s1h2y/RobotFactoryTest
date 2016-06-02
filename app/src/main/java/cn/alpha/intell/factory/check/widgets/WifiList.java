package cn.alpha.intell.factory.check.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.alpha.intell.factory.check.R;
import cn.alpha.intell.factory.check.utils.MyLog;

/**
 * Created by shy on 16-5-25.
 */
public class WifiList extends LinearLayout {

    private BaseAdapter mAdapter;
    private MyOnItemClickListener onItemClickListener;

    public WifiList(Context context) {
        super(context);
        initAttr(null);
    }

    public WifiList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
    }

    public void initAttr(AttributeSet attrs) {
        setOrientation(VERTICAL);
    }

    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    public void notifyChange() {
        removeAllViews();
        int count = mAdapter.getCount();
        MyLog.d("shy get wifi count=" + count);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final int index = i;
            final LinearLayout layout = new LinearLayout(getContext());
            layout.setLayoutParams(params);
            layout.setOrientation(VERTICAL);
            View v = mAdapter.getView(i, null, null);
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(WifiList.this,
                                layout, index, mAdapter.getItem(index));
                    }
                }
            });
            v.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEvent.ACTION_DOWN == event.getAction()
                            || MotionEvent.ACTION_MOVE == event.getAction()) {
                        v.setBackgroundColor(Color.YELLOW);
                    } else {
                        v.setBackgroundColor(getResources().getColor(R.color.list_background_grey));
                    }
                    return false;
                }
            });
            layout.addView(v);
            if (i < mAdapter.getCount() - 1) {
                ImageView imageView = new ImageView(getContext());
                imageView.setBackgroundColor(Color.parseColor("#BFBFBF"));
                imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
                layout.addView(imageView);
            }
            addView(layout, index);
        }
    }

    public void setOnItemClickListener(MyOnItemClickListener onClickListener) {
        this.onItemClickListener = onClickListener;
    }

    public void setAdapter(BaseAdapter adapter) {
        this.mAdapter = adapter;
        notifyChange();
    }

    public static interface MyOnItemClickListener {
        public void onItemClick(ViewGroup parent, View view, int position,
                                Object o);
    }

}
