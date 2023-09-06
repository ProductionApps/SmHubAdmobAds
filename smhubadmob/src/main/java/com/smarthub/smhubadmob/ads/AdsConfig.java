package com.smarthub.smhubadmob.ads;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AdsConfig {

    @SerializedName("native_feed_count")
    private int nativeFeedCount = 5;

    @SerializedName("blockedDeviceList")
    public List<String> blockedDeviceList = new ArrayList();

    @SerializedName("emu_stop")
    public boolean emuStop = false;

    @SerializedName("enable")
    public boolean enable = false;

    @SerializedName("limitedForNew")
    public boolean limitedForNew = false;

    @SerializedName("rate_us_count")
    public int rateUsCount = 5;

    @SerializedName("limited_count")
    public int limitedCount = 2;

    @SerializedName("daily_download_limit")
    public int dailyDownloadLimit = 5;

    @SerializedName("max_click")
    private int maxClick = 2;
    @SerializedName("native_feed_increamental_count")
    private int native_feed_increamental_count = 3;

    @SerializedName("interstitial_min_time")
    public int interstitialMinTime;

    public int getNativeFeedCount() {
        return nativeFeedCount;
    }

    public int getNative_feed_increamental_count() {
        return native_feed_increamental_count;
    }

    public void setNative_feed_increamental_count(int native_feed_increamental_count) {
        this.native_feed_increamental_count = native_feed_increamental_count;
    }
    public int getMaxClick() {
        return maxClick;
    }

}
