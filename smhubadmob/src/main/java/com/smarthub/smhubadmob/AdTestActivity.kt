package com.smarthub.smhubadmob

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.gms.ads.rewarded.RewardItem
import com.smarthub.smhubadmob.ads.AdsController
import com.smarthub.smhubadmob.ads.CustomActivity
import com.smarthub.smhubadmob.ads.listeners.RewardListener

class AdTestActivity : CustomActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_test)

        var nativeLayout = findViewById<FrameLayout>(R.id.native_view_holder)
        var rewardText = findViewById<TextView>(R.id.test_reward_ad)
        onAdLoading(nativeLayout)
        rewardText.setOnClickListener {
            AdsController.getInstance().showRewarded(this,object:RewardListener{
                override fun onRewardEarned(rewardItem: RewardItem?) {
                    showToast("onRewardEarned")
                }

                override fun onRewardFailed() {
                    showToast("onRewardFailed")
                }

                override fun onRewardLoadFailed() {

                    showToast("onRewardLoadFailed")
                }

            })
        }
    }
}