package com.smarthub.smhubadmob.ads.listeners;

import com.google.android.gms.ads.rewarded.RewardItem;

public interface RewardListener {
    void onRewardEarned(RewardItem rewardItem);

    void onRewardFailed();

    void onRewardLoadFailed();
}
