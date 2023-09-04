package com.smarthub.smhubads.ads.applovin;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.smarthub.smhubads.BaseApplication;
import com.smarthub.smhubads.R;
import com.smarthub.smhubads.Utils.AppLogger;
import com.smarthub.smhubads.ads.AdsController;
import com.smarthub.smhubads.ads.CustomActivity;
import com.smarthub.smhubads.ads.listeners.InterstitialListener;
import com.smarthub.smhubads.ads.listeners.RewardListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AppLovinMaxAdController {

    private MaxRewardedAd rewardedAd;
    RewardListener rewardListener;
    private int retryAttempt;
    private MaxAdView bannerAdView;
    ArrayList<MaxAdView> bannerAdView1 = new ArrayList();
    private MaxNativeAdLoader nativeAdLoader;
    ArrayList<MaxNativeAdLoader> nativeAdLoader1 = new ArrayList();

    public static AppLovinMaxAdController instance;
    private MaxInterstitialAd interstitialAd;
    InterstitialListener interstitialCallback;

    public static AppLovinMaxAdController getInstance(){
        if (instance==null)
            instance = new AppLovinMaxAdController();

        return instance;
    }

    public void createBannerAd(ViewGroup rootView,CustomActivity customActivity,String id){
        if (id==null || id.isEmpty())
            return;

        AppLogger.d("max banner loading --:" + id);

        // Add the Ad view into the ad container.
        bannerAdView = new MaxAdView(id,customActivity);

        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        // Banner height on phones and tablets is 50 and 90, respectively
        int heightPx = customActivity.getResources().getDimensionPixelSize( R.dimen.banner_height );
        bannerAdView.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx ) );

        LayoutInflater inflater = LayoutInflater.from(customActivity);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.common_banner_layout, rootView, false);

        FrameLayout adContainer = root.findViewById(R.id.ad_container);
        adContainer.removeAllViews();
        adContainer.addView(bannerAdView);

        rootView.removeAllViews();
        rootView.addView(root);
        bannerAdView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {

                AppLogger.d("Maxon AdLoaded banner:");
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
//                if (bannerAdView.getVisibility()==View.VISIBLE)
//                    bannerAdView.setVisibility(View.GONE);
                AppLogger.d("MaxError banner:"+error.getMessage());
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
//                if (bannerAdView.getVisibility()==View.VISIBLE)
//                    bannerAdView.setVisibility(View.GONE);
            }
        });

        // Load the ad
        bannerAdView.loadAd();
        bannerAdView.startAutoRefresh();

        BaseApplication.getInstance().putAdsEvent("max banner");
        bannerAdView1.add(bannerAdView);
        if (bannerAdView1.size()>2){
            bannerAdView1.get(0).destroy();
            bannerAdView1.remove(0);
        }
    }


    public void loadInterstitialAd(Activity activity,String id) {
        if (id==null || id.isEmpty())
            return;
        AppLogger.d("loadInterstitialAd :" + id);
        if (interstitialAd!=null && interstitialAd.isReady())
            return;
        if (interstitialAd==null){
            interstitialAd = new MaxInterstitialAd(id,activity);
            interstitialAd.setListener(new MaxAdListener() {
                @Override
                public void onAdLoaded(MaxAd ad) {
                    AppLogger.d("max loadInterstitialAd loaded"+ad.getAdUnitId());
                    // Interstitial ad is ready to be shown. interstitialAd.isReady() will now return 'true'

                    // Reset retry attempt
                    retryAttempt = 0;
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {
                    BaseApplication.getInstance().isAppOpen = false;
                    BaseApplication.getInstance().putAdsEvent("max interstitial");
                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    AppLogger.d("interstitial dismissed");
                    BaseApplication.getInstance().isAppOpen = true;
                    if (interstitialCallback!=null)
                        interstitialCallback.onAdDismissed();
                    interstitialAd.loadAd();
                }

                @Override
                public void onAdClicked(MaxAd ad) {
                    BaseApplication.getInstance().putAdsEvent("max interstitial clicked");
                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    AppLogger.d("max onAdLoadFailed:"+error.getMessage());
                    // Interstitial ad failed to load
                    // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)

                    retryAttempt++;
                    long delayMillis = TimeUnit.SECONDS.toMillis( (long) Math.pow( 2, Math.min( 6, retryAttempt ) ) );

                    new Handler().postDelayed(() -> {
                                if (interstitialAd==null || !interstitialAd.isReady())
                                    interstitialAd.loadAd();
                            }
                            , delayMillis );
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    if (interstitialCallback!=null)
                        interstitialCallback.onAdDismissed();
                    interstitialAd.loadAd();
                    AppLogger.d("max onAdDisplayFailed:"+error.getMessage());
                }
            });
        }
        // Load the first ad
        interstitialAd.loadAd();
    }

    public void showInterstitialAd(CustomActivity customActivity, InterstitialListener interstitialCallback,String id){
        this.interstitialCallback = interstitialCallback;
        if (interstitialAd!=null && interstitialAd.isReady()){
            interstitialAd.showAd();
            AppLogger.d("max showInterstitial available");
        }else{
            AppLogger.d("max showInterstitial not available");
            loadInterstitialAd(customActivity,id);
            interstitialCallback.onAdDismissed();
        }
    }

    public void createNativeAd(ViewGroup nativeAdContainer, String id, Context customActivity) {
        if (id==null || id.isEmpty())
            id = AdsController.getInstance().adUnits.maxNativeMedium;
        AppLogger.d("createNativeAd :" + id);
        if (nativeAdContainer==null)
            return;

        nativeAdLoader = new MaxNativeAdLoader(id, customActivity);
        nativeAdLoader.setNativeAdListener( new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                AppLogger.d("MaxError native ad loaded ");
                try {
//                    nativeAd = ad;
                    nativeAdContainer.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
                    nativeAdContainer.getLayoutParams().height = BaseApplication.getInstance().dpToPx(280);
                    nativeAdContainer.removeAllViews();
                    nativeAdContainer.addView(nativeAdView);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                AppLogger.d("MaxError native error : " + error.getMessage());
                // We recommend retrying with exponentially higher delays up to a maximum delay
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad) {
                // Optional click callback
            }
        } );

        nativeAdLoader.loadAd();

        nativeAdLoader1.add(nativeAdLoader);
        if (nativeAdLoader1.size()>2){
            nativeAdLoader1.get(0).destroy();
            nativeAdLoader1.remove(0);
        }
    }
    public void createNativeBannerAd(ViewGroup nativeAdContainer,String id,CustomActivity customActivity) {
        if (id==null || id.isEmpty())
            id = AdsController.getInstance().adUnits.maxNativeSmall;
        AppLogger.d("createNativeAd :" + id);
        if (nativeAdContainer==null)
            return;
        nativeAdLoader = new MaxNativeAdLoader(id, customActivity);
        nativeAdLoader.setNativeAdListener( new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                AppLogger.d("MaxError native ad loaded ");
//                nativeAd = ad;
                nativeAdContainer.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
                nativeAdContainer.getLayoutParams().height = BaseApplication.getInstance().dpToPx(130);
                if (customActivity.isVisible) {
//                    nativeAdView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    // Add ad view to view.
                    nativeAdContainer.removeAllViews();
                    nativeAdContainer.addView(nativeAdView);
                }
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                AppLogger.d("MaxError native error : " + error.getMessage());
                // We recommend retrying with exponentially higher delays up to a maximum delay
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad) {
                // Optional click callback
            }
        } );

        nativeAdLoader.loadAd();
        nativeAdLoader1.add(nativeAdLoader);
        if (nativeAdLoader1.size()>2){
            nativeAdLoader1.get(0).destroy();
            nativeAdLoader1.remove(0);
        }
    }

    public void showRewardAd(CustomActivity customActivity, String id, RewardListener listener){
        this.rewardListener = listener;
        if (rewardedAd!=null && rewardedAd.isReady() ) {
            rewardedAd.showAd();
        }else {
            rewardListener.onRewardFailed();
            loadRewardedAd(customActivity,id);
        }
    }

    public void loadRewardedAd(CustomActivity customActivity,String id) {
        if (rewardedAd!=null && rewardedAd.isReady() ) {
            return;
        }
        AppLogger.d("max loadRewardedAd:"+id);
        this.rewardListener = null;
        if (rewardedAd == null){
            rewardedAd = MaxRewardedAd.getInstance( id, customActivity );
            rewardedAd.setListener(new MaxRewardedAdListener() {

                // MAX Ad Listener
                @Override
                public void onAdLoaded(final MaxAd maxAd) {
                    AppLogger.d("loadRewardedAd onAdLoaded");
                    retryAttempt = 0;
                }

                @Override
                public void onAdLoadFailed(final String adUnitId, final MaxError error) {
                    AppLogger.d("max onAdLoadFailed:"+error.getMessage());
                    retryAttempt++;
                    rewardListener = null;
                    long delayMillis = TimeUnit.SECONDS.toMillis( (long) Math.pow( 2, Math.min( 6, retryAttempt ) ) );

                    new Handler().postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            if (rewardedAd==null || !rewardedAd.isReady())
                                rewardedAd.loadAd();
                        }
                    }, delayMillis );
                }

                @Override
                public void onAdDisplayFailed(final MaxAd maxAd, final MaxError error) {
                    AppLogger.d("max onAdLoadFailed:"+error.getMessage());
                    // Rewarded ad failed to display. We recommend loading the next ad
                    rewardedAd.loadAd();
                    if (rewardListener !=null)
                        rewardListener.onRewardFailed();
                }

                @Override
                public void onAdDisplayed(final MaxAd maxAd) {
                    BaseApplication.getInstance().putAdsEvent("max rewarded");
                }

                @Override
                public void onAdClicked(final MaxAd maxAd) {
                    BaseApplication.getInstance().putAdsEvent("max rewarded clicked");
                }

                @Override
                public void onAdHidden(final MaxAd maxAd) {
                    // rewarded ad is hidden. Pre-load the next ad
                    rewardedAd.loadAd();
                }

                @Override
                public void onRewardedVideoStarted(final MaxAd maxAd) {} // deprecated

                @Override
                public void onRewardedVideoCompleted(final MaxAd maxAd) {} // deprecated

                @Override
                public void onUserRewarded(final MaxAd maxAd, final MaxReward maxReward) {
                    RewardItem rewardItem = new RewardItem() {
                        @Override
                        public int getAmount() {
                            return maxReward.getAmount();
                        }

                        @NonNull
                        @Override
                        public String getType() {
                            return maxReward.getLabel();
                        }
                    };
                    if (rewardListener !=null)
                        rewardListener.onRewardEarned(rewardItem);
                    // Rewarded ad was displayed and user should receive the reward
                }
            });
        }
        rewardedAd.loadAd();
    }

}
