package com.bigdata.xinhuanufang.main.videoplayer;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class LivePlayerActivity extends Activity implements ITXLivePlayListener {
    private static final String TAG = LivePlayerActivity.class.getSimpleName();

    private TXLivePlayer mLivePlayer = null;
    private boolean mVideoPlay;
    private TXCloudVideoView mPlayerView;
    private ImageView mLoadingView;
    private boolean mHWDecode = false;

    private ScrollView mScrollView;
    private SeekBar mSeekBar;
    private TextView mTextDuration;
    private TextView mTextStart;

    private static final int CACHE_STRATEGY_FAST = 1; // 极速
    private static final int CACHE_STRATEGY_SMOOTH = 2; // 流畅
    private static final int CACHE_STRATEGY_AUTO = 3; // 自动

    private static final int CACHE_TIME_FAST = 1;
    private static final int CACHE_TIME_SMOOTH = 5;

    private static final int CACHE_TIME_AUTO_MIN = 5;
    private static final int CACHE_TIME_AUTO_MAX = 10;

    private int mCacheStrategy = 0;

    private int mCurrentRenderMode;

    private long mTrackingTouchTS = 0;
    private boolean mStartSeek = false;
    private boolean mVideoPause = false;
    private int mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
    private TXLivePlayConfig mPlayConfig;
    // 播放地址
    private String playUrl = "";

    @ViewInject(R.id.btnPlay)
    private ImageView btnPlay;

    @ViewInject(R.id.cancle)
    private ImageView cancle;

    @ViewInject(R.id.chroll_ui)
    private LinearLayout chroll_ui;

    @ViewInject(R.id.play_progress)
    private LinearLayout play_progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 保持CPU
        setContentView(R.layout.activity_liveplay);
        mCurrentRenderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
        mPlayConfig = new TXLivePlayConfig();
        x.view().inject(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        playUrl = intent.getStringExtra("playUrl");
        System.out.println("直播地址"+playUrl);
//        playUrl = "http://200002008.vod.myqcloud.com/200002008_db76cd46dc5c11e6bde4db5b989d480a.f30.mp4";
        if (mLivePlayer == null) {
            mLivePlayer = new TXLivePlayer(this);
        }
        mPlayerView = (TXCloudVideoView) findViewById(R.id.video_view);
        mLoadingView = (ImageView) findViewById(R.id.loadingImageView);
        mVideoPlay = false;
        mScrollView = (ScrollView) findViewById(R.id.scrollview);
        mScrollView.setVisibility(View.GONE);
        btnPlay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mVideoPlay) {
                    if (mPlayType == TXLivePlayer.PLAY_TYPE_VOD_FLV
                            || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_HLS
                            || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_MP4) {
                        if (mVideoPause) {
                            mLivePlayer.resume();
                            btnPlay.setImageResource(R.drawable.zbj_huibo_zanting);
                        } else {
                            mLivePlayer.pause();
                            btnPlay.setImageResource(R.drawable.zbj_huibo_kais);
                        }
                        mVideoPause = !mVideoPause;

                    } else {
                        stopPlayRtmp();
                        mVideoPlay = !mVideoPlay;
                    }

                } else {
                    if (startPlayRtmp(playUrl)) {
                        mVideoPlay = !mVideoPlay;
                    }
                }
            }
        });
        cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean bFromUser) {
                mTextStart.setText(String.format("%02d:%02d", progress / 60,
                        progress % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mStartSeek = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mLivePlayer != null) {
                    mLivePlayer.seek(seekBar.getProgress());
                }
                mTrackingTouchTS = System.currentTimeMillis();
                mStartSeek = false;
            }
        });

        mTextDuration = (TextView) findViewById(R.id.duration);
        mTextStart = (TextView) findViewById(R.id.play_start);
        mTextDuration.setTextColor(Color.rgb(255, 255, 255));
        mTextStart.setTextColor(Color.rgb(255, 255, 255));

        this.setCacheStrategy(CACHE_STRATEGY_AUTO);

        // 进入界面开始播放
        if (startPlayRtmp(playUrl)) {
            mVideoPlay = !mVideoPlay;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
        }
        if (mPlayerView != null) {
            mPlayerView.onDestroy();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mLivePlayer.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPlayType == TXLivePlayer.PLAY_TYPE_VOD_FLV
                || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_HLS
                || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_MP4) {
            if (mLivePlayer != null) {
                mLivePlayer.pause();
            }
        } else {
            stopPlayRtmp();
        }

        if (mPlayerView != null) {
            mPlayerView.onPause();
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        if (mVideoPlay && !mVideoPause) {
            // 退到后台重新回来继续播放，这里加延迟目的：让上一次的关闭操作执行完成
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mPlayType == TXLivePlayer.PLAY_TYPE_VOD_FLV
                            || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_HLS
                            || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_MP4) {
                        if (mLivePlayer != null) {
                            mLivePlayer.resume();
                        }
                    } else {
                        startPlayRtmp(playUrl);
                    }
                }
            }, 1200);
        }

        if (mPlayerView != null) {
            mPlayerView.onResume();
        }
    }

    private boolean checkPlayUrl(final String playUrl) {
        if (TextUtils.isEmpty(playUrl)
                || (!playUrl.startsWith("http://")
                && !playUrl.startsWith("https://") && !playUrl
                .startsWith("rtmp://"))) {
            Toast.makeText(getApplicationContext(),
                    "播放地址不合法，目前仅支持rtmp,flv,hls,mp4播放方式!", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        VideoConfig.mActivityType = VideoConfig.ACTIVITY_TYPE_LIVE_PLAY;
        switch (VideoConfig.mActivityType) {
            case VideoConfig.ACTIVITY_TYPE_LIVE_PLAY: {
                if (playUrl.startsWith("rtmp://")) {
                    mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
                } else if ((playUrl.startsWith("http://") || playUrl
                        .startsWith("https://")) && playUrl.contains(".flv")) {
                    mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
                } else {
                    Toast.makeText(getApplicationContext(),
                            "播放地址不合法，直播目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT)
                            .show();
                    return false;
                }
            }
            break;
            case VideoConfig.ACTIVITY_TYPE_VOD_PLAY: {
                if (playUrl.startsWith("http://") || playUrl.startsWith("https://")) {
                    if (playUrl.contains(".flv")) {
                        mPlayType = TXLivePlayer.PLAY_TYPE_VOD_FLV;
                    } else if (playUrl.contains(".m3u8")) {
                        mPlayType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
                    } else if (playUrl.toLowerCase().contains(".mp4")) {
                        mPlayType = TXLivePlayer.PLAY_TYPE_VOD_MP4;
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "播放地址不合法，点播目前仅支持flv,hls,mp4播放方式!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "播放地址不合法，点播目前仅支持flv,hls,mp4播放方式!", Toast.LENGTH_SHORT)
                            .show();
                    return false;
                }
            }
            break;
            default:
                Toast.makeText(getApplicationContext(),
                        "播放地址不合法，目前仅支持rtmp,flv,hls,mp4播放方式!", Toast.LENGTH_SHORT)
                        .show();
                return false;
        }
        return true;
    }

    private boolean startPlayRtmp(String playUrl) {

        if (!checkPlayUrl(playUrl)) {
            return false;
        }

        btnPlay.setImageResource(R.drawable.zbj_huibo_zanting);
        mLivePlayer.setPlayerView(mPlayerView);
        mLivePlayer.setPlayListener(this);

        // 硬件加速在1080p解码场景下效果显著，但细节之处并不如想象的那么美好：
        // (1) 只有 4.3 以上android系统才支持
        // (2) 兼容性我们目前还仅过了小米华为等常见机型，故这里的返回值您先不要太当真
        mLivePlayer.enableHardwareDecode(mHWDecode);
        if (VideoConfig.VIDEO_SCREEN_ROTATION == TXLiveConstants.RENDER_ROTATION_LANDSCAPE) {
            LivePlayerActivity.this
                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (VideoConfig.VIDEO_SCREEN_ROTATION == TXLiveConstants.RENDER_ROTATION_PORTRAIT) {
            //必須設置為橫屏的時候，可以更改這裡的代碼
            LivePlayerActivity.this
                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
//         mLivePlayer.setRenderRotation(VideoConfig.VIDEO_SCREEN_ROTATION);
        mLivePlayer.setRenderMode(mCurrentRenderMode);
        // 设置播放器缓存策略
        // 这里将播放器的策略设置为自动调整，调整的范围设定为1到4s，您也可以通过setCacheTime将播放器策略设置为采用
        // 固定缓存时间。如果您什么都不调用，播放器将采用默认的策略（默认策略为自动调整，调整范围为1到4s）
        // mLivePlayer.setCacheTime(5);
        mLivePlayer.setConfig(mPlayConfig);

        int result = mLivePlayer.startPlay(playUrl, mPlayType); // result返回值：0
        // success; -1
        // empty url; -2
        // invalid url;
        // -3 invalid
        // playType;
        if (result == -2) {
            Toast.makeText(getApplicationContext(),
                    "非腾讯云链接地址，若要放开限制，请联系腾讯云商务团队", Toast.LENGTH_SHORT).show();
        }
        if (result != 0) {
            // mBtnPlay.setBackgroundResource(R.drawable.play_start);
            btnPlay.setImageResource(R.drawable.zbj_huibo_kais);
            return false;
        }

//        mLivePlayer.setLogLevel(TXLiveConstants.LOG_LEVEL_DEBUG);

        startLoadingAnimation();

        return true;
    }

    private void stopPlayRtmp() {
        btnPlay.setImageResource(R.drawable.zbj_huibo_kais);
        // mBtnPlay.setBackgroundResource(R.drawable.play_start);
        stopLoadingAnimation();
        if (mLivePlayer != null) {
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
        }
    }

    @Override
    public void onPlayEvent(int event, Bundle param) {
        if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            stopLoadingAnimation();
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
            if (mStartSeek) {
                return;
            }
            int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);
            int duration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION);
            long curTS = System.currentTimeMillis();

            // 避免滑动进度条松开的瞬间可能出现滑动条瞬间跳到上一个位置
            if (Math.abs(curTS - mTrackingTouchTS) < 500) {
                return;
            }
            mTrackingTouchTS = curTS;

            if (mSeekBar != null) {
                mSeekBar.setProgress(progress);
            }
            if (mTextStart != null) {
                mTextStart.setText(String.format("%02d:%02d", progress / 60,
                        progress % 60));
            }
            if (mTextDuration != null) {
                mTextDuration.setText(String.format("%02d:%02d", duration / 60,
                        duration % 60));
            }
            if (mSeekBar != null) {
                mSeekBar.setMax(duration);
            }
            return;
        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT
                || event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            stopPlayRtmp();
            mVideoPlay = false;
            mVideoPause = false;
            if (mTextStart != null) {
                mTextStart.setText("00:00");
            }
            if (mSeekBar != null) {
                mSeekBar.setProgress(0);
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {
            startLoadingAnimation();
        }

        String msg = param.getString(TXLiveConstants.EVT_DESCRIPTION);
        if (mLivePlayer != null) {
            mLivePlayer.onLogRecord("[event:" + event + "]" + msg + "\n");
        }
        if (event < 0) {
            Toast.makeText(getApplicationContext(),
                    param.getString(TXLiveConstants.EVT_DESCRIPTION),
                    Toast.LENGTH_SHORT).show();
        }

        else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            stopLoadingAnimation();
        }
    }

    @Override
    public void onNetStatus(Bundle status) {
        Log.d(TAG, "Current status: " + status.toString());
    }

    public void setCacheStrategy(int nCacheStrategy) {
        if (mCacheStrategy == nCacheStrategy)
            return;
        mCacheStrategy = nCacheStrategy;

        switch (nCacheStrategy) {
            case CACHE_STRATEGY_FAST:
                mPlayConfig.setAutoAdjustCacheTime(true);
                mPlayConfig.setMaxAutoAdjustCacheTime(CACHE_TIME_FAST);
                mPlayConfig.setMinAutoAdjustCacheTime(CACHE_TIME_FAST);
                mLivePlayer.setConfig(mPlayConfig);
                break;

            case CACHE_STRATEGY_SMOOTH:
                mPlayConfig.setAutoAdjustCacheTime(false);
                mPlayConfig.setCacheTime(CACHE_TIME_SMOOTH);
                mLivePlayer.setConfig(mPlayConfig);
                break;

            case CACHE_STRATEGY_AUTO:
                mPlayConfig.setAutoAdjustCacheTime(true);
                mPlayConfig.setMaxAutoAdjustCacheTime(CACHE_TIME_AUTO_MAX);
                mPlayConfig.setMinAutoAdjustCacheTime(CACHE_TIME_AUTO_MIN);
                mLivePlayer.setConfig(mPlayConfig);
                break;

            default:
                break;
        }
    }

    private void startLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
            ((AnimationDrawable) mLoadingView.getDrawable()).start();
        }
    }

    private void stopLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
            ((AnimationDrawable) mLoadingView.getDrawable()).stop();
        }
    }

}