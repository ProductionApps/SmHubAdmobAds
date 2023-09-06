package com.smarthub.smhubadmob.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smarthub.smhubadmob.ads.AdsController;
import com.smarthub.smhubadmob.ads.CustomActivity;
import com.smarthub.smhubadmob.BaseApplication;
import com.smarthub.smhubadmob.R;

public class BaseFragment extends Fragment {

    ViewGroup bannerHolder;
    public boolean isAttached = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        isAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        BaseApplication.getInstance().currentFragmentName = getClass().getSimpleName();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bannerHolder = view.findViewById(R.id.rl_banner_holder);
        if (bannerHolder!=null && requireActivity() instanceof CustomActivity) {
            AdsController.getInstance().showBannerAd((CustomActivity) requireActivity(), bannerHolder,getClass().getSimpleName());
        }
    }

}
