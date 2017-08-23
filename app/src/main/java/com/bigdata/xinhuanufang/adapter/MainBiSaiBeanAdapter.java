package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.bean.MainBiSaiBean;
import com.bigdata.xinhuanufang.custom.CircleImageView;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.configUtils;

import org.xutils.x;

import java.util.List;

/**
 * Created by weiyu$ on 2017/4/17.
 */

public class MainBiSaiBeanAdapter extends BaseAdapter {
    private Context context;
    private List<MainBiSaiBean> list;
    private LayoutInflater mInflater;
    public MainBiSaiBeanAdapter(Context context, List<MainBiSaiBean> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.messageboard_listview_item,
                    null);
            holder=new ViewHolder();
            holder.iv_rli_fighterPhoto=(CircleImageView) convertView.findViewById(R.id.iv_rli_fighterPhoto);
            holder.tv_rli_fighterOne=(TextView) convertView.findViewById(R.id.tv_rli_fighterOne);
            holder.tv_rli_fighterTwo=(TextView) convertView.findViewById(R.id.tv_rli_fighterTwo);
            holder.tv_rli_context=(TextView) convertView.findViewById(R.id.tv_rli_context);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        //时间格式转换
        String strTime = configUtils.getStrTimes(list.get(position).getMessage_date());
        holder.tv_rli_fighterOne.setText(list.get(position).getUser_username());
        holder.tv_rli_fighterTwo.setText(strTime);
        holder.tv_rli_context.setText(list.get(position).getMessage_content());
        x.image().bind(holder.iv_rli_fighterPhoto, Config.ip+list.get(position).getUser_head());
        //加载头像
//        if (list.get(position).getUser_head()!=null) {
//            Bitmap bmp= BitmapFactory.decodeFile(list.get(position).getUser_head() + ".jpg");
//
//            holder.iv_rli_fighterPhoto.setImageBitmap(bmp);
//        }

        return convertView;
    }
    class ViewHolder{
        CircleImageView iv_rli_fighterPhoto;//头像
        TextView tv_rli_fighterOne;//昵称
        TextView tv_rli_fighterTwo;//时间
        TextView tv_rli_context;//评价内容
    }
}
