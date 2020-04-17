package com.wslerz.baselibrary.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream
import kotlin.math.roundToInt

/**
 *
 * @author by lzz
 * @date 2020/2/19
 * @description
 */
object FileUtils {
    @JvmStatic
    fun getFileProviderName(context: Context): String {
        return context.packageName + ".fileprovider"
    }

    /**
     * 获取缓存路径
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getCachePath(context: Context) = if (Environment.MEDIA_MOUNTED == Environment
            .getExternalStorageState() || !Environment.isExternalStorageRemovable()
    ) {
        context.externalCacheDir?.path ?: ""
    } else {
        context.cacheDir.path
    }

    /**
     * 压缩图片工具
     *
     * @param fileSrc
     * @return
     */
    @JvmStatic
    fun getSmallBitmap(context: Context, fileSrc: String?): File? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(fileSrc, options)
        options.inSampleSize =
            calculateInSampleSize(
                options,
                480,
                800
            )
        options.inJustDecodeBounds = false
        val img = BitmapFactory.decodeFile(fileSrc, options) ?: return null
        val filename =
            context.filesDir.toString() + File.separator + "avatar-" + System.currentTimeMillis() + ".jpg"
        saveBitmap2File(img, filename)
        return File(filename)
    }

    /**
     * 保存bitmap到文件
     *
     * @param bmp
     * @param filename
     * @return
     */
    @JvmStatic
    fun saveBitmap2File(bmp: Bitmap, filename: String?): Boolean {
        val format = CompressFormat.JPEG
        val quality = 50
        var stream: OutputStream? = null
        try {
            stream = FileOutputStream(filename)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return bmp.compress(format, quality, stream)
    }

    /**
     * 设置压缩的图片的大小设置的参数
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    @JvmStatic
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val heightRatio = height.toFloat().roundToInt() / reqHeight
            val widthRatio = width.toFloat().roundToInt() / reqWidth
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }
}
