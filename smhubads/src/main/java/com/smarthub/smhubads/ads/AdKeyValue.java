package com.smarthub.smhubads.ads;

import com.google.gson.annotations.SerializedName;

public class AdKeyValue {

    @SerializedName("key")
    private String key;
    @SerializedName("banner")
    private boolean banner;
    @SerializedName("native")
    private boolean nativeAd;
    @SerializedName("interstitial")
    private boolean interstitial;
    @SerializedName("rewarded")
    private boolean rewarded;

    public boolean isRewarded() {
        return rewarded;
    }

    public String getKey() {
        return key;
    }

    public boolean isBanner() {
        return banner;
    }

    public boolean isNativeAd() {
        return nativeAd;
    }

    public boolean isInterstitial() {
        return interstitial;
    }
}
