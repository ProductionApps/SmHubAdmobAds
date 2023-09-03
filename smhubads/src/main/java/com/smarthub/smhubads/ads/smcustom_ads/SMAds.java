package com.smarthub.smhubads.ads.smcustom_ads;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SMAds {

    @SerializedName("ads")
    private List<SmNativeAd> ads;

    @SerializedName("banner_ads")
    private List<SmBannerAd> smBannerAds;

    public List<SmNativeAd> getAds() {
        return ads;
    }

    public void setAds(List<SmNativeAd> ads) {
        this.ads = ads;
    }

    public List<SmBannerAd> getSmBannerAds() {
        return smBannerAds;
    }
}
