package com.bigdata.xinhuanufang.bean;

/**
 * Created by weiyu$ on 2017/4/10.
 * 我的模块抽奖游戏
 */

public class MineChouJiangGame {
    /**
     *  "game_id":"19",
     "game_gloves":"200",
     "game_date5":"1491460215"
     */

    private String game_id;
    private String game_gloves;
    private String game_date5;

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getGame_gloves() {
        return game_gloves;
    }

    public void setGame_gloves(String game_gloves) {
        this.game_gloves = game_gloves;
    }

    public String getGame_date5() {
        return game_date5;
    }

    public void setGame_date5(String game_date5) {
        this.game_date5 = game_date5;
    }

    public MineChouJiangGame(String game_id, String game_gloves, String game_date5) {
        this.game_id = game_id;
        this.game_gloves = game_gloves;
        this.game_date5 = game_date5;
    }
}
