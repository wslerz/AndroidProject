package com.wslerz.baselibrary.util

import android.content.Context
import android.content.SharedPreferences
import androidx.collection.SimpleArrayMap
import com.wslerz.baselibrary.base.BaseApp

/**
 *
 * @author by lzz
 * @date 2020/2/19
 * @description
 */
class SPUtils private constructor(spName: String, context: Context) {

    private val sp: SharedPreferences = context.applicationContext
        .getSharedPreferences(spName, Context.MODE_PRIVATE)

    fun put(key: String, value: String) {
        sp.edit().putString(key, value).apply()
    }

    fun put(key: String, value: Long) {
        sp.edit().putLong(key, value).apply()
    }

    fun getString(key: String): String {
        return getString(key, "")
    }

    fun getString(key: String, defaultValue: String): String {
        return sp.getString(key, defaultValue) ?: ""
    }

    fun getLong(key: String?, def: Int): Double {
        return sp.getLong(key, def.toLong()).toDouble()
    }

    companion object {
        private lateinit var mContext: Context
        private val SP_UTILS_MAP: SimpleArrayMap<String, SPUtils> =
            SimpleArrayMap()

        fun getInstance(context: Context): SPUtils {
            return getInstance(
                "",
                context
            )
        }

        fun getInstance(spName: String, context: Context): SPUtils {
            var spName = spName
            if (isSpace(spName)) spName = "ztsSP"
            var spUtils = SP_UTILS_MAP[spName]
            if (spUtils == null) {
                mContext = context
                spUtils = SPUtils(spName, context)
                SP_UTILS_MAP.put(spName, spUtils)
            }
            return spUtils
        }

        val instance: SPUtils
            get() = getInstance(
                "",
                mContext ?: BaseApp.context
            )

        private fun isSpace(s: String): Boolean {
            var i = 0
            val len = s.length
            while (i < len) {
                if (!Character.isWhitespace(s[i])) {
                    return false
                }
                ++i
            }
            return true
        }
    }

}
