package com.smarthub.smhubadmob;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDexApplication;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.smarthub.smhubadmob.ads.AdsController;
import com.smarthub.smhubadmob.ads.AdsType;
import com.smarthub.smhubadmob.ads.listeners.InterstitialListener;

import java.util.Date;


public  class BaseApplication extends MultiDexApplication {
    public boolean appLovingInit = false;
    public boolean isAppOpen = true;
    public boolean isLimitedFeature = false;
    private static BaseApplication instance = null;
    public InterstitialListener interstitialListener;
    public SharedPreferences mPrefs;
    public String currentFragmentName = "fragment";

    public Boolean isUnityInitialized = false;
    public static BaseApplication getInstance(){
        if (instance==null)
            instance = new BaseApplication();
        return instance;
    }

    public interface EventPostListener{
        void onEventPost(String name);
    }
    public EventPostListener eventPostListener;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mPrefs = getSharedPreferences("MyPreference", Context.MODE_PRIVATE);

    }

    public void putAdsEvent(String name){
        if (eventPostListener!=null){
            eventPostListener.onEventPost(name);
        }
    }

    public void setPreferenceValue(String key,String value) {
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putString(key, value);
        edit.commit();
    }
    public void setPreferenceValue(String key,Boolean value) {
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public void setVideoLiked(String id,boolean isLiked){
        String v = "isVideoLiked_"+id;
        setPreferenceValue(v,isLiked);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void setPreferenceValue(String key, int value) {
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public void setPreferenceValue(String key, long value) {
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public boolean getNativeAdEnability(String key) {
        return mPrefs.getBoolean(AdsType.NATIVE.name()+"_"+key,true);
    }

    public boolean getBannerAdEnability(String key) {
        return mPrefs.getBoolean(AdsType.BANNER.name()+"_"+key,true);
    }

    public boolean getInterstitialAdEnability(String key) {
        return mPrefs.getBoolean(AdsType.INTERSTITIAL.name()+"_"+key,true);
    }

    public boolean isVideoLiked(String id) {
        String v = "isVideoLiked_"+id;
        return mPrefs.getBoolean(v,false);
    }

    public boolean getRewardedAdEnability(String key) {
        return getPreferenceBoolean(AdsType.REWARDED.name()+"_"+key);
    }

    public String getPreferenceString(String key) {
        return mPrefs.getString(key,"");
    }
    public boolean getPreferenceBoolean(String key) {
        try{
            return mPrefs.getBoolean(key,false);
        }catch (Exception e){

        }
        return false;
    }

    public boolean getPreferenceBoolean(String key,boolean isEnable) {
        try{
            return mPrefs.getBoolean(key,isEnable);
        }catch (Exception e){

        }
        return isEnable;
    }

    public int getPreferenceInt(String key) {
        return mPrefs.getInt(key,0);
    }

    public long getPreferenceLong(String key) {
        return mPrefs.getLong(key,0);
    }

    public boolean foregrounded() {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);
        return (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE);
    }

    /**
     * Interface definition for a callback to be invoked when an app open ad is complete
     * (i.e. dismissed or fails to show).
     */
    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }

    /** Inner class that loads and shows app open ads. */
    public class AppOpenAdManager {

        private static final String LOG_TAG = "AppOpenAdManager";

        private AppOpenAd appOpenAd = null;
        private boolean isLoadingAd = false;

        /** Keep track of the time an app open ad is loaded to ensure you don't show an expired ad. */
        private long loadTime = 0;

        /** Constructor. */
        public AppOpenAdManager() {}

        /**
         * Load an ad.
         *
         * @param context the context of the activity that loads the ad
         */
        public void loadAd(Context context) {
            // Do not load ad if there is an unused ad or one is already loading.
            if (isLoadingAd || isAdAvailable()) {
                return;
            }

            isLoadingAd = true;
            AdRequest request = new AdRequest.Builder().build();
            AppOpenAd.load(
                    context,
                    AdsController.getInstance().adUnits.admAppOpen,
                    request,
                    new AppOpenAd.AppOpenAdLoadCallback() {
                        /**
                         * Called when an app open ad has loaded.
                         *
                         * @param ad the loaded app open ad.
                         */
                        @Override
                        public void onAdLoaded(AppOpenAd ad) {
                            appOpenAd = ad;
                            isLoadingAd = false;
                            loadTime = (new Date()).getTime();

                            Log.d(LOG_TAG, "onAdLoaded.");
//                            Toast.makeText(context, "onAdLoaded", Toast.LENGTH_SHORT).show();
                        }

                        /**
                         * Called when an app open ad has failed to load.
                         *
                         * @param loadAdError the error.
                         */
                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            isLoadingAd = false;
                            Log.d(LOG_TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
//                            Toast.makeText(context, "onAdFailedToLoad", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        /** Check if ad was loaded more than n hours ago. */
        private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
            long dateDifference = (new Date()).getTime() - loadTime;
            long numMilliSecondsPerHour = 3600000;
            return (dateDifference < (numMilliSecondsPerHour * numHours));
        }

        /** Check if ad exists and can be shown. */
        private boolean isAdAvailable() {
            // Ad references in the app open beta will time out after four hours, but this time limit
            // may change in future beta versions. For details, see:
            // https://support.google.com/admob/answer/9341964?hl=en
            return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
        }

        /**
         * Show the ad if one isn't already showing.
         *
         * @param activity the activity that shows the app open ad
         */
        public void showAdIfAvailable(@NonNull final Activity activity) {
            showAdIfAvailable(
                    activity,
                    () -> {
                        // Empty because the user will go back to the activity that shows the ad.
                    });
        }

        /**
         * Show the ad if one isn't already showing.
         *
         * @param activity the activity that shows the app open ad
         * @param onShowAdCompleteListener the listener to be notified when an app open ad is complete
         */
        public void showAdIfAvailable(
                @NonNull final Activity activity,
                @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
            // If the app open ad is already showing, do not show the ad again.
            if (isShowingAd) {
                Log.d(LOG_TAG, "The app open ad is already showing.");
                return;
            }

            // If the app open ad is not available yet, invoke the callback then load the ad.
            if (!isAdAvailable()) {
                Log.d(LOG_TAG, "The app open ad is not ready yet.");
                onShowAdCompleteListener.onShowAdComplete();
                loadAd(activity);
                return;
            }

            Log.d(LOG_TAG, "Will show ad.");
            isShowingAd = true;
            appOpenAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        /** Called when full screen content is dismissed. */
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            appOpenAd = null;
                            isShowingAd = false;

                            Log.d(LOG_TAG, "onAdDismissedFullScreenContent.");
//                            Toast.makeText(activity, "onAdDismissedFullScreenContent", Toast.LENGTH_SHORT).show();

                            onShowAdCompleteListener.onShowAdComplete();
                            loadAd(activity);
                        }

                        /** Called when fullscreen content failed to show. */
                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            appOpenAd = null;
                            isShowingAd = false;

                            Log.d(LOG_TAG, "onAdFailedToShowFullScreenContent: " + adError.getMessage());
//                            Toast.makeText(activity, "onAdFailedToShowFullScreenContent", Toast.LENGTH_SHORT).show();

                            onShowAdCompleteListener.onShowAdComplete();
                            loadAd(activity);
                        }

                        /** Called when fullscreen content is shown. */
                        @Override
                        public void onAdShowedFullScreenContent() {
                            Log.d(LOG_TAG, "onAdShowedFullScreenContent.");
//                            Toast.makeText(activity, "onAdShowedFullScreenContent", Toast.LENGTH_SHORT).show();
                        }
                    });

            appOpenAd.show(activity);
        }
    }
    public boolean isShowingAd = false;
}
