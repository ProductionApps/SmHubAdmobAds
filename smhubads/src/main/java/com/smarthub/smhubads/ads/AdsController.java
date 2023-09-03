package com.smarthub.smhubads.ads;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.gson.Gson;
import com.smarthub.smhubads.BaseApplication;
import com.smarthub.smhubads.R;
import com.smarthub.smhubads.Utils.AppLogger;
import com.smarthub.smhubads.ads.admob.AdmobAdController;
import com.smarthub.smhubads.ads.applovin.AppLovinMaxAdController;
//import com.smarthub.smhubads.ads.facebook.FbAdController;
import com.smarthub.smhubads.ads.listeners.FallbackListener;
import com.smarthub.smhubads.ads.listeners.InterstitialListener;
import com.smarthub.smhubads.ads.listeners.RewardListener;
import com.smarthub.smhubads.ads.model.AdUnits;
import com.smarthub.smhubads.ads.model.AdsPriority;
import com.smarthub.smhubads.ads.unity.UnityMediationController;
//import com.smarthub.smhubads.ads.unity.UnityMediationController;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdsController {

    private static AdsController instance;
    public AdUnits adUnits = new AdUnits();
    public AdsPriority adPrior = new AdsPriority();
    AdsConfig adsConfig;

    public boolean adServing;
    public long interstitialAdLoadTime;
    public int currentInterstitialAdImpressionCount = 0;

    public int getMaxClicked(){
        if (adsConfig!=null)
            return adsConfig.getMaxClick();
        else return 3;
    }

    public void updateAdsServing(){
        int dayClickedCount = BaseApplication.getInstance().getPreferenceInt(getDay()) + 1;
        BaseApplication.getInstance().setPreferenceValue(getDay(),dayClickedCount);
        if (dayClickedCount>=getMaxClicked()) {
            adServing = false;
            BaseApplication.getInstance().putAdsEvent("ad serving limited at:"+dayClickedCount);
        }
    }

    public void updateBannerNativeAdsServing(){
//        int dayNativeBannerClickedCount = BaseApplication.getInstance().getPreferenceInt(getDay()+"_nativeBanner") + 1;
//        BaseApplication.getInstance().setPreferenceValue(getDay()+"_nativeBanner",dayNativeBannerClickedCount);
//        if (dayNativeBannerClickedCount>=getMaxClicked()) {
//            BaseApplication.getInstance().putAdsEvent("banner native serving limited at:"+dayNativeBannerClickedCount);
//        }
    }

    public AdsController() {
        adsConfig = new AdsConfig();
        adServing = true;
        adUnits.defaultInit();
        interstitialAdLoadTime = 0;
    }

    public AdsConfig getAdsConfig() {
        return adsConfig;
    }

    public String getDay(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate= formatter.format(new Date());
        return strDate;
    }

    public void setAdsConfig(AdsConfig adsConfig) {
        this.adsConfig = adsConfig;
    }

    public static AdsController getInstance(){
        if (instance==null) {
            instance = new AdsController();
        }
        return instance;
    }

    public void loadLibrary(AdsPriority adPrior, AdUnits adUnits){
        this.adPrior = adPrior;
        this.adUnits = adUnits;

        if (adPrior.getAdmobNativeAds()){
            AdmobAdController.getInstance().loadNativeAdCache(BaseApplication.getInstance(),adUnits.admNative);
        }
    }

    public void updateInterstitialTime(){
        interstitialAdLoadTime = System.currentTimeMillis();
        currentInterstitialAdImpressionCount++;
    }

    public void updateInterstitialAdUnit(){
//        try{
//            if (adUnits.admInterstitialList==null || adUnits.admInterstitialList.isEmpty())
//                return;
//            currentInterstitialCount++;
//            if (currentInterstitialCount>adUnits.admInterstitialList.size()-1)
//                currentInterstitialCount = 0;
//            if (adUnits.admInterstitialList.size()>=2){
//                adUnits.admInterstitial = adUnits.admInterstitialList.get(currentInterstitialCount);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public void updateBannerAdUnit(){
//        try{
//            if (adUnits.admBannerList==null || adUnits.admBannerList.isEmpty())
//                return;
//            currentBannerCount++;
//            if (currentBannerCount>adUnits.admBannerList.size()-1)
//                currentBannerCount = 0;
//            if (adUnits.admBannerList.size()>=2){
//                adUnits.admBanner = adUnits.admBannerList.get(currentBannerCount);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public void updateNativeAdUnit(){
//        try{
//            if (adUnits.admNativeList==null || adUnits.admNativeList.isEmpty())
//                return;
//            currentNativeCount++;
//            if (currentNativeCount>adUnits.admNativeList.size()-1)
//                currentNativeCount = 0;
//            if (adUnits.admNativeList.size()>=2){
//                adUnits.admNative = adUnits.admNativeList.get(currentNativeCount);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public void loadAds(CustomActivity customActivity){
        loadInterstitial(customActivity);
        if (adPrior.getAdmobNativeAds()){
            AdmobAdController.getInstance().loadNativeAdCache(customActivity,adUnits.admNative);
        }

        if (adPrior.getAdmobRewardAds()){
            AdmobAdController.getInstance().loadRewarded(customActivity);
        }

        if (adPrior.getMaxRewardAds()){
            AppLovinMaxAdController.getInstance().loadRewardedAd(customActivity,adUnits.maxReward);
        }

    }

    public void loadInterstitial(CustomActivity customActivity){
        AppLogger.d("loadInterstitial:"+adPrior.getUnityAds());
        if (adPrior==null)
            adPrior = new AdsPriority();
        if (adPrior.getAdmobInterstitialAds()){
            AdmobAdController.getInstance().loadInterstitial(adUnits.admInterstitial);
        }
        if (adPrior.getMaxInterstitialAds() && BaseApplication.getInstance().appLovingInit){
            AppLovinMaxAdController.getInstance().loadInterstitialAd(customActivity, adUnits.maxInterstitial);
        }
        if (adPrior.getUnityAds() && BaseApplication.getInstance().isUnityInitialized){
            UnityMediationController.getInstance().loadInterstitial(adUnits.unityInterstitial);
        }
    }

    public void loadRewardAndInterstitial(CustomActivity context){
        AppLogger.d("loadRewardAndInterstitial:"+adPrior.getAdmobRewardAds()+"getUnityReward:"+adPrior.getUnityReward());
        if (adPrior.getAdmobRewardAds()){
            AdmobAdController.getInstance().loadRewardAndInterstitial(context);
        }
        if (adPrior.getUnityReward()){
            UnityMediationController.getInstance().loadRewarded(adUnits.unityReward);
        }
        if (adPrior.getMaxRewardAds()){
            AppLovinMaxAdController.getInstance().loadRewardedAd(context,adUnits.maxReward);
        }
    }

    public void handleAdmobReward(CustomActivity customActivity,RewardListener l){
        AppLogger.d("showRewarded:"+adUnits.maxReward);
        if (adPrior.getMaxRewardAds()){
            AppLovinMaxAdController.getInstance().showRewardAd(customActivity,adUnits.maxReward,l);
        }else if (adPrior.getUnityReward()){
            UnityMediationController.getInstance().showRewarded(adUnits.unityReward,customActivity,l);
        }else
            l.onRewardFailed();
    }

    public void showRewarded(CustomActivity activity, RewardListener l){
        if (adPrior.getAdmobRewardAds()){
            AppLogger.d("showRewarded:"+adPrior.getAdmobRewardAds());
            AdmobAdController.getInstance().showRewarded(activity, new RewardListener() {
                @Override
                public void onRewardEarned(RewardItem rewardItem) {
                    l.onRewardEarned(rewardItem);
                }

                @Override
                public void onRewardFailed() {
                    handleAdmobReward(activity,l);
                }

                @Override
                public void onRewardLoadFailed() {
                    l.onRewardLoadFailed();
                }
            });
        }
        else handleAdmobReward(activity,l);
    }

    public void clearAds(){
        AdmobAdController.getInstance().clearAds();
//        UnityMediationController.getInstance().clearAds();
    }

    public void showInterstitial(CustomActivity customActivity, InterstitialListener adListenerCallback){

//        AppLovinMaxAdController.getInstance().showInterstitialAd(customActivity,adListenerCallback, adUnits.maxInterstitial);

        boolean isEnable = BaseApplication.getInstance().getInterstitialAdEnability(BaseApplication.getInstance().currentFragmentName) &&
                adServing;
        if (isEnable) {
            if (adPrior.getAdmobInterstitialAds()){
                AdmobAdController.getInstance().showInterstitial(adListenerCallback, customActivity, adUnits.admInterstitial, () -> {
                    handleAdmobInterstitalFallback(customActivity,adListenerCallback);
                });
            }else handleAdmobInterstitalFallback(customActivity,adListenerCallback);
        }else handleAdmobInterstitalFallback(customActivity,adListenerCallback);
    }

    void handleAdmobInterstitalFallback(CustomActivity customActivity, InterstitialListener adListenerCallback){
        int time = (int) ((System.currentTimeMillis() - interstitialAdLoadTime) / 1000);
        boolean isEnable = time >= adsConfig.interstitialMinTime;
        AppLogger.d("handleAdmobInterstitalFallback:"+adsConfig.interstitialMinTime+",time:"+time);

        if (isEnable){
            if(adPrior.getUnityInterstitial() && BaseApplication.getInstance().isUnityInitialized){
                UnityMediationController.getInstance().showInterstitial(adUnits.unityInterstitial,customActivity,adListenerCallback);
            }else if(adPrior.getMaxInterstitialAds() && BaseApplication.getInstance().appLovingInit){
                AppLovinMaxAdController.getInstance().showInterstitialAd(customActivity,adListenerCallback, adUnits.maxInterstitial);
            }else adListenerCallback.onAdDismissed();
        }else adListenerCallback.onAdDismissed();

    }

    public void showNativeAd(ViewGroup container,CustomActivity customActivity){

        boolean isEnable = BaseApplication.getInstance().getNativeAdEnability(BaseApplication.getInstance().currentFragmentName);
        AppLogger.d("getAdmobNativeAds:"+adPrior.getAdmobNativeAds()+",isEnable:"+isEnable);

        if (isEnable && adPrior.getAdmobNativeAds()){
            AdmobAdController.getInstance().getNativeAd(customActivity, adUnits.admNative, () ->
                    handleAdmobNativeAd(container,customActivity)
            );
        }else handleAdmobNativeAd(container,customActivity);
    }

    void handleAdmobNativeAd(ViewGroup container,CustomActivity customActivity){
        AppLogger.d("handleAdmobNativeAd MaxNativeAds:"+adPrior.getMaxNativeAds()+",appLovingInit:"+
                BaseApplication.getInstance().appLovingInit);
        if (adPrior.getMaxNativeAds() && BaseApplication.getInstance().appLovingInit){
            AppLovinMaxAdController.getInstance().createNativeAd(container, adUnits.maxNativeMedium,customActivity);
        }
    }


    public void showNativeBannerAd(ViewGroup container, CustomActivity customActivity){
//        AppLovinMaxAdController.getInstance().createNativeBannerAd(container, adUnits.maxNativeSmall,customActivity);

        boolean isEnable = BaseApplication.getInstance().getBannerAdEnability(BaseApplication.getInstance().currentFragmentName);
        if (isEnable && adPrior.getAdmobBannerAds()){
            AdmobAdController.getInstance().showNativeAdCurrent(customActivity, adUnits.admNative, container, new FallbackListener() {
                @Override
                public void onFallback() {
                    if (customActivity.isVisible){
                        handleAdmobNativeBannerAd(container,customActivity);
                    }
                }
            });
        }else handleAdmobNativeBannerAd(container,customActivity);
    }

    void handleAdmobNativeBannerAd(ViewGroup container,CustomActivity customActivity){
       if (adPrior.getMaxNativeAds() && BaseApplication.getInstance().appLovingInit){
            AppLovinMaxAdController.getInstance().createNativeBannerAd(container, adUnits.maxNativeSmall,customActivity);
        }else if (adPrior.getUnityBanner() && BaseApplication.getInstance().isUnityInitialized){
           UnityMediationController.getInstance().showLargeBannerAd(adUnits.unityBanner, customActivity,container);
       }
    }

    public void showBannerAd(CustomActivity customActivity, ViewGroup container,String name){
        boolean isEnable = BaseApplication.getInstance().getBannerAdEnability(BaseApplication.getInstance().currentFragmentName);
        if (container.getTag()!=null && (container.getTag() instanceof String) && (container.getTag().toString().equals("medium") || container.getTag().toString().equals("large"))) {
//            AppLovinMaxAdController.getInstance().createNativeBannerAd(container, adUnits.maxNativeSmall,customActivity);

            if (isEnable && adPrior.getAdmobBannerAds()){
                AdmobAdController.getInstance().showBanner(customActivity,container,AdSize.LARGE_BANNER,adUnits.admBanner,()->{
                    if (customActivity.isVisible){
                        handleAdmobLargeBanner(customActivity,container);
                    }
                });
            }else handleAdmobLargeBanner(customActivity,container);
        }
        else {
//            AppLovinMaxAdController.getInstance().createBannerAd(container,customActivity, adUnits.maxBanner);

            if (isEnable && adPrior.getAdmobBannerAds()) {
                AdmobAdController.getInstance().showBanner(customActivity, container, AdSize.BANNER, adUnits.admBanner,()->{
                    if (customActivity.isVisible){
                        handleAdmobBanner(customActivity,container);
                    }
                });
            }
            else handleAdmobBanner(customActivity,container);
        }
    }

    void handleAdmobBanner(CustomActivity customActivity, ViewGroup container){
        if (adPrior.getMaxBannerAds() && BaseApplication.getInstance().appLovingInit){
            AppLovinMaxAdController.getInstance().createBannerAd(container,customActivity, adUnits.maxBanner);
        }else if (adPrior.getUnityBanner() && BaseApplication.getInstance().isUnityInitialized){
            UnityMediationController.getInstance().showBannerAd(adUnits.unityBanner,container,customActivity);
        }
    }

    void handleAdmobLargeBanner(CustomActivity customActivity, ViewGroup container){
        if (adPrior.getMaxBannerAds() && BaseApplication.getInstance().appLovingInit){
            AppLovinMaxAdController.getInstance().createNativeBannerAd(container, adUnits.maxNativeSmall,customActivity);
        }else if (adPrior.getUnityBanner() && BaseApplication.getInstance().isUnityInitialized){
            UnityMediationController.getInstance().showLargeBannerAd(adUnits.unityBanner, customActivity,container);
        }
    }

    public interface NativeAdLoaded{
        void nativeAdLoaded();
    }

}
