package com.smarthub.smhubadmob.ads.admob;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.smarthub.smhubadmob.BaseApplication;
import com.smarthub.smhubadmob.R;
import com.smarthub.smhubadmob.Utils.AppLogger;
import com.smarthub.smhubadmob.ads.AdsController;
import com.smarthub.smhubadmob.ads.CustomActivity;
import com.smarthub.smhubadmob.ads.listeners.InterstitialListener;
import com.smarthub.smhubadmob.ads.listeners.RewardListener;

import java.util.ArrayList;

public class AdmobAdController{

    AdLoader adLoader;
    NativeAd nativeAd;
    //    RewardListener listener;
    ArrayList<NativeAd> nativeAd1 = new ArrayList();
    AdRequest adRequest;
    CustomActivity customActivity;
    public RewardedAd rewardedAd;
    public boolean rewardedAdLoading = false;
    public RewardedInterstitialAd rewardedInterstitialAd;
    public boolean rewardedInterstitialAdLoading = false;
    public RewardItem earnedReward;
    boolean isLoading = false;
    private InterstitialAd mInterstitialAd;
    private static AdmobAdController instance;

    public static AdmobAdController getInstance(){
        if (instance == null) {
            instance = new AdmobAdController();
        }
        return instance;
    }


    public void setContext(CustomActivity customActivity){
        if (customActivity!=null)
            this.customActivity = customActivity;
    }

    public AdmobAdController() {
        adRequest = new AdRequest.Builder().build();
    }
    AdView adView;
    public void showBanner(CustomActivity context, ViewGroup frameLayout, AdSize adSize,String id){
        loadBanner(context,frameLayout,adSize,id);
    }

    public void loadCacheBanner(CustomActivity context,AdSize adSize,String id){
        if (adRequest==null)
            adRequest = new AdRequest.Builder().build();

        // Add the Ad view into the ad container.
        adView = new AdView(context);
        adView.setAdSize(adSize);
        adView.setAdUnitId(id);
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                AdsController.getInstance().updateBannerNativeAdsServing();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                AdsController.getInstance().updateBannerAdUnit();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
        Log.d("status","banner ad loading...");
    }
//    void loadBanner(CustomActivity context, ViewGroup frameLayout, AdSize adSize,String id,boolean isCurrent){
//        if (adRequest==null)
//            adRequest = new AdRequest.Builder().build();
//
//        // Add the Ad view into the ad container.
//        LayoutInflater inflater = LayoutInflater.from(context);
//        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
//        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.admob_banner_ad, frameLayout, false);
//        AdView adView = new AdView(context);
//        LinearLayout adContainer = linearLayout.findViewById(R.id.ad_container);
//        adContainer.addView(adView);
//        if (frameLayout!=null){
//            frameLayout.removeAllViews();
//            frameLayout.addView(linearLayout);
//            adView.setAdSize(adSize);
//            adView.setAdUnitId(id);
//            adView.loadAd(adRequest);
//            adView.setAdListener(new AdListener() {
//                @Override
//                public void onAdClicked() {
//                    super.onAdClicked();
//                    AdsController.getInstance().updateAdsServing();
//                }
//
//                @Override
//                public void onAdClosed() {
//                    super.onAdClosed();
//                }
//
//                @Override
//                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                    super.onAdFailedToLoad(loadAdError);
//                }
//
//                @Override
//                public void onAdImpression() {
//                    super.onAdImpression();
//                }
//
//                @Override
//                public void onAdLoaded() {
//                    super.onAdLoaded();
//                    if (isCurrent)
//                        AdsController.getInstance().updateBannerTime();
//                    adContainer.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onAdOpened() {
//                    super.onAdOpened();
//                }
//            });
//
//        }
//        Log.d("status","banner ad loading...");
//    }

