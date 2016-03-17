package com.crazy.toutiaonews.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetState {

    private static final int CMNET = 3;
    private static final int CMWAP = 2;
    private static final int WIFI = 1;

//    // 判断是否有网络连接
//    public boolean isNetworkConnected(Context context) {
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
//            if (mNetworkInfo != null) {
//                return mNetworkInfo.isAvailable();
//            }
//        }
//        return false;
//    }
//
//    // 判断WIFI网络是否可用
//    public boolean isWifiConnected(Context context) {
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
//                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//            if (mWiFiNetworkInfo != null) {
//                return mWiFiNetworkInfo.isAvailable();
//            }
//        }
//        return false;
//    }
//
//    // 判断MOBILE网络是否可用
//    public boolean isMobileConnected(Context context) {
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mMobileNetworkInfo = mConnectivityManager
//                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            if (mMobileNetworkInfo != null) {
//                return mMobileNetworkInfo.isAvailable();
//            }
//        }
//        return false;
//    }
//
//    // 获取当前网络连接的类型信息
//    public static int getConnectedType(Context context) {
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
//            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
//                return mNetworkInfo.getType();
//            }
//        }
//        return -1;
//    }


    /**
     * 获取当前的网络状态  -1：没有网络  1：WIFI网络 2：wap网络  3：net网络
     * @param context
     * @return
     */
    public static int getAPNType(Context context){

        int netType = -1;

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo==null){
            return netType;
        }

        int nType = networkInfo.getType();
        if(nType==ConnectivityManager.TYPE_MOBILE){

            Log.e("tag", "networkInfo.getExtraInfo() is " + networkInfo.getExtraInfo());

            if(networkInfo.getExtraInfo().toLowerCase().equals("cmnet")){
                netType = CMNET;
            } else {
                netType = CMWAP;
            }

        } else if (nType==ConnectivityManager.TYPE_WIFI){
            netType = WIFI;
        }

        return netType;
    }
}
