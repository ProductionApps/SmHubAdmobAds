package com.smarthub.smhubads.ads.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AdUnits {

//    facebook ad units
    @SerializedName("fb_interstitial_unit")
    public String fbInterstitial;
    @SerializedName("fb_native_unit")
    public String fbNative;
    @SerializedName("fb_banner_unit")
    public String fbBanner;

    @SerializedName("unity_interstitial_unit")
    public String unityInterstitial="Interstitial_Android";

    @SerializedName("unity_banner_unit")
    public String unityBanner="Banner_Android";

    @SerializedName("unity_reward_unit")
    public String unityReward="Rewarded_Android";

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

    @SerializedName("max_native_medium")
    public String maxNativeMedium="765834c490a02c47";

    @SerializedName("max_native_small")
    public String maxNativeSmall="eeb4ffb41cdd5a85";

    @SerializedName("max_banner")
    public String maxBanner="9f678a53d0374c8c";

    @SerializedName("max_interstitial")
    public String maxInterstitial="888b6c891bfe2950";

    @SerializedName("max_app_open")
    public String maxAppOpen="5ce64976b0df0768";

    @SerializedName("max_reward")
    public String maxReward="0d89f2e62a289516";

    public AdUnits(){

    }

    public void setUpFbUnits(String fbInterstitial, String fbNative, String fbBanner) {
        this.fbInterstitial = fbInterstitial;
        this.fbNative = fbNative;
        this.fbBanner = fbBanner;
    }

    public void setUpUnity(String unityInterstitial,String unityBanner){
        this.unityInterstitial = unityInterstitial;
        this.unityBanner = unityBanner;
    }

    public void setUpAdmUnits(String admInterstitial, String admNative, String admBanner, String admAppOpen, String admRewarded, String admRewardedInterstitial) {
        this.admInterstitial = admInterstitial;
        this.admNative = admNative;
        this.admBanner = admBanner;
        this.admAppOpen = admAppOpen;
        this.admRewarded = admRewarded;
        this.admRewardedInterstitial = admRewardedInterstitial;
    }

    public void setUpMaxUnits(String maxInterstitial, String maxNativeSmall, String maxNativeMedium, String maxAppOpen, String maxReward, String maxBanner) {
        this.maxInterstitial = admInterstitial;
        this.maxNativeSmall = admNative;
        this.maxNativeMedium = admBanner;
        this.maxAppOpen = admAppOpen;
        this.maxReward = admRewarded;
        this.maxBanner = admRewardedInterstitial;
    }

   public void defaultInit(){
        this.fbInterstitial = "YOUR_PLACEMENT_ID";
        this.fbNative = "YOUR_PLACEMENT_ID";
        this.fbBanner = "YOUR_PLACEMENT_ID";

        this.admInterstitial = "ca-app-pub-3940256099942544/1033173712";
        this.admNative = "ca-app-pub-3940256099942544/2247696110";
        this.admBanner = "ca-app-pub-3940256099942544/6300978111";
        this.admRewarded = "ca-app-pub-3940256099942544/5224354917";
        this.admRewardedInterstitial = "ca-app-pub-3940256099942544/5354046379";
        this.admAppOpen = "ca-app-pub-3940256099942544/3419835294";

        unityInterstitial = "Interstitial_Android";
        unityBanner = "Banner_Android";
    }
}
