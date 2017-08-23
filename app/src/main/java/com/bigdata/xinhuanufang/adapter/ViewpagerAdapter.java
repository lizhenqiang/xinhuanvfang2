package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigdata.xinhuanufang.game.bean.MainPrices;
import com.bigdata.xinhuanufang.utils.Config;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/*
 * ViewpagerAdapter
 * */
public class ViewpagerAdapter extends PagerAdapter {

	private List<MainPrices> list;
	private Context context;

	public ViewpagerAdapter(List<MainPrices> list, Context context) {
		this.list = list;
		this.context=context;
	}

	/**
	 * 返回有多少页
	 * */
	@Override
	public int getCount() {
		return list.size() * 10000 * 100; // 返回一个这么大的值是为了实现无限循环
	}

	/** 用于判断ViewPager是否可以复用 */
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object; // 固定写法
	}

	/**
	 * 跟ListView中的Adpater中的getView方法类似，用于创建一个Item
	 * 
	 * @param container
	 * ViewPager
	 * @param position
	 * 要生成item的位置
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(container.getContext());
		position = position % list.size();

		final ImageView im=imageView;
		Transformation transformation = new Transformation() {

			@Override
			public Bitmap transform(Bitmap source) {

				int targetWidth = im.getWidth();

				if(source.getWidth()==0){
					return source;
				}

				//如果图片小于设置的宽度，则返回原图
				if(source.getWidth()<targetWidth){
					return source;
				}else{
					//如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
					double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
					int targetHeight = (int) (targetWidth * aspectRatio);
					if (targetHeight != 0 && targetWidth != 0) {
						Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, true);
						if (result != source) {
							// Same bitmap is returned if sizes are the same
							source.recycle();
						}
						return result;
					} else {
						return source;
					}
				}

			}

			@Override
			public String key() {
				return "transformation" + " desiredWidth";
			}
		};
		String path=list.get(position).getLayer_pic();
//		x.image().bind(imageView, Config.ip+path);
		Picasso.with(context).load(Config.ip+path).transform(transformation).into(imageView);
		container.addView(imageView); // 把一个item添加到ViewPager容器
		return imageView;
	}

	/**
	 * 销毁一个Item
	 * 
	 * @param container
	 *            ViewPager
	 * @param position
	 *            要销毁item的位置
	 * @param object
	 *            instantiateItem方法的返回值
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((ImageView) object);
	}

}
