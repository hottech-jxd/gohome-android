package com.jxd.android.gohomeapp.libcommon.util

import android.app.ActivityManager
import android.content.Context
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.math.BigDecimal

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

}
