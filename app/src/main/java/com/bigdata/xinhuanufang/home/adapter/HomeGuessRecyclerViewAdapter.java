package com.bigdata.xinhuanufang.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseViewHolder;
import com.bigdata.xinhuanufang.home.bean.liveListBean;
import com.bigdata.xinhuanufang.main.GameGussingDetailActivity;
import com.bigdata.xinhuanufang.main.videoplayer.LivePlayerActivity;
import com.bigdata.xinhuanufang.main.videoplayer.VideoConfig;
import com.bigdata.xinhuanufang.utils.Config;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzq on 2017/5/15.
 */
public class HomeGuessRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context mContext;

    private List<liveListBean.LiveBean> liveBeanList = new ArrayList<>();

    public HomeGuessRecyclerViewAdapter(List<liveListBean.LiveBean> liveBeanList, Context context) {
        this.liveBeanList= liveBeanList;
        this.mContext =context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_guess,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return liveBeanList==null?0:liveBeanList.size();
    }


    private class ViewHolder extends BaseViewHolder {
        private TextView num;
        private ImageView audience;
        private ImageView guess;
        private ImageView state;
        private  TextView title;
        public ViewHolder(View itemView)
        {
            super(itemView, HomeGuessRecyclerViewAdapter.this.mContext);
        }
        public ViewHolder(View itemView, Context context) {
            super(itemView, context);
        }

        @Override
        public void setData() {


        }



        @Override
        public void initView() {


               num = (TextView) itemView.findViewById(R.id.tv_mgi_hunmanNum);
            audience = (ImageView) itemView.findViewById(R.id.imageView1);
            guess = (ImageView) itemView.findViewById(R.id.iv_mgi_gamePhoto);
            state = (ImageView) itemView.findViewById(R.id.iv_mgvi_zhibozhong);
            title = (TextView) itemView.findViewById(R.id.tv_mgi_title);
            audience.setVisibility(View.GONE);
            num.setVisibility(View.GONE);

        }


        public void bindData(final int position){

            String a=liveBeanList.get(position).getLive_status();
            if (liveBeanList.get(position).getLive_status().equals("")) {
                //预告
                title.setText(liveBeanList.get(position).getLiveguess_title());

                Glide.with(mContext).load( Config.ip+liveBeanList.get(position).getLive_pic()).into(guess);
                Log.e("TAG", "tupian"+Config.ip+liveBeanList.get(position).getLive_pic());
                state.setImageResource(R.drawable.sy_weikaishi);
            }else if (liveBeanList.get(position).getLive_status().equals("1")){

                title.setText(liveBeanList.get(position).getLive_title());

                Glide.with(mContext).load( Config.ip+liveBeanList.get(position).getLive_pic()).into(guess);
                Log.e("TAG", "tupian"+Config.ip+liveBeanList.get(position).getLive_pic());
                Log.e("TAG", "timu"+liveBeanList.get(position).getLive_title());
            }
            else if (liveBeanList.get(position).getLive_status().equals("2")){
                title.setText(liveBeanList.get(position).getLive_title());
                Log.e("TAG", "tupian"+Config.ip+liveBeanList.get(position).getLive_pic());
                Glide.with(mContext).load( Config.ip+liveBeanList.get(position).getLive_pic()).into(guess);
//			holder.tv_mgi_title.setVisibility(View.GONE);
//			holder.tv_mgi_hunmanNum.setVisibility(View.GONE);
//			holder.iv_mgi_gamePhoto.setVisibility(View.GONE);
//			holder.imageView1.setVisibility(View.GONE);
            }

            guess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VideoConfig.mActivityType = VideoConfig.ACTIVITY_TYPE_LIVE_PLAY;
                    if (liveBeanList.get(position).getLive_status().equals("")) {
                        //跳转到比赛预告里面activity
                        Intent intent=new Intent(mContext, GameGussingDetailActivity.class);
                        intent.putExtra("liveguess_id",liveBeanList.get(position).getLiveguess_id());
                        intent.putExtra("liveguess_pic",liveBeanList.get(position).getLiveguess_pic());
                        intent.putExtra("liveguess_time",liveBeanList.get(position).getLiveguess_time());
                        intent.putExtra("liveguess_title",liveBeanList.get(position).getLiveguess_title());
                        intent.putExtra("liveguess_playera",liveBeanList.get(position).getLiveguess_playera());
                        intent.putExtra("liveguess_playerb",liveBeanList.get(position).getLiveguess_playerb());
                        intent.putExtra("liveguess_content",liveBeanList.get(position).getLiveguess_content());
                        intent.putExtra("playera_head",liveBeanList.get(position).getPlayera_head());
                        intent.putExtra("playera_name",liveBeanList.get(position).getPlayera_name());
                        intent.putExtra("playerb_head",liveBeanList.get(position).getPlayerb_head());
                        intent.putExtra("playerb_name",liveBeanList.get(position).getPlayerb_name());
                        intent.putExtra("joina",liveBeanList.get(position).getJoina());
                        intent.putExtra("joinb",liveBeanList.get(position).getJoinb());
                        intent.putExtra("suma",liveBeanList.get(position).getSuma());
                        intent.putExtra("sumb",liveBeanList.get(position).getSumb());
                        intent.putExtra("concern",liveBeanList.get(position).getConcern());
//                    startActivity(intent);
                        ((Activity)mContext).startActivityForResult(intent,88);
                    }else  if (liveBeanList.get(position).getLive_status().equals("1")) {
                        //正在直播
                        Intent intent = new Intent(mContext, LivePlayerActivity.class);
                        intent.putExtra("playUrl", liveBeanList.get(position).getLive_downstream_address());
                        ((Activity)mContext). startActivity(intent);
                    }else  if (liveBeanList.get(position).getLive_status().equals("2")) {
                        //正在直播
                        Intent intent = new Intent(mContext, LivePlayerActivity.class);
                        intent.putExtra("playUrl", liveBeanList.get(position).getLive_downstream_address());
                        ((Activity)mContext).startActivity(intent);
                    }
                }
            });

        }

    }





}
