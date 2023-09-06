package com.smarthub.smhubadmob.ads.smcustom_ads;

import com.google.gson.annotations.SerializedName;

public class SmNativeAd {

    @SerializedName("app_icon")
    private String appIcon;

    @SerializedName("app_title")
    private String appTitle;

    @SerializedName("ad_advertiser")
    private String adAdvertiser;

    @SerializedName("media_view")
    private String mediaView;

    @SerializedName("ad_description")
    private String adDescription;

    @SerializedName("app_id")
    private String app_id;

    @SerializedName("cta_launch_url")
    private String cta_launch_url;

    @SerializedName("impression_count")
    private int impressionCount;

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getAdAdvertiser() {
        return adAdvertiser;
    }

    public void setAdAdvertiser(String adAdvertiser) {
        this.adAdvertiser = adAdvertiser;
    }

    public String getMediaView() {
        return mediaView;
    }

    public void setMediaView(String mediaView) {
        this.mediaView = mediaView;
    }

    public String getAdDescription() {
        return adDescription;
    }

    public void setAdDescription(String adDescription) {
        this.adDescription = adDescription;
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
