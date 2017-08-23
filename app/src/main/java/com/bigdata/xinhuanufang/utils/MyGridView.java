package com.bigdata.xinhuanufang.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.GridView;
/*
 * ��дGridView�������ListView��ͻ����
 * */
public class MyGridView extends GridView {
	private boolean canScroll;
	 
	private GestureDetector mGestureDetector;
	OnTouchListener mGestureListener;
	public MyGridView(Context context) {
        super(context);
    }
 
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mGestureDetector = new GestureDetector(new YScrollDetector());
		canScroll = true;
    }
 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;
 
        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
            // The great Android "hackatlon", the love, the magic.
            // The two leftmost bits in the height measure spec have
            // a special meaning, hence we can't use them to describe height.
            heightSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        }
        else {
            // Any other height should be respected as is.
            heightSpec = heightMeasureSpec;
        }
 
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
    @Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(ev.getAction() == MotionEvent.ACTION_UP)
			canScroll = true;
		return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
	}

	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if(canScroll)
				if (Math.abs(distanceY) >= Math.abs(distanceX))
					canScroll = true;
				else
					canScroll = false;
			return canScroll;
		}
	}
}
