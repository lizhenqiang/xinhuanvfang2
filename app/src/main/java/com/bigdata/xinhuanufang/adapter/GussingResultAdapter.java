package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.GameJiaYouJinCaiResult;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.configUtils;

import org.xutils.x;

import java.util.List;


/*
 * 竞猜结果listViewAdapter
 * */
public class GussingResultAdapter extends BaseAdapter {
    private Context context;
    private List<GameJiaYouJinCaiResult> dataList;
    private LayoutInflater mInflater;
    private OnGussingResultItemClick onGussingResultItemClick;
    private boolean isjincai = true;

    public GussingResultAdapter(Context context,
                                List<GameJiaYouJinCaiResult> dataList) {
        this.context = context;
        this.dataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    public void setOnGussingResultItemClick(
            OnGussingResultItemClick onGussingResultItemClick) {
        this.onGussingResultItemClick = onGussingResultItemClick;
    }

    // 竞猜结果Item点击事件的回调接口
    public interface OnGussingResultItemClick {
        void onGussingResultClick(int pos);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return dataList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        // TODO Auto-generated method stub
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.gussingresult_listview_item, null);
            holder.resultItemLL = (LinearLayout) convertView
                    .findViewById(R.id.ll_grli_item);
            holder.iv_dli_dreamPhoto = (ImageView) convertView
                    .findViewById(R.id.iv_dli_dreamPhoto);
            holder.tv_dli_fighterOne = (TextView) convertView
                    .findViewById(R.id.tv_dli_fighterOne);
            holder.tv_dli_fighterTwo = (TextView) convertView
                    .findViewById(R.id.tv_dli_fighterTwo);
            holder.tv_gli_gameRuler = (TextView) convertView
                    .findViewById(R.id.tv_gli_gameRuler);
            holder.tv_dli_fightertime = (TextView) convertView
                    .findViewById(R.id.tv_dli_fightertime);
            holder.iv_gli_smile = (ImageView) convertView
                    .findViewById(R.id.iv_gli_smile);
            holder.tv_gli_smile = (TextView) convertView
                    .findViewById(R.id.tv_gli_smile);
            holder.iv_gli_handOn = (ImageView) convertView
                    .findViewById(R.id.iv_gli_handOn);
            holder.tv_dli_handOnNum = (TextView) convertView
                    .findViewById(R.id.tv_dli_handOnNum);
            holder.iv_gli_handUp = (ImageView) convertView
                    .findViewById(R.id.iv_gli_handUp);
            holder.tv_dli_handUpNum = (TextView) convertView
                    .findViewById(R.id.tv_dli_handUpNum);
            holder.superdream_progressa = (ProgressBar) convertView
                    .findViewById(R.id.superdream_progressa);
            holder.superdream_progressb = (ProgressBar) convertView
                    .findViewById(R.id.superdream_progressb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        x.image().bind(holder.iv_dli_dreamPhoto,
                Config.ip + dataList.get(position).getLiveguess_pic());
        holder.tv_dli_fighterOne.setText(dataList.get(position)
                .getPlayera_name());
        holder.tv_dli_fighterTwo.setText(dataList.get(position)
                .getPlayerb_name());
        holder.tv_gli_gameRuler.setText(dataList.get(position)
                .getSucess() + "胜");
        String strTime = configUtils.getStrTime(dataList.get(position)
                .getLiveguess_time2());
        holder.tv_dli_fightertime.setText(strTime);
        holder.tv_dli_fightertime.setVisibility(View.GONE);
        holder.tv_dli_handOnNum.setText(dataList.get(position).getSuma());
        holder.tv_dli_handUpNum.setText(dataList.get(position).getSumb());
		if (dataList.get(position).getLiveguess_playerid()
				.equals(dataList.get(position).getGuess_playerid())) {
			// 说明竞猜成功
			holder.iv_gli_smile.setImageResource(R.drawable.xiaolian);
			holder.tv_gli_smile.setText("竞猜成功");
		} else if (!dataList.get(position).getLiveguess_playerid()
                .equals(dataList.get(position).getGuess_playerid())){
            //竞猜失败
			holder.iv_gli_smile.setImageResource(R.drawable.kulian);
			holder.tv_gli_smile.setText("竞猜失败");
		}
        //先进行多次判断,后台处理过的字段没有问题的话,就直接可以用字段来区分
//        if (isjincai) {
//            if (dataList.get(position).getJoina().equals("1") && dataList.get(position).getJoinb().equals("1")) {
//                if (dataList.get(position).getPlayera_name().equals(dataList.get(position).getSucess())) {
//                    //判断用户是否押注
//                    holder.iv_gli_smile.setImageResource(R.drawable.xiaolian);
//                    holder.tv_gli_smile.setText("竞猜成功");
//                }
//                if (dataList.get(position).getPlayerb_name().equals(dataList.get(position).getSucess())) {
//                    //判断用户是否押注
//                    holder.iv_gli_smile.setImageResource(R.drawable.xiaolian);
//                    holder.tv_gli_smile.setText("竞猜成功");
//                }
//                isjincai = false;
//            }
//        } else if (isjincai) {
//            if (dataList.get(position).getJoina().equals("1")) {
//                if (dataList.get(position).getPlayera_name().equals(dataList.get(position).getSucess())) {
//                    //判断用户是否押注
//                    holder.iv_gli_smile.setImageResource(R.drawable.xiaolian);
//                    holder.tv_gli_smile.setText("竞猜成功");
//                } else {
//                    holder.iv_gli_smile.setImageResource(R.drawable.kulian);
//                    holder.tv_gli_smile.setText("竞猜失败");
//                }
//            }
//            isjincai = false;
//        } else if (isjincai) {
//            if (dataList.get(position).getJoinb().equals("1")) {
//                if (dataList.get(position).getPlayerb_name().equals(dataList.get(position).getSucess())) {
//                    //判断用户是否押注
//                    holder.iv_gli_smile.setImageResource(R.drawable.xiaolian);
//                    holder.tv_gli_smile.setText("竞猜成功");
//                } else {
//                    holder.iv_gli_smile.setImageResource(R.drawable.kulian);
//                    holder.tv_gli_smile.setText("竞猜失败");
//                }
//            }
//            isjincai = false;
//        }


        if (dataList.get(position).getJoina().equals("0")) {
            if (dataList.get(position).getJoinb().equals("0")) {

                holder.iv_gli_smile.setImageResource(R.drawable.kulian);
                holder.tv_gli_smile.setText("未竞猜");
            }
        }
        // 显示押注了那位选手
        if (dataList.get(position).getJoina().equals("1")) {
            holder.iv_gli_handOn.setImageResource(R.drawable.zuodianzan_1);
        } else if (dataList.get(position).getJoina().equals("0")) {
            holder.iv_gli_handOn.setImageResource(R.drawable.zuodianzan_0);
        }
        if (dataList.get(position).getJoinb().equals("1")) {
            holder.iv_gli_handUp.setImageResource(R.drawable.zuodianzan_1);
        } else if (dataList.get(position).getJoinb().equals("0")) {
            holder.iv_gli_handUp.setImageResource(R.drawable.zuodianzan_0);
        }
        holder.resultItemLL.setId(position);
        holder.resultItemLL.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onGussingResultItemClick.onGussingResultClick(v.getId());
            }
        });

        holder.superdream_progressa.setProgress(Integer.parseInt(dataList.get(position).getSuma()));
        holder.superdream_progressa.setVisibility(View.GONE);
        holder.superdream_progressb.setProgress(Integer.parseInt(dataList.get(position).getSumb()));
        holder.superdream_progressb.setVisibility(View.GONE);
        return convertView;
    }

    public final class ViewHolder {
        private LinearLayout resultItemLL; // item布局
        ImageView iv_dli_dreamPhoto;
        TextView tv_dli_fighterOne;
        TextView tv_dli_fighterTwo;
        TextView tv_gli_gameRuler;// 标题
        TextView tv_dli_fightertime;
        ImageView iv_gli_smile;// 失败与否的图标
        TextView tv_gli_smile;// 竞猜失败
        ImageView iv_gli_handOn;// 选手1的竞猜结果
        TextView tv_dli_handOnNum;
        ImageView iv_gli_handUp;// 选手2
        TextView tv_dli_handUpNum;
        ProgressBar superdream_progressa;
        ProgressBar superdream_progressb;
    }
}
