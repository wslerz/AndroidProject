package com.wslerz.baselibrary.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiInfo
import com.wslerz.baselibrary.ext.wifiManager

/**
 *
 * @author by lzz
 * @date 2020/4/1
 * @description
 */
object WifiUtil {
    /**
     * 需要定位权限
     */
    @SuppressLint("MissingPermission")
    fun getContentWifi(context: Context): WifiInfo? {
        val wm = context.wifiManager
        if (wm != null) {
            return wm.connectionInfo
        }
        return null
    }

    /**
     * WifiInfo.getIpAddress -> String
     * 这段是转换成点分式IP的码
     */
    fun intToIp(ip: Int): String {
        return (ip and 0xFF).toString() + "." + (ip shr 8 and 0xFF) + "." + (ip shr 16 and 0xFF) + "." + (ip shr 24 and 0xFF)
    }

}