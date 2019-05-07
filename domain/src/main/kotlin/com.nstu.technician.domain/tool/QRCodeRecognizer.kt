package com.nstu.technician.domain.tool

interface QRCodeRecognizer<T> {

    fun getString(obj: T): List<String>
}