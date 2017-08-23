package com.bigdata.xinhuanufang.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author asus
 * ��дScrollVIEW
 */
public class OverwriteScrollView extends ScrollView {
	public OverwriteScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public OverwriteScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public OverwriteScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public interface ScrollViewListener {
		void onScrollChanged(OverwriteScrollView scrollView, int x, int y,
                             int oldx, int oldy);

	}

	private ScrollViewListener scrollViewListener = null;

	public void setScrollViewListener(ScrollViewListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (scrollViewListener != null) {
			scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}

}
