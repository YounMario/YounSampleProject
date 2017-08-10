package com.younchen.younsampleproject.commons.utils;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by yinlongquan on 2017/8/10.
 */

public class NetUtils {

    public static final int NETWORK_TYPE_WIFI = 3;
    public static final int NETWORK_TYPE_3G = 2;
    public static final int NETWORK_TYPE_2G = 1;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int NETWORK_TYPE_NONE = 4;
    public static final int NETWORK_TYPE_4G = 5;


    public static boolean IsMobileNetworkAvailable(Context context) {
        if (IsNetworkAvailable(context)) {
            return !IsWifiNetworkAvailable(context);
        }
        return false;
    }

    public static boolean IsWifiNetworkAvailable(Context context) {
        // Monitor network connections (Wi-Fi, GPRS, UMTS, etc.)
        // mobile 3G Data Network
        ConnectivityManager conmgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conmgr == null) {
            return false;
        }

        NetworkInfo info = null;
        try {
            info = conmgr
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info == null) {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }

        NetworkInfo.State wifi = info.getState(); // 显示wifi连接状态
        return wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING;

    }

    public static boolean IsRoaming(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }

        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null) {
            if (info.isRoaming()) {
                return true;
            }
        }
        return false;
    }

    public static boolean IsNetworkAvailable(Context context) {

        ConnectivityManager conmgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conmgr == null) {
            return false;
        }

        // 修改解决判断网络时的崩溃
        // mobile 3G Data Network
        try {
            NetworkInfo net3g = conmgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (net3g != null) {
                NetworkInfo.State mobile = net3g.getState();// 显示3G网络连接状态
                if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
                    return true;
            }
        } catch (Exception e) {
        }

        try {
            NetworkInfo netwifi = conmgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (netwifi != null) {
                NetworkInfo.State wifi = netwifi.getState(); // wifi
                // 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
                if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
                    return true;
            }
        } catch (Exception e) {
        }

        return false;
    }


    /**
     * Return the ssid of wifi is it's connected, otherwise return null.
     */
    public static String getWifiSSID(Context context) {
        if (IsWifiNetworkAvailable(context)) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Service.WIFI_SERVICE);
            WifiInfo info = wifiManager.getConnectionInfo();
            String ssid = info.getSSID();
            return ssid;
        }
        return null;
    }

    /**
     * 返回网卡地址 注意: 网卡地址只有当Wifi开启时才能获得
     *
     * @param context
     * @param fail    失败后返回值
     * @return
     */
    public static String getMacAddress(Context context, String fail) {
        WifiManager wimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String mac = wimanager.getConnectionInfo().getMacAddress();
        return TextUtils.isEmpty(mac) ? fail : mac;
    }

    /**
     * 获取BSSID属性（所连接的WIFI设备的MAC地址） 注意: 网卡地址只有当Wifi开启时才能获得
     *
     * @param context
     * @param fail    失败后返回值
     * @return
     */
    public static String getWifiBSSID(Context context, String fail) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String bssid = "";
        if (wifiInfo != null) {
            bssid = wifiInfo.getBSSID();
        }
        return TextUtils.isEmpty(bssid) ? fail : bssid;
    }

    /**
     * @param context
     * @return 返回5种网络类型，0-->未知网络，1-->2G网络，2-->3G网络，3-->wifi网络，4-->无网络,5-->4G
     * 注意：只有wifi类型的判断是准确的，其他类型相对都有适配问题
     * @author lin
     */
    public static int getNetworkState(Context context) {
        if (context == null)
            return NETWORK_TYPE_UNKNOWN;

        int networkType = NETWORK_TYPE_NONE;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                int type = info.getType();
                if (type == ConnectivityManager.TYPE_WIFI) {
                    if (isWiFiActive(context)) {
                        networkType = NETWORK_TYPE_WIFI;
                    } else {
                        networkType = NETWORK_TYPE_NONE;
                    }
                } else {
                    int subType = info.getSubtype();
                    if (isMobile2G(subType)) {
                        networkType = NETWORK_TYPE_2G;
                    } else if (isMobile4G(subType)) {
                        networkType = NETWORK_TYPE_4G;
                    } else {
                        networkType = NETWORK_TYPE_3G;
                    }
                }
            } else {
                networkType = NETWORK_TYPE_NONE;
            }
        } catch (Exception ex) {
            networkType = NETWORK_TYPE_UNKNOWN;
        }

        return networkType;
    }

    private static boolean isMobile4G(int subType) {
        return subType == TelephonyManager.NETWORK_TYPE_LTE;
    }

    /**
     * 判断Wifi是否可用
     *
     * @return true:可用 false:不可用
     */
    public static boolean isWiFiActive(Context context) {
        if (context == null)
            return false;
        boolean bReturn = false;
        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
        if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
            bReturn = true;
        }
        return bReturn;
    }

    /**
     * 判断是否2G网络
     *
     * @param type
     * @return
     */
    public static boolean isMobile2G(int type) {
        return type == TelephonyManager.NETWORK_TYPE_GPRS
                || type == TelephonyManager.NETWORK_TYPE_EDGE
                || type == TelephonyManager.NETWORK_TYPE_UMTS
                || type == TelephonyManager.NETWORK_TYPE_1xRTT;
    }
}
