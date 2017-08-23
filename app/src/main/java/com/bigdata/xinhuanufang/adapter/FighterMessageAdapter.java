package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.GameFighterBean;
import com.bigdata.xinhuanufang.utils.Config;

import org.xutils.x;

import java.util.List;

/*
 * 选手信息Adapter
 * */
public class FighterMessageAdapter extends BaseAdapter {
    private Context context;
    private List<GameFighterBean> list;
    private LayoutInflater mInflater;
    private OnFighterMessageClick onFighterMessageClick;

    public void setOnFighterItemClick(
            OnFighterMessageClick onFighterMessageClick) {
        this.onFighterMessageClick = onFighterMessageClick;
    }

    // 选手信息的item回调接口
    public interface OnFighterMessageClick {
        void onFighterClick(int pos);
    }

    public FighterMessageAdapter(Context context, List<GameFighterBean> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.attentionfighter_listview_item, null);
            holder = new ViewHolder();
            holder.tv_fai_fighterOne = (TextView) convertView.findViewById(R.id.tv_fai_fighterOne);
            holder.tv_fai_age = (TextView) convertView.findViewById(R.id.tv_fai_age);
            holder.iv_fai_fightPhone = (ImageView) convertView.findViewById(R.id.iv_fai_fightPhone);
            holder.tv_fai_country = (TextView) convertView.findViewById(R.id.tv_fai_country);
            holder.tv_fai_level = (TextView) convertView.findViewById(R.id.tv_fai_level);
            holder.itemLL = (LinearLayout) convertView.findViewById(R.id.ll_fai_item);
            holder.tv_fai_honor = (TextView) convertView.findViewById(R.id.tv_fai_honor);
            holder.finghter_isfollow = (Button) convertView.findViewById(R.id.finghter_isfollow);
            holder.iv_fai_sex = (ImageView) convertView.findViewById(R.id.iv_fai_sex);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.finghter_isfollow.setVisibility(View.INVISIBLE);
        holder.tv_fai_fighterOne.setText(list.get(position).getPlayer_name());
        holder.tv_fai_age.setText(list.get(position).getPlayer_age());
        holder.tv_fai_country.setText(list.get(position).getPlayer_area());
        holder.tv_fai_level.setText(list.get(position).getPlayer_level());
        try {
            String Honors = "";
            for (int i = 0; i < list.get(position).getHonors().size(); i++) {
                Honors = Honors + list.get(position).getHonors().get(i).getHonors_content() + " ";
            }
            holder.tv_fai_honor.setText(Honors);
        } catch (Exception e) {
            e.printStackTrace();
        }
        x.image().bind(holder.iv_fai_fightPhone, Config.ip + list.get(position).getPlayer_head());
        holder.itemLL.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onFighterMessageClick.onFighterClick(position);
            }
        });
        if (list.get(position).getPlayer_sex().equals("男")) {
            holder.iv_fai_sex.setImageResource(R.drawable.nan);
        } else if (list.get(position).getPlayer_sex().equals("女")) {
            holder.iv_fai_sex.setImageResource(R.drawable.nv);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_fai_fighterOne;//选手1
        ImageView iv_fai_fightPhone;//图片
        TextView tv_fai_age;//年龄
        TextView tv_fai_country;//国籍
        TextView tv_fai_level;//级别
        TextView tv_fai_honor;//荣誉
        Button finghter_isfollow;//是否关注
        private LinearLayout itemLL;
        ImageView iv_fai_sex;
    }

}
