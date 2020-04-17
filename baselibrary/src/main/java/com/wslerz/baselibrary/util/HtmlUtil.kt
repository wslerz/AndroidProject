package com.wslerz.baselibrary.util

import android.text.Html
import android.text.Spanned

/**
 *
 * @author by lzz
 * @date 2020/2/17
 * @description
 */
object HtmlUtil {
    private fun String.convertEnter() = replace("\n", "<br>")

    @JvmStatic
    fun getFontSpanned(
        color: String,
        colorStr: Any,
        start: String = "",
        end: String = ""
    ): Spanned {
        return Html.fromHtml(
            "${start.convertEnter()}<font color='$color'>${if (isEmpty(colorStr)) "" else colorStr}</font>${end.convertEnter()}"
        )
    }

    @JvmStatic
    fun getFontSpanned(
        color: String, colorStr: Any, start: String, end: String,
        color2: String, colorStr2: Any, start2: String, end2: String
    ): Spanned {
        var colorStrNoNull = colorStr
        var colorStrNoNull2 = colorStr2
        if (isEmpty(colorStrNoNull)) {
            colorStrNoNull = ""
        }
        if (isEmpty(colorStrNoNull2)) {
            colorStrNoNull2 = ""
        }
        return Html.fromHtml(
            start.convertEnter() + "<font color='" + color + "'>" + colorStrNoNull + "</font>" + end.convertEnter()
                    + start2.convertEnter() + "<font color='" + color2 + "'>" + colorStrNoNull2 + "</font>" + end2.convertEnter()
        )
    }

    @JvmStatic
    fun isEmpty(colorStr: Any?): Boolean {
        return colorStr == null || colorStr == "null"
    }
}