package com.smarthub.smhubadmob.ads.smcustom_ads;

import com.google.gson.annotations.SerializedName;

public class SmBannerAd {

    @SerializedName("media_view")
    private String mediaView;

    @SerializedName("medium_url")
    public String mediumUrl;

    @SerializedName("large_url")
    public String largeUrl;

    @SerializedName("app_id")
    private String app_id;

    @SerializedName("cta_launch_url")
    private String cta_launch_url;

    @SerializedName("impression_count")
    private int impressionCount;

    public String getMediaView() {
        return mediaView;
    }

    public void setMediaView(String mediaView) {
        this.mediaView = mediaView;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public int getImpressionCount() {
        return impressionCount;
    }

    public void setImpressionCount(int impressionCount) {
        this.impressionCount = impressionCount;
    }

    public String getCta_launch_url() {
        return cta_launch_url;
    }

    public void setCta_launch_url(String cta_launch_url) {
        this.cta_launch_url = cta_launch_url;
    }
}
