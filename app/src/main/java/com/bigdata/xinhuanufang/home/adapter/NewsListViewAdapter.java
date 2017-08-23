package com.bigdata.xinhuanufang.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.TopNewsAdapter;
import com.bigdata.xinhuanufang.home.bean.NewsBean;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.configUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SEELE on 2017/8/18.
 */

public class NewsListViewAdapter  extends BaseAdapter{
    private final Context mContext;
    private final List<NewsBean.List1Bean> list1BeenList;

    public NewsListViewAdapter(Context mContext, List<NewsBean.List1Bean> list1BeenList) {
        this.mContext = mContext;
        this.list1BeenList = list1BeenList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        return list1BeenList ==null?0:4;
    }


    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list1BeenList==null?null:list1BeenList.get(arg0);
    }


    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int	position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        NewsListViewAdapter.ViewHolder holder = null;

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.topnews_listview_item, null);
            holder = new NewsListViewAdapter.ViewHolder();
            holder.tv_title=(TextView) convertView.findViewById(R.id.tv_tnli_fighterOne);
            holder.tv_picture=(ImageView) convertView.findViewById(R.id.iv_topN_fightPhone);
            holder.tv_title2=(TextView) convertView.findViewById(R.id.tv_grli_fighterTwo);
            holder.tv_date=(TextView) convertView.findViewById(R.id.tv_grli_time);
            convertView.setTag(holder);

        }else{
            holder = (NewsListViewAdapter.ViewHolder) convertView.getTag();
        }
        //时间格式转换
        String strTime = configUtils.getStrTimes((list1BeenList.get(position)).getNews_date());
        Picasso.with(mContext).load(Config.ip+ list1BeenList.get(position).getNews_pic()).into(holder.tv_picture);
//		x.image().bind(holder.tv_picture, Config.ip+ list.get(0).getmList1().get(position).getNews_pic());
        holder.tv_title.setText((list1BeenList.get(position)).getNews_title());
        holder.tv_date.setText(strTime);
        holder.tv_title2.setText((list1BeenList.get(position)).getNews_title2());

        return convertView;
    }
    class ViewHolder{
        TextView tv_title;
        ImageView tv_picture;
        TextView tv_title2;
        TextView tv_date;
    }
}
