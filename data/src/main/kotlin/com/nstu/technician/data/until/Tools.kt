package com.nstu.technician.data.until

import android.util.Log

fun logCodeOfResponse(tag: String, code: Int) {
    Log.d(tag, "Code of response: $code")
}

fun <T> isNotEqualSafeList(list: List<T>?, other: List<T>?): Boolean {
    if (list != null && other != null) {
        if (!list.containsAll(other)) return true
    } else if (list != other) return true
    return false
}