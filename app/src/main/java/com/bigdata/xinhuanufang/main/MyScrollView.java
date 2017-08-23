package com.bigdata.xinhuanufang.main;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    private OnScrollListener onScrollListener;
    private int lastScrollY;//滑动的距离
    private float y;
    private float y1;
    private float downY;

    public MyScrollView(Context context) {
        super(context, null);
    }
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }
    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public void setOnScrollListener(OnScrollListener onScrollListener){
        this.onScrollListener = onScrollListener;
    }
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            int scrollY = MyScrollView.this.getScrollY();

            if(lastScrollY != scrollY){
                lastScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(), 20);
            }
            if(onScrollListener != null){
                onScrollListener.onScroll(scrollY);
            }
            if (msg.what==2) {
//                onScrollListener.onScroll(11111111);
            }
        };

    };
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(onScrollListener != null){
            onScrollListener.onScroll(lastScrollY = this.getScrollY());
        }
        switch(ev.getAction()){
//        case MotionEvent.ACTION_UP:  
//             handler.sendMessageDelayed(handler.obtainMessage(), 20);    
//            break;
            case MotionEvent.ACTION_DOWN:
                 y = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                handler.sendMessageDelayed(handler.obtainMessage(), 20);
//                float y2=ev.getY();
//                y1 = y2 - y;
//                    System.out.println("移动"+y1);
//                    if(y1 > 0){
//                        if(onScrollListener != null){
//                            onScrollListener.onScroll(11111111);
//
//                        }
//                }
                break;
            case MotionEvent.ACTION_UP:
                downY = ev.getY() - y;
                System.out.println("按下的位置: "+y+"抬起的位置 "+ev.getY()+"  距离差  :"+downY);
//                System.out.println("现在滑动的距离:"+ downY);
//                System.out.println("现在滑动的距离2:"+ev.getY());
//                System.out.println("现在滑动的距离3:"+y1 + "--" + y + "---" + ( ev.getY()- y1)  );
                /*if (ev.getY()>y&&(Math.abs(ev.getY()-y)>50)) {
                    Message msg=Message.obtain();
                    msg.what=2;
                    handler.sendMessage(msg);
                    break;
                }*/
                if(ev.getY() > 800){
                    if(onScrollListener != null){
                        onScrollListener.onScroll(11111111);

                    }
//                    onScrollListener.onScroll(11111111);
//                    Message msg= Message.obtain();
//                    msg.what=2;
//                    handler.sendMessage(msg);
                }
                System.out.println("滑动"+ev.getY());
                if(ev.getY() <240){
                    if(onScrollListener != null){
                        onScrollListener.onScroll(22222222);

                    }
//                    onScrollListener.onScroll(11111111);
//                    Message msg= Message.obtain();
//                    msg.what=2;
//                    handler.sendMessage(msg);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    private float xDistance, yDistance, xLast, yLast;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if(xDistance > yDistance){
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }




    public interface OnScrollListener{
        public void onScroll(int scrollY);
    }

//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//    	// TODO Auto-generated method stub
//    	super.onScrollChanged(l, t, oldl, oldt);
//    }

}
