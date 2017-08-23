package com.bigdata.xinhuanufang.mine;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bigdata.xinhuanufang.R;
/**
 * 竞猜记录
 * @author weiyu$
 *
 */
public class GuessingFragmentRecord extends Fragment{
	private ListView gameGussingLV;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_gamegussing, container,
				false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		gameGussingLV = (ListView) view
				.findViewById(R.id.lv_fgg_gameGus);
	}
}
