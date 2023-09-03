package com.smarthub.smhubads.ads.model

import androidx.annotation.Keep

@Keep
class AdsPriority {
    var admobAds: Boolean = true
    var forceAds: Boolean = false
    var unityAds: Boolean = false
    var unityBanner: Boolean = false
    var unityReward: Boolean = false
    var unityInterstitial: Boolean = false
    var admobRewardAds: Boolean = true
    var admobBannerAds: Boolean = true
    var admobAppOpenAds: Boolean = false
    var admobInterstitialAds: Boolean = true
    var admobNativeAds: Boolean = true
    var fbAds: Boolean = false
    var fbBannerAds: Boolean = false
    var fbInterstitialAds: Boolean = false
    var fbNativeAds: Boolean = false
    var maxAds: Boolean = false
    var maxBannerAds: Boolean = false
    var maxRewardAds: Boolean = false
    var maxInterstitialAds: Boolean = false
    var maxNativeAds: Boolean = false
    var maxAppOpenAds: Boolean = false

}