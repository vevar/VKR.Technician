package com.nstu.technician.feature.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment

const val PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 1

fun checkPermissionLocation(context: Context): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && PermissionChecker.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED
}

fun requestLocationPermission(fragment: Fragment) {
    fragment.requestPermissions(
        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
        PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION
    )
}