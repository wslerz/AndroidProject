package com.wslerz.baselibrary.util

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 *
 * @author by lzz
 * @date 2020/2/19
 * @description
 */
object IntentUtils {
    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    @JvmStatic
    fun callPhone(context: Context?, phoneNum: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse("tel:$phoneNum")
        intent.data = data
        context?.startActivity(intent)

    }
}