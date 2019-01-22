package com.jxd.android.gohomeapp.libcommon.util

import android.app.ActivityManager
import android.content.Context
import java.lang.Exception
import java.math.BigDecimal
import android.graphics.Bitmap.CompressFormat
import android.graphics.Bitmap
import java.io.*
import java.nio.file.Files.delete
import java.nio.file.Files.exists
import com.facebook.common.file.FileUtils.mkdirs
import java.nio.file.Files.isDirectory
import java.nio.file.Files.exists






/**
 * Created by jinxiangdong on 2017/12/16.
 */
object AppUtil {

    fun isAppLoaded(context: Context, className: String): Boolean {
        val packageName = context.packageName
        val activityManager = context.getSystemService(android.content.Context.ACTIVITY_SERVICE) as ActivityManager
        val rti = activityManager.getRunningTasks(10)
        for (item in rti) {
            if (item.baseActivity.className.startsWith(className) || item.topActivity.className.startsWith(className))
                return true
        }
        return false
    }

    fun getFileName( path:String? ):String{
        if(path==null) return ""
        var index = path.lastIndexOf("/"  )
        //var pref=""
        //var index2 = path.lastIndexOf("/", 0, true)
        var name = ""
        //if(index>=0){
        //    pref = path.substring(index)
        //}
        if(index>=0 && index<(path.length-1)){
            name = path.substring(index+1)
        }else{
            name = path
        }

        return name
    }

    fun getFileExt(path:String):String{
        var index = path.lastIndexOf("." )
        var pref=""
        if(index>=0){
            pref = path.substring(index)
        }
        return pref
    }

    //    public static boolean isInt(BigDecimal data){
    //        if(data==null) return false;
    //        if(data.toPlainString())
    //    }


    fun fileToByte(filePath: String): ByteArray? {
        try {
            val fileInputStream = FileInputStream(filePath)
            return input2Byte(fileInputStream)
        } catch (ioex: IOException) {
            return null
        }

    }

    private fun input2Byte(`is`: InputStream?): ByteArray? {
        if (`is` == null) return null
        try {
            val os = ByteArrayOutputStream()
            val b = ByteArray(1024)
            var len =`is`.read(b, 0, 1024)

            while ((len ) != -1) {
                os.write(b, 0, len)

                len = `is`.read(b, 0, 1024)
            }
            return os.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }


    /**
     * 检测是否安装了指定包名的app
     */
    fun checkInstallApp( context: Context , packageName:String):Boolean{
        try {
            var packageManager = context.packageManager
            var pList = packageManager.getInstalledPackages(0)
            for (item in pList) {
                if (item.packageName.equals(packageName, true)) return true
            }
            return false
        }catch (ex:Exception){
            return false
        }
    }


    /**
     * Save the bitmap.
     *
     * @param src     The source of bitmap.
     * @param file    The file.
     * @param format  The format of the image.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return `true`: success<br></br>`false`: fail
     */
    fun save( src: Bitmap, file: File, format: CompressFormat, recycle: Boolean ): Boolean {
        if (isEmptyBitmap(src) || !createFileByDeleteOldFile(file)) return false
        var os: OutputStream? = null
        var ret = false
        try {
            os = BufferedOutputStream(FileOutputStream(file))
            ret = src.compress(format, 100, os)
            if (recycle && !src.isRecycled) src.recycle()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (os != null) {
                    os!!.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return ret
    }

    private fun isEmptyBitmap(src: Bitmap?): Boolean {
        return src == null || src.width == 0 || src.height == 0
    }

    private fun createFileByDeleteOldFile(file: File?): Boolean {
        if (file == null) return false
        if (file.exists() && !file.delete()) return false
        if (!createOrExistsDir(file.parentFile)) return false
        try {
            return file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

    }

    private fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

}
