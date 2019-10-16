package com.yx.appgameservice.enums;

import java.util.HashMap;
import java.util.Map;


public enum GameChannelCode {

    GAME_AG(1, "AG游戏",true),
    GAME_EBET(2, "EBET游戏",true),
    GAME_BBIN(3, "BBIN游戏",false),
    GAME_PT(4, "PT游戏",true),
    GAME_MG(5, "MG游戏",false),
    GAME_GG(6, "GG游戏",false),
    GAME_188(7, "贝博体育",true),
    GAME_LB(8, "LB彩票",true),
    GAME_KY(9, "开元棋牌",false),
    GAME_HLQP(10, "欢乐棋牌",true),
    GAME_GMQP(11, "贝博棋牌",true),
    GAME_188_LIVE(13, "小金真人",true),
    GAME_5GM_LTY(12, "5GM彩票",false),
    GAME_VIRTUAL_SPORT(16, "虚拟体育",false),
    GAME_IM_PP(17, "PP电子",true),
    GAME_IM_ESPORT(18, "IM电竞",true),
    GAME_IM_SW(19, "双赢彩票",true),
    GAME_VR(20, "VR竞速娱乐",false),
    GAME_MW(23, "MW游戏",true),
    GAME_XYQP(24, "幸运棋牌",false);

    private int channel;
    private String code;
    private boolean isOpen;

    GameChannelCode(int channel, String code, boolean isOpen) {
        this.channel = channel;
        this.code = code;
        this.isOpen = isOpen;
    }

    public int getChannel() {
        return channel;
    }

    public String getCode() {
        return code;
    }

    public boolean isOpen() {
        return isOpen;
    }

    static Map<Integer,String> bbMap = new HashMap<>();

    static {
        bbMap.put(0, "中心");
        bbMap.put(GameChannelCode.GAME_AG.getChannel(),"AG平台");
        bbMap.put(GameChannelCode.GAME_EBET.getChannel(),"Ebet平台");
        bbMap.put(GameChannelCode.GAME_BBIN.getChannel(),"BBIN");
        bbMap.put(GameChannelCode.GAME_PT.getChannel(),"PT电子");
        bbMap.put(GameChannelCode.GAME_MG.getChannel(),"MG");
        bbMap.put(GameChannelCode.GAME_GG.getChannel(),"GG");
        bbMap.put(GameChannelCode.GAME_188.getChannel(),"贝博体育");
        bbMap.put(GameChannelCode.GAME_KY.getChannel(),"开元棋牌");
        bbMap.put(GameChannelCode.GAME_LB.getChannel(),"LB彩票");
        bbMap.put(GameChannelCode.GAME_HLQP.getChannel(),"欢乐棋牌");
        bbMap.put(GameChannelCode.GAME_5GM_LTY.getChannel(),"5GM彩票");
        bbMap.put(GameChannelCode.GAME_188_LIVE.getChannel(),"小金真人");
        bbMap.put(GameChannelCode.GAME_VIRTUAL_SPORT.getChannel(),"虚拟体育");
        bbMap.put(GameChannelCode.GAME_IM_PP.getChannel(),"PP电子");
        bbMap.put(GameChannelCode.GAME_IM_ESPORT.getChannel(),"IM电竞");
        bbMap.put(GameChannelCode.GAME_IM_SW.getChannel(),"双赢彩票");
        bbMap.put(GameChannelCode.GAME_VR.getChannel(),"VR竞速");
        bbMap.put(GameChannelCode.GAME_MW.getChannel(),"MW平台");
        bbMap.put(GameChannelCode.GAME_XYQP.getChannel(),"幸运棋牌");
        bbMap.put(GameChannelCode.GAME_GMQP.getChannel(),"贝博棋牌");
    }
    public static final int CHANNEL_AG = 1;
    public static final int CHANNEL_EBET = 2;
    public static final int CHANNEL_BBIN = 3;
    public static final int CHANNEL_PT = 4;
    public static final int CHANNEL_MG = 5;
    public static final int CHANNEL_GG = 6;
    public static final int CHANNEL_188 = 7;
    public static final int CHANNEL_LB = 8;
    public static final int CHANNEL_KY = 9;
    public static final int CHANNEL_HLQP = 10;
    public static final int CHANNEL_GMQP = 11;
    public static final int CHANNEL_5GM_LTY = 12;
    public static final int CHANNEL_188_LIVE = 13;
    public static final int CHANNEL_VR = 20;
    public static final int CHANNEL_SELF = 0;
    public static final int CHANNEL_XYQP = 24;

    public static final int CHANNEL_MW_FISH = 15;
    public static final int CHANNEL_VIRTUAL_SPORT = 16;
    public static final int CHANNEL_IM_PP = 17;
    public static final int CHANNEL_IM_ESPORT = 18;
    public static final int CHANNEL_IM_SW = 19;
    public static final int CHANNEL_GAME_MW = 23;


    /**
     *  根据channelId返回游戏状态
     * @param channelId
     * @return
     */
    public static boolean getGameStatusByChannel(Integer channelId) {
        if(channelId.equals(0)){ //转账时代表中心钱包
            return true;
        }
        for (GameChannelCode channel :GameChannelCode.values()) {
            if(channelId==channel.getChannel()){
                return channel.isOpen();
            }
        }
        return false;
    }

}
