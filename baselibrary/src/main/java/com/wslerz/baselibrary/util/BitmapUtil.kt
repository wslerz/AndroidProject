package com.wslerz.baselibrary.util

import android.annotation.TargetApi
import android.content.ContentResolver
import android.content.Context
import android.graphics.*
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.wslerz.baselibrary.util.ScreenUtil.getScreenHeight
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 *
 * @author by lzz
 * @date 2020/2/19
 * @description
 */
/**
 * @author by lzz
 * @date 2019/12/19
 * @description
 */
object BitmapUtil {
    fun getIconBitmap(context: Context?, iconId: Int): Bitmap? {
        return try {
            val icon = ContextCompat.getDrawable(context!!, iconId) ?: return null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && icon is AdaptiveIconDrawable) {
                val bitmap = Bitmap.createBitmap(
                    icon.getIntrinsicWidth(),
                    icon.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                icon.setBounds(0, 0, canvas.width, canvas.height)
                icon.draw(canvas)
                bitmap
            } else {
                (icon as BitmapDrawable).bitmap
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * bitmp 转自己诶
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    fun bmpToByteArray(bmp: Bitmap, needRecycle: Boolean): ByteArray {
        val output = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output)
        if (needRecycle) {
            bmp.recycle()
        }
        val result = output.toByteArray()
        try {
            output.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun getImageSmall(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val width = bm.width
        val height = bm.height
        // 设置想要的大小
// 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
    }

    /**
     * 获取图标资源
     *
     * @return
     */
    fun getResBitmap(context: Context, drawableId: Int): Bitmap? {
        val drawable =
            ContextCompat.getDrawable(context.applicationContext, drawableId)
        if (drawable is BitmapDrawable) {
            return BitmapFactory.decodeResource(
                context.applicationContext.resources, drawableId
            )
        } else if (drawable is VectorDrawable) {
            return getBitmap(drawable)
        }
        return null
    }

    /**
     * 网络图片URL转bipmap
     *
     * @param url 网络图片URL
     * @return
     */
    fun returnBipmap(url: String?): Bitmap? {
        var imgUrl: URL? = null
        var bitmap: Bitmap? = null
        try {
            imgUrl = URL(url)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        try {
            val conn =
                imgUrl!!.openConnection() as HttpURLConnection
            conn.doInput = true
            conn.connect()
            val inputStream = conn.inputStream
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getBitmap(vectorDrawable: VectorDrawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return bitmap
    }
    /*    */
    /**
     * 根据指定的图像路径和大小来获取缩略图 此方法有两点好处： 1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。 2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
     * 用这个工具生成的图像不会被拉伸。
     *
     * @param imagePath 图像的路径
     * @param width     指定输出图像的宽度
     * @param height    指定输出图像的高度
     * @return 生成的缩略图
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    fun getImageThumbnail(imagePath: String?, width: Int, height: Int): Bitmap? {
        var bitmap: Bitmap? = null
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options)
        options.inJustDecodeBounds = false // 设为 false
        // 计算缩放比
        val h = options.outHeight
        val w = options.outWidth
        val beWidth = w / width
        val beHeight = h / height
        var be = 1
        be = if (beWidth < beHeight) {
            beWidth
        } else {
            beHeight
        }
        if (be <= 0) {
            be = 1
        }
        options.inSampleSize = be
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options)
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(
            bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT
        )
        return bitmap
    }

    /**
     * 根据图片的ID得到缩略图
     *
     * @param cr
     * @param imageId
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    fun getThumbnailsFromImageId(cr: ContentResolver?, imageId: Int): Bitmap? {
        var bitmap: Bitmap? = null
        val options = BitmapFactory.Options()
        options.inDither = false
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        // via imageid get the bimap type thumbnail in thumbnail table.
        bitmap = MediaStore.Images.Thumbnails.getThumbnail(
            cr, imageId.toLong(), MediaStore.Images.Thumbnails.MINI_KIND, options
        )
        return bitmap
    }

    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    fun getBitmapFormUri(
        ac: Context,
        uri: Uri,
        sWidth: Int,
        sHeight: Int
    ): Bitmap? {
        try {
            var input = ac.contentResolver.openInputStream(uri)
            val onlyBoundsOptions = BitmapFactory.Options()
            onlyBoundsOptions.inJustDecodeBounds = true
            onlyBoundsOptions.inDither = true //optional
            onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888 //optional
            BitmapFactory.decodeStream(input, null, onlyBoundsOptions)
            input?.close()
            val originalWidth = onlyBoundsOptions.outWidth
            val originalHeight = onlyBoundsOptions.outHeight
            if (originalWidth == -1 || originalHeight == -1) return null
            //图片分辨率以480x800为标准
//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            var be = 1 //be=1表示不缩放
            if (originalWidth > originalHeight && originalWidth > sWidth) { //如果宽度大的话根据宽度固定大小缩放
                be = (originalWidth / sWidth)
            } else if (originalWidth < originalHeight && originalHeight > sHeight) { //如果高度高的话根据宽度固定大小缩放
                be = (originalHeight / sHeight)
            }
            if (be <= 0) be = 1
            //比例压缩
            val bitmapOptions = BitmapFactory.Options()
            bitmapOptions.inSampleSize = be //设置缩放比例
            bitmapOptions.inDither = true //optional
            bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888 //optional
            input = ac.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions)
            input?.close()
            bitmap?.let {
                return compressImage(
                    bitmap
                ) //再进行质量压缩
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    fun compressImage(image: Bitmap): Bitmap? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos) //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        var options = 100
        while (baos.toByteArray().size / 1024 > 100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset() //重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos) //这里压缩options%，把压缩后的数据存放到baos中
            options -= 10 //每次都减少10
        }
        val isBm =
            ByteArrayInputStream(baos.toByteArray()) //把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null)
    }

    /**
     * bitmap圆形处理
     *
     * @param bitmap
     * @return
     */
    fun toRoundBitmap(bitmap: Bitmap): Bitmap {
        var width = bitmap.width
        var height = bitmap.height
        val roundPx: Float
        val left: Float
        val top: Float
        val right: Float
        val bottom: Float
        val dst_left: Float
        val dst_top: Float
        val dst_right: Float
        val dst_bottom: Float
        if (width <= height) {
            roundPx = width / 2.toFloat()
            top = 0f
            bottom = width.toFloat()
            left = 0f
            right = width.toFloat()
            height = width
            dst_left = 0f
            dst_top = 0f
            dst_right = width.toFloat()
            dst_bottom = width.toFloat()
        } else {
            roundPx = height / 2.toFloat()
            val clip = (width - height) / 2.toFloat()
            left = clip
            right = width - clip
            top = 0f
            bottom = height.toFloat()
            width = height
            dst_left = 0f
            dst_top = 0f
            dst_right = height.toFloat()
            dst_bottom = height.toFloat()
        }
        val output = Bitmap.createBitmap(
            width,
            height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val src =
            Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        val dst = Rect(
            dst_left.toInt(),
            dst_top.toInt(),
            dst_right.toInt(),
            dst_bottom.toInt()
        )
        val rectF = RectF(dst)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, src, dst, paint)
        return output
    }

    /**
     * 判断图片是否是长图
     *
     * @param height 图片高
     * @param width  图片宽
     * @return 是 否
     */
    fun isLongBitmap(
        context: Context,
        height: Float,
        width: Float
    ): Boolean {
        val screenHeight = getScreenHeight(context.applicationContext)
        return height > screenHeight && height / width > 2.3
    }
}