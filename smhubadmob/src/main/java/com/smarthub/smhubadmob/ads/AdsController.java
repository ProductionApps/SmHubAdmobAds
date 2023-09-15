package com.smarthub.smhubadmob.ads;

import android.view.ViewGroup;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.smarthub.smhubadmob.ads.admob.AdmobAdController;
import com.smarthub.smhubadmob.BaseApplication;
import com.smarthub.smhubadmob.Utils.AppLogger;
import com.smarthub.smhubadmob.ads.listeners.FallbackListener;
import com.smarthub.smhubadmob.ads.listeners.InterstitialListener;
import com.smarthub.smhubadmob.ads.listeners.RewardListener;
import com.smarthub.smhubadmob.ads.model.AdUnits;
import com.smarthub.smhubadmob.ads.model.AdsPriority;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdsController {

    private static AdsController instance;
    public AdUnits adUnits = new AdUnits();
    AdsConfig adsConfig;

    public boolean adServing;
    public long interstitialAdLoadTime;
    public int currentInterstitialAdImpressionCount = 0;

    public int getMaxClicked(){
        if (adsConfig!=null)
            return adsConfig.getMaxClick();
        else return 3;
    }

//    public void updateAdsServing(){
//        int dayClickedCount = BaseApplication.getInstance().getPreferenceInt(getDay()) + 1;
//        BaseApplication.getInstance().setPreferenceValue(getDay(),dayClickedCount);
//        if (dayClickedCount>=getMaxClicked()) {
//            BaseApplication.getInstance().putAdsEvent("ad serving limited at:"+dayClickedCount);
//        }
//    }

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

    public void loadLibrary(AdUnits adUnits){
        this.adUnits = adUnits;
        AdmobAdController.getInstance().loadNativeAdCache(BaseApplication.getInstance(),adUnits.admNative);
    }

    public void updateInterstitialTime(){
        interstitialAdLoadTime = System.currentTimeMillis();
        currentInterstitialAdImpressionCount++;
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
        loadInterstitial();
        AdmobAdController.getInstance().loadNativeAdCache(customActivity,adUnits.admNative);
        AdmobAdController.getInstance().loadRewarded(customActivity);

    }

    public void loadInterstitial(){
        AdmobAdController.getInstance().loadInterstitial(adUnits.admInterstitial);
    }

    public void loadRewardAndInterstitial(CustomActivity context){
        AdmobAdController.getInstance().loadRewardAndInterstitial(context);
    }


    public void showRewarded(CustomActivity activity, RewardListener l){
        if (!adServing)
            l.onRewardFailed();
        AdmobAdController.getInstance().showRewarded(activity, new RewardListener() {
            @Override
            public void onRewardEarned(RewardItem rewardItem) {
                l.onRewardEarned(rewardItem);
            }

            @Override
            public void onRewardFailed() {
                l.onRewardFailed();
            }

            @Override
            public void onRewardLoadFailed() {
                l.onRewardLoadFailed();
            }
        });
    }

    public void clearAds(){
        AdmobAdController.getInstance().clearAds();
    }

    public void showInterstitial(CustomActivity customActivity, InterstitialListener adListenerCallback){
        if (!adServing)
            adListenerCallback.onAdDismissed(false);
        AdmobAdController.getInstance().showInterstitial(adListenerCallback, customActivity, adUnits.admInterstitial);
    }

    public void showNativeAd(ViewGroup container,CustomActivity customActivity){
        if (!adServing)
            return;
        AdmobAdController.getInstance().getNativeAd(customActivity, adUnits.admNative);
    }


    public void showNativeBannerAd(ViewGroup container, CustomActivity customActivity){
        if (!adServing)
            return;
        AdmobAdController.getInstance().showNativeAdCurrent(customActivity, adUnits.admNative, container);
    }

    public void showBannerAd(CustomActivity customActivity, ViewGroup container,String name){
        if (!adServing)
            return;
        if (container.getTag()!=null && (container.getTag() instanceof String) && (container.getTag().toString().equals("medium") || container.getTag().toString().equals("large"))) {
            AdmobAdController.getInstance().showBanner(customActivity,container,AdSize.LARGE_BANNER,adUnits.admBanner);
        }
        else {
            AdmobAdController.getInstance().showBanner(customActivity, container, AdSize.BANNER, adUnits.admBanner);
        }
    }

}
