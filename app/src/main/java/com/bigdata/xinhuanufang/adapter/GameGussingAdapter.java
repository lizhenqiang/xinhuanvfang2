package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.GameJiaYouJinCaiBiSai;
import com.bigdata.xinhuanufang.main.GameGussingDetailActivity;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.configUtils;

import org.xutils.x;

import java.util.List;


/*
 * 比赛竞猜ListView Adapter
 * */
public class GameGussingAdapter extends BaseAdapter{
	private Context context;
	List<GameJiaYouJinCaiBiSai> dataList;
	private LayoutInflater mInflater;
	private boolean flag=true;
	private int index=0;

	public GameGussingAdapter(Context context,List<GameJiaYouJinCaiBiSai> dataList){
		this.context = context;
		this.dataList = dataList;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return dataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.gamegussing_listview_item,
					null);
			holder=new ViewHolder();
			holder.iv_ggli_fightPhoto=(ImageView) convertView.findViewById(R.id.iv_ggli_fightPhoto);
			holder.tv_ggli_fighterOne=(TextView) convertView.findViewById(R.id.tv_ggli_fighterOne);
			holder.tv_ggli_fighterTwo=(TextView) convertView.findViewById(R.id.tv_ggli_fighterTwo);
			holder.tv_ggli_gameRuler=(TextView) convertView.findViewById(R.id.tv_ggli_gameRuler);
			holder.iv_ggli_handOn=(ImageView) convertView.findViewById(R.id.iv_ggli_handOn);
			holder.tv_ggli_handOnNum=(TextView) convertView.findViewById(R.id.tv_ggli_handOnNum);
			holder.iv_ggli_handUp=(ImageView) convertView.findViewById(R.id.iv_ggli_handUp);
			holder.tv_ggli_handUpNum=(TextView) convertView.findViewById(R.id.tv_ggli_handUpNum);
			holder.btn_ggli_join=(Button) convertView.findViewById(R.id.btn_ggli_join);
			holder.tv_gglig_timeH=(TextView) convertView.findViewById(R.id.tv_gglig_timeH);
			holder.superdream_progressa=(ProgressBar) convertView.findViewById(R.id.superdream_progressa);
			holder.superdream_progressb=(ProgressBar) convertView.findViewById(R.id.superdream_progressb);
			holder.jiayoujingcai_bisaijingcai= (LinearLayout) convertView.findViewById(R.id.jiayoujingcai_bisaijingcai);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		if (dataList.get(position).getJoina().equals("1")) {
			holder.btn_ggli_join.setText("已参与");
			holder.btn_ggli_join.setTextColor(Color.RED);
			holder.btn_ggli_join.setBackgroundColor(Color.WHITE);
		}else if (dataList.get(position).getJoinb().equals("1")) {
			holder.btn_ggli_join.setText("已参与");
			holder.btn_ggli_join.setTextColor(Color.RED);
			holder.btn_ggli_join.setBackgroundColor(Color.WHITE);
		}else{
			holder.btn_ggli_join.setText("未参与");
			holder.btn_ggli_join.setTextColor(Color.WHITE);
			holder.btn_ggli_join.setBackgroundColor(Color.RED);
		}

