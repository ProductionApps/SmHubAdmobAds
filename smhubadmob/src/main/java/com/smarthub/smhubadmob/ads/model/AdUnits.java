package com.smarthub.smhubadmob.ads.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AdUnits {

//    admob ad units
    @SerializedName("adm_interstitial_unit")
    public String admInterstitial;
    @SerializedName("adm_native_unit")
    public String admNative;
    @SerializedName("adm_banner_unit")
    public String admBanner;
    @SerializedName("adm_rewarded_unit")
    public String admRewarded;
    @SerializedName("adm_reward_interstitial_unit")
    public String admRewardedInterstitial;
    @SerializedName("adm_app_open_unit")
    public String admAppOpen;

    public AdUnits(){

    }

    public void setUpAdmUnits(String admInterstitial, String admNative, String admBanner, String admAppOpen, String admRewarded, String admRewardedInterstitial) {
        this.admInterstitial = admInterstitial;
        this.admNative = admNative;
        this.admBanner = admBanner;
        this.admAppOpen = admAppOpen;
        this.admRewarded = admRewarded;
        this.admRewardedInterstitial = admRewardedInterstitial;
    }

   public void defaultInit(){

        this.admInterstitial = "ca-app-pub-3940256099942544/1033173712";
        this.admNative = "ca-app-pub-3940256099942544/2247696110";
        this.admBanner = "ca-app-pub-3940256099942544/6300978111";
        this.admRewarded = "ca-app-pub-3940256099942544/5224354917";
        this.admRewardedInterstitial = "ca-app-pub-3940256099942544/5354046379";
        this.admAppOpen = "ca-app-pub-3940256099942544/3419835294";

    }
}
