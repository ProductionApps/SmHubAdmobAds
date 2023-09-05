package com.smarthub.smhubads.ads.unity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.smarthub.smhubads.BaseApplication;
import com.smarthub.smhubads.R;
import com.smarthub.smhubads.Utils.AppLogger;
import com.smarthub.smhubads.ads.AdsController;
import com.smarthub.smhubads.ads.CustomActivity;
import com.smarthub.smhubads.ads.listeners.InterstitialListener;
import com.smarthub.smhubads.ads.listeners.RewardListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import java.util.ArrayList;

public class UnityMediationController implements IUnityAdsLoadListener,IUnityAdsShowListener {

    String INTERSTITIAL_ID="";
    String REWARD_AD_ID="";
    CustomActivity customActivity;
    RewardListener rewardListener;
    //    boolean isRewardedAdLoading = false;
    public boolean isRewardReady = false;
    public boolean isInterstitialReady = false;
    public static UnityMediationController instance;
    public InterstitialListener interstitialListener;

    public static UnityMediationController getInstance(){
        if (instance==null)
            instance = new UnityMediationController();
        return instance;
    }

    @Override
    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
        if (INTERSTITIAL_ID.equals(placementId)) {
            isInterstitialReady = false;
            if (interstitialListener != null && customActivity != null && customActivity.isVisible)
                interstitialListener.onAdDismissed(false);
        }else if (placementId.equals(REWARD_AD_ID) && rewardListener!=null) {
            rewardListener.onRewardFailed();
//            isRewardedAdLoading = false;
        }
        UnityAds.load(placementId,this);
        AppLogger.d("Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
    }

    @Override
    public void onUnityAdsShowStart(String placementId) {
        AppLogger.d( "onUnityAdsShowStart: " + placementId);
    }

    @Override
    public void onUnityAdsShowClick(String placementId) {
        AppLogger.d("onUnityAdsShowClick: " + placementId);
    }