    void loadBanner(CustomActivity context, ViewGroup frameLayout, AdSize adSize,String id){
        if (adRequest==null)
            adRequest = new AdRequest.Builder().build();
        if (frameLayout!=null){
            AdView adView = new AdView(context);

            try{
                // Add the Ad view into the ad container.
                LayoutInflater inflater = LayoutInflater.from(context);
                // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.admob_banner_ad, frameLayout, false);

                LinearLayout adContainer = linearLayout.findViewById(R.id.admob_banner);
                adContainer.removeAllViews();
                adContainer.addView(adView);

                frameLayout.removeAllViews();
                frameLayout.addView(linearLayout);
                adView.setAdSize(adSize);
                adView.setAdUnitId(id);
                adView.loadAd(adRequest);

                adView.setAdListener(new AdListener() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        AdsController.getInstance().updateBannerNativeAdsServing();
                    }

                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        AdsController.getInstance().updateBannerAdUnit();
                        adContainer.setVisibility(View.VISIBLE);
                        customActivity.bannerView.add(adView);
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                    }
                });
            }catch (Exception e){
                AppLogger.d("admob banner e:"+e.getLocalizedMessage());
                BaseApplication.getInstance().putAdsEvent("adm banner:"+e.getLocalizedMessage());
            }

        }
        Log.d("status","banner ad loading...");
    }

    public void showNativeAd(CustomActivity context,String id){
        if (nativeAd!=null && customActivity!=null && customActivity.mainNativeViewHolder!=null){
            try {
                TemplateView template = customActivity.mainNativeViewHolder.findViewById(R.id.my_template);
                if (template==null)
                    return;
                if (template.getVisibility() != View.VISIBLE)
                    template.setVisibility(View.VISIBLE);
                NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();

                template.setStyles(styles);
                template.setNativeAd(nativeAd);
                nativeAd = null;
                BaseApplication.getInstance().putAdsEvent("admob native ad ");
                loadNativeAdCache(context,id);
            }catch (Exception e){
                AppLogger.d("native ad error:"+e.getLocalizedMessage());
            }

        }
    }

    public void getNativeAd(CustomActivity context,String id){
        this.customActivity = context;
        if (nativeAd!=null && customActivity!=null && customActivity.mainNativeViewHolder!=null){
            try {
                TemplateView template = customActivity.mainNativeViewHolder.findViewById(R.id.my_template);
                if (template==null)
                    return;
                if (template.getVisibility() != View.VISIBLE)
                    template.setVisibility(View.VISIBLE);
                NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();

                template.setStyles(styles);
                template.setNativeAd(nativeAd);
                nativeAd = null;
                BaseApplication.getInstance().putAdsEvent("admob native ad ");
                loadNativeAdCache(context,id);
            }catch (Exception e){
                AppLogger.d("native ad error:"+e.getLocalizedMessage());
            }

        }
        else {
            AppLogger.d("native onFallback");
            if (nativeAd == null)
                loadNativeCache(context,id);
        }
    }

    public void clearAds(){
        for (NativeAd ad : nativeAd1){
            try {
                ad.destroy();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        nativeAd1.clear();
        AppLogger.d("Admob all ad cache cleared");
    }

    public void loadNativeAdCache(Context context,String id)  {
        adLoader = new AdLoader.Builder(context, id).forNativeAd(ad -> {
            nativeAd = ad;
            nativeAd1.add(ad);
            if (nativeAd1.size()>2){
                nativeAd1.get(0).destroy();
                nativeAd1.remove(0);
            }
            AdsController.getInstance().updateNativeAdUnit();
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                // Handle the failure by logging, altering the UI, and so on.
                AppLogger.d("adm native onAdFailedToLoad:"+adError.getMessage()+",id:"+id);
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                AdsController.getInstance().updateBannerNativeAdsServing();
            }
        }).build();
        adLoader.loadAd(adRequest);
    }
    public void loadNativeCache(CustomActivity context,String id)  {
        adLoader = new AdLoader.Builder(context, id).forNativeAd(ad -> {
            nativeAd = ad;
            nativeAd1.add(ad);
            if (nativeAd1.size()>2){
                nativeAd1.get(0).destroy();
                nativeAd1.remove(0);
            }
            AdsController.getInstance().updateNativeAdUnit();
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                // Handle the failure by logging, altering the UI, and so on.
                AppLogger.d("adm native onAdFailedToLoad:"+adError.getMessage()+",id:"+id);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                showNativeAd(context,id);
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                AdsController.getInstance().updateBannerNativeAdsServing();
            }
        }).build();
        adLoader.loadAd(adRequest);
    }
    public void showNativeAdCurrent(Context context, String id, View mainNativeViewHolder)  {
        AdLoader adLoader = new AdLoader.Builder(context, id).forNativeAd(ad -> {
            nativeAd = ad;
            nativeAd1.add(ad);
            if (nativeAd1.size()>2){
                nativeAd1.get(0).destroy();
                nativeAd1.remove(0);
            }
            try {
                if (mainNativeViewHolder==null)
                    return;
                TemplateView template = mainNativeViewHolder.findViewById(R.id.my_template);
                if (template==null)
                    return;
                if (template.getVisibility() != View.VISIBLE)
                    template.setVisibility(View.VISIBLE);
                NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();

                template.setStyles(styles);
                template.setNativeAd(nativeAd);
                nativeAd = null;
                BaseApplication.getInstance().putAdsEvent("admob native ad ");
                AdsController.getInstance().updateNativeAdUnit();
            }catch (Exception e){
                AppLogger.d("native ad error:"+e.getLocalizedMessage());
            }
            AdsController.getInstance().updateNativeAdUnit();
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                // Handle the failure by logging, altering the UI, and so on.
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                BaseApplication.getInstance().putAdsEvent("admob native clicked ");
                AdsController.getInstance().updateBannerNativeAdsServing();
            }
        }).build();
        adLoader.loadAd(adRequest);
    }

    /**
     * Populates a {@link CustomActivity} object with data from a given
     * {@link InterstitialListener}.
     */
    public void showInterstitial (InterstitialListener interstitialListener, CustomActivity customActivity,String id){
        Log.d("status","admob request Interstitial");
        if (mInterstitialAd!=null){
            mInterstitialAd.show(customActivity);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    interstitialListener.onAdDismissed(false);
                    mInterstitialAd=null;
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                    BaseApplication.getInstance().isAppOpen = false;
                    BaseApplication.getInstance().interstitialListener = interstitialListener;
                    BaseApplication.getInstance().putAdsEvent("admob interstitial ad ");
                    AdsController.getInstance().updateInterstitialTime();
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    BaseApplication.getInstance().isAppOpen = true;
                    if (BaseApplication.getInstance().interstitialListener!=null) {
                        interstitialListener.onAdDismissed(true);
                        BaseApplication.getInstance().interstitialListener = null;
                    }
                    mInterstitialAd=null;
                    loadInterstitial(id);
                }

                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                    BaseApplication.getInstance().putAdsEvent("admob interstitial clicked ");

                }
            });
            return;
        }
        interstitialListener.onAdDismissed(false);
        if (!isLoading){
            loadInterstitial(id);
        }
    }

    /**
     * Populates a {@link ViewGroup} object with data from a given
     * {@link NativeAd}.
     */
    public void loadInterstitial(String id) {
        if (isLoading || mInterstitialAd!=null || customActivity==null){
            return;
        }
        if (adRequest==null)
            adRequest = new AdRequest.Builder().build();
        // Set the media view.

        isLoading = true;
        Log.d("status","loadInterstitial");
        InterstitialAd.load(customActivity,id,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        isLoading = false;
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                        isLoading = false;
                    }
                });
    }

    public void showRewardedInterstitial(Activity activity, RewardListener l){
        if (rewardedInterstitialAd!=null){
            setFullScreenCallback(activity,l);
            rewardedInterstitialAd.show(activity, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    earnedReward = rewardItem;
                }
            });
        }else {
            l.onRewardFailed();
            loadRewardedInterstitialAd(activity);
//            Toast.makeText(activity,"reward not available currently",Toast.LENGTH_SHORT).show();
        }
    }

    public void showRewarded(Activity activity,RewardListener l){

        if (rewardedAd!=null){
            AppLogger.d("rewardedAd not null");
            setRewardedFullScreenCallback(activity,l);
            rewardedAd.show(activity, rewardItem -> {
                earnedReward = rewardItem;
                AppLogger.d("rewardedAd granted");
            });
        }else {
            loadRewarded(activity);
            AppLogger.d("rewardedAd null");
            showRewardedInterstitial(activity,l);
        }
    }

    public void loadRewardAndInterstitial(Context context){
        if (rewardedAd == null)
            loadRewarded(context);
        if (rewardedInterstitialAd == null)
            loadRewardedInterstitialAd(context);
    }

    public void loadRewardedInterstitialAd(Context context) {
        // Use the test ad unit ID to load an ad.
        if (rewardedInterstitialAdLoading || rewardedInterstitialAd!=null)
            return;
        rewardedInterstitialAdLoading = true;
        RewardedInterstitialAd.load(context, AdsController.getInstance().adUnits.admRewardedInterstitial,
                new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        rewardedInterstitialAd = null;
                        rewardedInterstitialAdLoading = false;
                        AppLogger.d( "admob RewardedInterstitial loadAdError:"+loadAdError.getMessage());
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedInterstitialAd ad) {
                        super.onAdLoaded(ad);
                        AppLogger.d( "admob RewardedInterstitial was loaded.");
                        rewardedInterstitialAd = ad;
                        rewardedInterstitialAdLoading = false;
                    }
                });
    }

    public void setFullScreenCallback(Context context,RewardListener l){
        rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
                if (earnedReward != null)
                    l.onRewardEarned(earnedReward);
                earnedReward = null;
                rewardedInterstitialAd = null;
                loadRewardedInterstitialAd(context);
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
            }
        });
    }

    public void loadRewarded(Context context) {
        if (rewardedAdLoading || rewardedAd!=null)
            return;
        rewardedAdLoading = true;
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, AdsController.getInstance().adUnits.admRewarded,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        AppLogger.d( "admob Rewarded loadAdError:"+loadAdError.getMessage());
                        rewardedAd = null;
                        rewardedAdLoading = false;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        rewardedAdLoading = false;
                        AppLogger.d( "admob reward was loaded.");
                    }
                });
    }

    public void setRewardedFullScreenCallback(Context context,RewardListener l){
        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
                if (earnedReward != null)
                    l.onRewardEarned(earnedReward);
                earnedReward = null;
                rewardedAd = null;
                loadRewarded(context);
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
            }
        });
    }


}
