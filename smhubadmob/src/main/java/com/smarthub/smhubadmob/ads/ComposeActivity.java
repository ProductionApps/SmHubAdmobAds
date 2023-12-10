 package com.smarthub.smhubadmob.ads;

 import android.content.pm.PackageManager;
 import android.os.Bundle;
 import android.view.Window;
 import android.view.WindowManager;

 import androidx.activity.ComponentActivity;
 import androidx.annotation.NonNull;

 import com.smarthub.smhubadmob.BaseApplication;

 public class ComposeActivity extends ComponentActivity {

     public boolean isVisible = false;
     public static final int READ_WRITE_STORAGE = 52;


     public void isPermissionGranted(boolean isGranted, String permission) {

     }

     public void makeFullScreen() {
         requestWindowFeature(Window.FEATURE_NO_TITLE);
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
     }

     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         switch (requestCode) {
             case READ_WRITE_STORAGE:
                 isPermissionGranted(grantResults[0] == PackageManager.PERMISSION_GRANTED, permissions[0]);
                 break;
         }
     }

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        try {
//            if (AdmobAdController.getInstance()!=null)
//                AdmobAdController.getInstance().setContext();
//        }catch (Exception e){
//            AppLogger.d("CustomActivity onCreate :-"+ e.getLocalizedMessage());
//        }
        BaseApplication.getInstance().currentFragmentName = getClass().getSimpleName();
     }

     @Override
     protected void onResume() {
         super.onResume();
         isVisible = true;
         if (BaseApplication.getInstance().interstitialListener!=null){
             BaseApplication.getInstance().interstitialListener.onAdDismissed(true);
             BaseApplication.getInstance().interstitialListener = null;
//             AppLogger.d("onResume interstitialListener");
             return;
         }
     }

     @Override
     protected void onStop() {
         super.onStop();
         isVisible = false;
     }

     @Override
     protected void onStart() {
         super.onStart();
         isVisible = true;
     }

     @Override
     protected void onPause() {
         super.onPause();
         isVisible = false;
     }

     @Override
     protected void onDestroy() {
         isVisible = false;
         super.onDestroy();
     }


 }
