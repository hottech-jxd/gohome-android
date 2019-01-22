package com.jxd.android.gohomeapp.libcommon.util

import android.graphics.Bitmap
import android.widget.ImageView
import com.google.zxing.WriterException
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.EncodeHintType
import java.util.*


class QrCodeUtils {

    /**
     * 获取建造者
     *
     * @param text 样式字符串文本
     * @return [RxQRCode.Builder]
     */
    fun builder( text: CharSequence): QrCodeUtils.Builder {
        return QrCodeUtils.Builder(text)
    }


    class Builder( val content: CharSequence) {

        private var backgroundColor = -0x1

        private var codeColor = -0x1000000

        private var codeSide = 800

        fun backColor(backgroundColor: Int): Builder {
            this.backgroundColor = backgroundColor
            return this
        }

        fun codeColor(codeColor: Int): Builder {
            this.codeColor = codeColor
            return this
        }

        fun codeSide(codeSide: Int): Builder {
            this.codeSide = codeSide
            return this
        }

        fun into(imageView: ImageView?): Bitmap? {
            val bitmap = QrCodeUtils().creatQRCode(content, codeSide, codeSide, backgroundColor, codeColor)
            if (imageView != null) {
                imageView!!.setImageBitmap(bitmap)
            }
            return bitmap
        }
    }


    //----------------------------------------------------------------------------------------------以下为生成二维码算法

    fun creatQRCode(
        content: CharSequence?,
        QR_WIDTH: Int,
        QR_HEIGHT: Int,
        backgroundColor: Int,
        codeColor: Int
    ): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            // 判断URL合法性
            if (content == null || "" == content || content.length < 1) {
                return null
            }
            val hints = Hashtable<EncodeHintType, String>()
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
            // 图像数据转换，使用了矩阵转换
            val bitMatrix =
                QRCodeWriter().encode(content.toString() + "", BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints)
            val pixels = IntArray(QR_WIDTH * QR_HEIGHT)
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (y in 0 until QR_HEIGHT) {
                for (x in 0 until QR_WIDTH) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = codeColor
                    } else {
                        pixels[y * QR_WIDTH + x] = backgroundColor
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888)
            bitmap!!.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT)
        } catch (e: WriterException) {
            e.printStackTrace()
        }

        return bitmap
    }

    fun creatQRCode(content: CharSequence, QR_WIDTH: Int, QR_HEIGHT: Int): Bitmap? {
        return creatQRCode(content, QR_WIDTH, QR_HEIGHT, -0x1, -0x1000000)
    }

    fun creatQRCode(content: CharSequence): Bitmap? {
        return creatQRCode(content, 800, 800)
    }

    /**
     * @param content   需要转换的字符串
     * @param QR_WIDTH  二维码的宽度
     * @param QR_HEIGHT 二维码的高度
     * @param iv_code   图片空间
     */
    fun createQRCode(content: String, QR_WIDTH: Int, QR_HEIGHT: Int, iv_code: ImageView) {
        iv_code.setImageBitmap(creatQRCode(content, QR_WIDTH, QR_HEIGHT))
    }

    /**
     * QR_WIDTH  二维码的宽度
     * QR_HEIGHT 二维码的高度
     *
     * @param content 需要转换的字符串
     * @param iv_code 图片空间
     */
    fun createQRCode(content: String, iv_code: ImageView) {
        iv_code.setImageBitmap(creatQRCode(content))
    }

}