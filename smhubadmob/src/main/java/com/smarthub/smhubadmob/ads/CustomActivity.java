 package com.smarthub.smhubadmob.ads;

 import android.app.ProgressDialog;
 import android.content.Intent;
 import android.content.pm.PackageManager;
 import android.os.Bundle;
 import android.view.View;
 import android.view.ViewGroup;
 import android.view.Window;
 import android.view.WindowManager;
 import android.widget.Toast;

 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;

 import com.google.android.gms.ads.AdView;
 import com.google.android.material.snackbar.Snackbar;
 import com.smarthub.smhubadmob.ads.admob.AdmobAdController;
 import com.smarthub.smhubadmob.BaseApplication;
 import com.smarthub.smhubadmob.R;

 import java.util.ArrayList;

 public class CustomActivity extends AppCompatActivity {
     public ProgressDialog progressDialog;
     public ViewGroup bannerHolder;
     public boolean isVisible = false;
     public boolean isInitialized = false;
     public boolean isNativeInitialized = false;
     public static final int READ_WRITE_STORAGE = 52;
     public ArrayList<AdView> bannerView = new ArrayList();

     public void showProgressDialog(){
         try {
             if (progressDialog==null){
                 progressDialog = new ProgressDialog(this);
                 progressDialog.setMessage("Preparing...");
                 progressDialog.setCanceledOnTouchOutside(true);
             }
             if (!progressDialog.isShowing())
                 progressDialog.show();
         }catch (Exception e){
//             AppLogger.d("unable to showProgressDialog:"+e.getLocalizedMessage());
         }
     }
     public void showProgressDialog(String msg){
//         AppLogger.d("showProgressDialog");
         try {
             if (progressDialog==null){
                 progressDialog = new ProgressDialog(this);
                 progressDialog.setCanceledOnTouchOutside(true);
             }
             progressDialog.setMessage(msg);
             if (!progressDialog.isShowing())
                 progressDialog.show();
         }catch (Exception e){
//             AppLogger.d("unable to showProgressDialog:"+e.getLocalizedMessage());
         }
     }

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

     public void showNewProgressDialog(){
//         AppLogger.d("showProgressDialog");
         try {
             if (progressDialog!=null && progressDialog.isShowing())
                 progressDialog.dismiss();
             progressDialog = new ProgressDialog(this);
             progressDialog.setMessage("Preparing...");
             progressDialog.setCanceledOnTouchOutside(true);
             progressDialog.show();
         }catch (Exception e){
             log("unable to showProgressDialog:"+e.getLocalizedMessage());
         }
     }
     public void hideProgressDialog(){
         try {
             if (progressDialog!=null && progressDialog.isShowing())
                 progressDialog.dismiss();
         }catch (Exception e){
             log("unable to hideProgressDialog:"+e.getLocalizedMessage());
         }
     }
     public View mainNativeViewHolder;

     public void onAdLoading(ViewGroup view) {
         mainNativeViewHolder = view;
         AdsController.getInstance().showNativeAd(view, this);
     }

     public void setProgressMsg(String msg){
         progressDialog.setMessage(msg);
     }

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         progressDialog = new ProgressDialog(this);
         progressDialog.setMessage("Preparing...");
         progressDialog.setCanceledOnTouchOutside(true);
         getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        try {
            if (AdmobAdController.getInstance()!=null)
                AdmobAdController.getInstance().setContext(this);
        }catch (Exception e){
//            AppLogger.d("CustomActivity onCreate :-"+ e.getLocalizedMessage());
        }
        BaseApplication.getInstance().currentFragmentName = getClass().getSimpleName();
     }

     @Override
     public void startActivity(Intent intent) {
         try {
             super.startActivity(intent);
             isInitialized = false;
             isNativeInitialized = false;
         }catch (Exception e){
//             AppLogger.d("e:"+e.getLocalizedMessage());
         }
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
         if (!isInitialized){
             if (bannerHolder==null && findViewById(R.id.rl_banner_holder)!=null)
                 bannerHolder = findViewById(R.id.rl_banner_holder);

             if (bannerHolder!=null) {
                 if (bannerHolder.getChildCount()>0 && bannerHolder.getChildAt(0)!=null && bannerHolder.getChildAt(0) instanceof AdView) {
                     AdView childAt = (AdView) bannerHolder.getChildAt(0);
                     childAt.destroy();
                 }
                 isInitialized = true;
                 AdsController.getInstance().showBannerAd(this, bannerHolder,getClass().getSimpleName());
             }
         }
     }

     @Override
     protected void onStop() {
         super.onStop();
         isVisible = false;
         hideProgressDialog();
     }

     @Override
     protected void onStart() {
         super.onStart();
         isVisible = true;
     }

     public void showToast(String msg) {
         Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
     }

     public void showSnackBar(String msg) {
         View view = findViewById(android.R.id.content);
         Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
         snackbar.show();
     }

     public void log(String msg) {
//         AppLogger.d(msg);
     }

     public Snackbar getSnackBar(String msg) {
         View view = findViewById(android.R.id.content);
         return Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
     }

     @Override
     protected void onPause() {
         super.onPause();
         isVisible = false;
         hideProgressDialog();
     }

     @Override
     protected void onDestroy() {
         isVisible = false;
         hideProgressDialog();
         progressDialog = null;
         for (AdView bannerView:bannerView){
             try {
                 bannerView.destroy();
             }catch (Exception e){

             }
         }
         bannerView.clear();
         super.onDestroy();
     }


 }
