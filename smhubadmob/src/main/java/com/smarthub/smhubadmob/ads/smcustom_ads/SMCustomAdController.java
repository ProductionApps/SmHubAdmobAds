package com.smarthub.smhubadmob.ads.smcustom_ads;

public class SMCustomAdController {

    public static SMCustomAdController instance;

    public static SMCustomAdController getInstance(){
        if (instance==null)
            instance = new SMCustomAdController();
        return instance;
    }

//    public void showNativeAd(CustomActivity activity, ViewGroup container){
//        AppLogger.d("showNativeAd");
////        check if data is null or not
//       if (AdsController.getInstance().smNativeAds==null || AdsController.getInstance().smNativeAds.size()==0)
//           return;
//       SmNativeAd ad = AdsController.getInstance().smNativeAds.get(0);
//       for (SmNativeAd smAd : AdsController.getInstance().smNativeAds){
//           if (smAd.getImpressionCount() < ad.getImpressionCount())
//               ad = smAd;
//       }
//       if (ad!=null){
////           populate ad with UI
//           populateNativeAd(activity,container,ad);
//           AppLogger.d("populateNativeAd");
//       }
//    }

//    public void showBannerAd(CustomActivity activity, ViewGroup container){
//        AppLogger.d("showNativeAd");
////        check if data is null or not
//       if (AdsController.getInstance().smBannerAds==null || AdsController.getInstance().smBannerAds.size()==0)
//           return;
//       SmBannerAd ad = AdsController.getInstance().smBannerAds.get(0);
//       for (SmBannerAd smAd : AdsController.getInstance().smBannerAds){
//           if (smAd.getImpressionCount() < ad.getImpressionCount())
//               ad = smAd;
//       }
//       if (ad!=null){
////           populate ad with UI
//           populateBannerAd(activity,container,ad);
//           AppLogger.d("populateNativeAd");
//       }
//    }

//    void populateNativeAd(CustomActivity activity, ViewGroup container,SmNativeAd smNativeAd){
//        if (container==null)
//            return;
//        // Add the Ad view into the ad container.
//        LayoutInflater inflater = LayoutInflater.from(activity);
//        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
//        RelativeLayout root = (RelativeLayout) inflater.inflate(R.layout.sm_native_custom_ad, container, false);
//
//        ImageView appIcon = root.findViewById(R.id.app_icon);
//        ImageView mediaView = root.findViewById(R.id.media_view);
//        TextView appTitle = root.findViewById(R.id.app_title);
//        TextView adAdvertiser = root.findViewById(R.id.ad_advertiser);
//        TextView adDescription = root.findViewById(R.id.ad_description);
//        CardView ctaButton = root.findViewById(R.id.call_to_action_card);
//
//        container.removeAllViews();
//        container.addView(root);
//
//        Glide.with(activity).
//                load(smNativeAd.getAppIcon()).
//                into(appIcon);
//        Glide.with(activity).
//                load(smNativeAd.getMediaView()).
//                into(mediaView);
//        appTitle.setText(""+smNativeAd.getAppTitle());
//        adDescription.setText(""+smNativeAd.getAdDescription());
//        View.OnClickListener onClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + smNativeAd.getApp_id()));
//                    activity.startActivity(intent);
//                } catch (Exception e) {
//                    AppLogger.d("Exception :"+e.getLocalizedMessage());
//                    //                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
//        ctaButton.setOnClickListener(onClickListener);
//        appTitle.setOnClickListener(onClickListener);
//        adDescription.setOnClickListener(onClickListener);
//        appIcon.setOnClickListener(onClickListener);
//        mediaView.setOnClickListener(onClickListener);
//        adAdvertiser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(activity.getResources().getString(R.string.link_to_channel)));
//                    activity.startActivity(intent);
//                } catch (Exception e) {
//                    AppLogger.d("Exception :"+e.getLocalizedMessage());
//                }
//            }
//        });
//    }

//    void populateBannerAd(CustomActivity activity, ViewGroup container,SmBannerAd smNativeAd){
//        if (container==null)
//            return;
//        int layoutId = R.layout.sm_banner_custom_ad;
//        // Add the Ad view into the ad container.
//        LayoutInflater inflater = LayoutInflater.from(activity);
//        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
//        RelativeLayout root = (RelativeLayout) inflater.inflate(R.layout.sm_banner_custom_ad, container, false);
//        ImageView mediaView = root.findViewById(R.id.media_view);
//
//        container.removeAllViews();
//        container.addView(root);
//
//        String mediaUrl = smNativeAd.getMediaView();
//        if (container.getTag()!=null && container.getTag() instanceof String){
//            String tag  = container.getTag().toString();
//            if (tag.equals("medium")){
//                mediaUrl = smNativeAd.mediumUrl;
//            }else if (tag.equals("large")){
//                mediaUrl = smNativeAd.largeUrl;
//            }
//        }
//        Glide.with(activity).
//                load(mediaUrl).
//                into(mediaView);
//        View.OnClickListener onClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + smNativeAd.getApp_id()));
//                    activity.startActivity(intent);
//                } catch (Exception e) {
//                    AppLogger.d("Exception :"+e.getLocalizedMessage());
//                    //                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
//        mediaView.setOnClickListener(onClickListener);
//    }

}