    @Override
    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
        BaseApplication.getInstance().putAdsEvent("unityAdImpression");
        BaseApplication.getInstance().putAdsEvent("unity"+placementId);
        if (placementId.equals(INTERSTITIAL_ID)) {
            isInterstitialReady = false;
            AdsController.getInstance().updateInterstitialTime();
            AppLogger.d( "onUnityAdsShowComplete: " + placementId);
            if (interstitialListener != null && customActivity != null && customActivity.isVisible)
                interstitialListener.onAdDismissed(true);
        }else if (placementId.equals(REWARD_AD_ID) && rewardListener!=null) {
            isRewardReady = false;
            AppLogger.d("onUnityAdsShowComplete: " + placementId);
            RewardItem rewardItem = new RewardItem() {
                @Override
                public int getAmount() {
                    return 1;
                }

                @NonNull
                @Override
                public String getType() {
                    return "UnityAds";
                }
            };
            rewardListener.onRewardEarned(rewardItem);
        }
        UnityAds.load(placementId,this);
    }

    @Override
    public void onUnityAdsAdLoaded(String placementId) {
        if (INTERSTITIAL_ID.equals(placementId)) {
            isInterstitialReady = true;
            AppLogger.d( "unity interstitial was loaded.");
        }else if (REWARD_AD_ID.equals(placementId)) {
            isRewardReady = true;
            AppLogger.d( "unity Rewarded was loaded.");
        }
    }

    @Override
    public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
        if (INTERSTITIAL_ID.equals(placementId)) {
            isInterstitialReady = false;
        }else if (REWARD_AD_ID.equals(placementId) && rewardListener!=null) {
            isRewardReady = false;
            rewardListener.onRewardLoadFailed();
        }
        AppLogger.d("Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
    }

    public void showInterstitial(String id, CustomActivity customActivity,InterstitialListener interstitialListener){
        INTERSTITIAL_ID = id;
        this.customActivity = customActivity;
        this.interstitialListener = interstitialListener;
        AppLogger.d("showInterstitial id :"+id);
        if (isInterstitialReady){
            UnityAds.show(customActivity,id,this);
        }else{
            loadInterstitial(INTERSTITIAL_ID);
            interstitialListener.onAdDismissed(false);
        }
    }

    public void showRewarded(String id, CustomActivity customActivity, RewardListener l){
        REWARD_AD_ID = id;
        AppLogger.d("unity showRewarded:"+REWARD_AD_ID);
        this.rewardListener = l;
        if (isRewardReady){
            UnityAds.show(customActivity,id,this);
        }else{
            loadRewarded(REWARD_AD_ID);
            l.onRewardFailed();
//            Toast.makeText(customActivity,"reward not available currently",Toast.LENGTH_SHORT).show();
        }
    }

    public void showBannerAd(String id,ViewGroup frameLayout,CustomActivity customActivity){
        if (frameLayout==null)
            return;
        AppLogger.d("showBannerAd");
        BannerView bannerAdView = new BannerView(customActivity,id,
                new UnityBannerSize(320,50));
        populateBanner(bannerAdView,frameLayout,customActivity);
        bannerAdView.load();
    }

    public void clearAds(){
        for (BannerView ad : bannerAd1){
            try {
                ad.destroy();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        bannerAd1.clear();
        AppLogger.d("Admob all ad cache cleared");
    }

    public void showLargeBannerAd(String id,CustomActivity customActivity,ViewGroup viewGroup){
        this.customActivity = customActivity;
        AppLogger.d("showLargeBannerAd");
        BannerView bannerAd = new BannerView(customActivity,id,UnityBannerSize.getDynamicSize(customActivity));
        bannerAd.load();
        populateBanner(bannerAd,viewGroup,customActivity);
    }
    BannerView bannerAd;
    ArrayList<BannerView> bannerAd1 = new ArrayList();
    private void populateBanner(BannerView bannerAdView,ViewGroup viewGroup,CustomActivity customActivity){
        if (bannerAdView==null)
            return;
        bannerAd = bannerAdView;
        bannerAd1.add(bannerAdView);
        if (bannerAd1.size()>2){
            bannerAd1.get(0).destroy();
            bannerAd1.remove(0);
        }
        BaseApplication.getInstance().putAdsEvent("unityBanner");
        // Add the Ad view into the ad container.
        LayoutInflater inflater = LayoutInflater.from(customActivity);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.unity_banner_layout, null, false);
        LinearLayout adContainer = linearLayout.findViewById(R.id.unity_banner);
        adContainer.removeAllViews();
        adContainer.addView(bannerAdView);

        viewGroup.removeAllViews();
        viewGroup.addView(linearLayout);
        viewGroup.invalidate();
        bannerAdView.setListener(new BannerView.Listener() {
            @Override
            public void onBannerLoaded(BannerView bannerAdView) {
                super.onBannerLoaded(bannerAdView);
                if (viewGroup.getVisibility()!=View.VISIBLE)
                    viewGroup.setVisibility(View.VISIBLE);
                AppLogger.d("onBannerLoaded :");
            }

            @Override
            public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
                super.onBannerFailedToLoad(bannerAdView, errorInfo);
                AppLogger.d("onBannerFailedToLoad :"+errorInfo.errorMessage);
            }

            @Override
            public void onBannerClick(BannerView bannerAdView) {
                super.onBannerClick(bannerAdView);
                AppLogger.d("onBannerClick :");
            }

            @Override
            public void onBannerLeftApplication(BannerView bannerAdView) {
                super.onBannerLeftApplication(bannerAdView);
                AppLogger.d("onBannerLeftApplication :");
            }
        });
    }

    public void loadInterstitial(String id){
        if (isInterstitialReady)
            return;
        INTERSTITIAL_ID = id;
        UnityAds.load(id,this);
    }

    public void loadRewarded(String id){
        REWARD_AD_ID = id;
        if (isRewardReady)
            return;
//        isRewardedAdLoading = true;
        UnityAds.load(id,this);
    }

}
