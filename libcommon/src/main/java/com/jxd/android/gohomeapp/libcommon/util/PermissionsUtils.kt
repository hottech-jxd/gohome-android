package com.jxd.android.gohomeapp.libcommon.util

import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.app.Activity
import android.support.annotation.NonNull


/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.util
 * @ClassName:      PermissionsUtils
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/15 9:29
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/15 9:29
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class PermissionsUtils {

    fun with(activity: Activity): PermissionsUtils.Builder {
        return Builder(activity)
    }

    class Builder(@param:NonNull private val mActivity: Activity) {
        private val permissionList: MutableList<String>

        init {
            permissionList = ArrayList()
        }

        /**
         * Determine whether *you* have been granted a particular permission.
         *
         * @param permission The name of the permission being checked.
         * @return [PackageManager.PERMISSION_GRANTED] if you have the
         * permission, or [PackageManager.PERMISSION_DENIED] if not.
         * @see PackageManager.checkPermission
         */
        fun addPermission(@NonNull permission: String): Builder {
            if (!permissionList.contains(permission)) {
                permissionList.add(permission)
            }
            return this
        }

        fun initPermission(): List<String> {
            val list = ArrayList<String>()
            for (permission in permissionList) {
                if (ActivityCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                    list.add(permission)
                }
            }
            if (list.size > 0) {
                ActivityCompat.requestPermissions(mActivity, list.toTypedArray(), 1)
            }
            return list
        }

    }


}