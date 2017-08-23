package com.bigdata.xinhuanufang.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.GameNewsAdapter;
import com.bigdata.xinhuanufang.home.bean.NewsBean;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.configUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SEELE on 2017/8/18.
 */

public class NewsListViewAdapter2 extends BaseAdapter {
    private final List<NewsBean.List2Bean> list2BeanList;
    private final Context mContext;

    public NewsListViewAdapter2(Context mContext, List<NewsBean.List2Bean> list2BeanList) {
        this.mContext = mContext;
        this.list2BeanList = list2BeanList;
    }

    @Override
    public int getCount() {
        return list2BeanList==null?0:4;
    }

    @Override
    public Object getItem(int i) {
        return list2BeanList==null?null:list2BeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int	position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        NewsListViewAdapter2.ViewHolder holder = null;

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.topnews_listview_item, null);
            holder = new NewsListViewAdapter2.ViewHolder();
            holder.tv_title=(TextView) convertView.findViewById(R.id.tv_tnli_fighterOne);
            holder.tv_picture=(ImageView) convertView.findViewById(R.id.iv_topN_fightPhone);
            holder.tv_title2=(TextView) convertView.findViewById(R.id.tv_grli_fighterTwo);
            holder.tv_date=(TextView) convertView.findViewById(R.id.tv_grli_time);
            convertView.setTag(holder);
        }else{
            holder = (NewsListViewAdapter2.ViewHolder) convertView.getTag();
        }
        //时间格式转换
        String strTime = configUtils.getStrTimes((list2BeanList.get(position)).getNews_date());
        Picasso.with(mContext).load(Config.ip + list2BeanList.get(position).getNews_pic()).into(holder.tv_picture);
//		x.image().bind(holder.tv_picture, Config.ip + list.get(0).getmList2().get(position).getNews_pic());
        holder.tv_title.setText((list2BeanList.get(position)).getNews_title());
        holder.tv_date.setText(strTime);
        holder.tv_title2.setText((list2BeanList.get(position)).getNews_title2());

        return convertView;
    }
    class ViewHolder{
        TextView tv_title;
        ImageView tv_picture;
        TextView tv_title2;
        TextView tv_date;
    }
}
