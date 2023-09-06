package com.smarthub.smhubadmob.ads;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdsInfoItem {

    @SerializedName("adslist")
    private List<AdKeyValue> adslist;

    @SerializedName("config")
    private AdsConfig config;

    public List<AdKeyValue> getAdslist() {
        return adslist;
    }

    public void setAdslist(List<AdKeyValue> adslist) {
        this.adslist = adslist;
    }

    public AdsConfig getConfig() {
        return config;
    }

    public void setConfig(AdsConfig config) {
        this.config = config;
    }
}