		holder.btn_ggli_join.setTag(position);
		if (holder.btn_ggli_join.getText().toString().equals("未参与")) {
			holder.btn_ggli_join.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//将所需要的参数给传递过去
					index = (int) v.getTag();
					Intent intent=new Intent(context, GameGussingDetailActivity.class);
					intent.putExtra("liveguess_id",dataList.get(index).getLiveguess_id());
					intent.putExtra("liveguess_pic",dataList.get(index).getLiveguess_pic());
					intent.putExtra("liveguess_time",dataList.get(index).getLiveguess_time());
					intent.putExtra("liveguess_title",dataList.get(index).getLiveguess_title());
					intent.putExtra("liveguess_playera",dataList.get(index).getLiveguess_playera());
					intent.putExtra("liveguess_playerb",dataList.get(index).getLiveguess_playerb());
					intent.putExtra("liveguess_content",dataList.get(index).getLiveguess_content());
					intent.putExtra("playera_head",dataList.get(index).getPlayera_head());
					intent.putExtra("playera_name",dataList.get(index).getPlayera_name());
					intent.putExtra("playerb_head",dataList.get(index).getPlayerb_head());
					intent.putExtra("playerb_name",dataList.get(index).getPlayerb_name());
					intent.putExtra("joina",dataList.get(index).getJoina());
					intent.putExtra("joinb",dataList.get(index).getJoinb());
					intent.putExtra("suma",dataList.get(index).getSuma());
					intent.putExtra("sumb",dataList.get(index).getSumb());
					intent.putExtra("concern",dataList.get(index).getConcern());
					context.startActivity(intent);
				}
			});
		}
		holder.jiayoujingcai_bisaijingcai.setId(position);
		holder.jiayoujingcai_bisaijingcai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//将所需要的参数给传递过去
				int indexs = (int) v.getId();
				Intent intent=new Intent(context, GameGussingDetailActivity.class);
				intent.putExtra("liveguess_id",dataList.get(indexs).getLiveguess_id());
				intent.putExtra("liveguess_pic",dataList.get(indexs).getLiveguess_pic());
				intent.putExtra("liveguess_time",dataList.get(indexs).getLiveguess_time());
				intent.putExtra("liveguess_title",dataList.get(indexs).getLiveguess_title());
				intent.putExtra("liveguess_playera",dataList.get(indexs).getLiveguess_playera());
				intent.putExtra("liveguess_playerb",dataList.get(indexs).getLiveguess_playerb());
				intent.putExtra("liveguess_content",dataList.get(indexs).getLiveguess_content());
				intent.putExtra("playera_head",dataList.get(indexs).getPlayera_head());
				intent.putExtra("playera_name",dataList.get(indexs).getPlayera_name());
				intent.putExtra("playerb_head",dataList.get(indexs).getPlayerb_head());
				intent.putExtra("playerb_name",dataList.get(indexs).getPlayerb_name());
				intent.putExtra("joina",dataList.get(indexs).getJoina());
				intent.putExtra("joinb",dataList.get(indexs).getJoinb());
				intent.putExtra("suma",dataList.get(indexs).getSuma());
				intent.putExtra("sumb",dataList.get(indexs).getSumb());
				intent.putExtra("concern",dataList.get(indexs).getConcern());
				context.startActivity(intent);
			}
		});
		String strTime = configUtils.getStrTime(dataList.get(position).getLiveguess_time());

		holder.tv_ggli_fighterOne.setText(dataList.get(position).getPlayera_name());
		holder.tv_ggli_fighterTwo.setText(dataList.get(position).getPlayerb_name());
		holder.tv_ggli_gameRuler.setText(dataList.get(position).getLiveguess_title());
		holder.tv_ggli_handOnNum.setText(dataList.get(position).getSuma());
		holder.tv_ggli_handUpNum.setText(dataList.get(position).getSumb());
		holder.tv_gglig_timeH.setText(strTime);
		holder.tv_gglig_timeH.setVisibility(View.GONE);
		if (dataList.get(position).getJoina().equals("1")) {
			holder.iv_ggli_handOn.setImageResource(R.drawable.zuodianzan_1);
		}else if (dataList.get(position).getJoina().equals("0")){
			holder.iv_ggli_handOn.setImageResource(R.drawable.zuodianzan_0);
		}
		if (dataList.get(position).getJoinb().equals("1")) {
			holder.iv_ggli_handUp.setImageResource(R.drawable.zuodianzan_1);
		}else if (dataList.get(position).getJoinb().equals("0")){
			holder.iv_ggli_handUp.setImageResource(R.drawable.zuodianzan_0);
		}
		x.image().bind(holder.iv_ggli_fightPhoto, Config.ip+dataList.get(position).getLiveguess_pic());
		holder.superdream_progressa.setProgress(Integer.parseInt(dataList.get(position).getSuma()));
		holder.superdream_progressb.setProgress(Integer.parseInt(dataList.get(position).getSumb()));
		holder.superdream_progressa.setVisibility(View.GONE);
		holder.superdream_progressb.setVisibility(View.GONE);
		return convertView;
		
	}
	
	class ViewHolder{
		ImageView iv_ggli_fightPhoto;
		TextView tv_ggli_fighterOne;
		TextView tv_ggli_fighterTwo;
		TextView tv_ggli_gameRuler;//65公斤级常规赛
		ImageView iv_ggli_handOn;
		TextView tv_ggli_handOnNum;//添加竞猜
		ImageView iv_ggli_handUp;
		TextView tv_ggli_handUpNum;
		Button btn_ggli_join;
		TextView tv_gglig_timeH;
		ProgressBar superdream_progressa;
		ProgressBar superdream_progressb;
		LinearLayout jiayoujingcai_bisaijingcai;
		
	}


}
