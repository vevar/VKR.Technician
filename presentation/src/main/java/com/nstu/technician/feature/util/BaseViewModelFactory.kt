package com.nstu.technician.feature.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseViewModelFactory<VM: ViewModel>(
    private val classType: Class<VM>
) : ViewModelProvider.Factory {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(classType)) {
            return createViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }

    abstract fun createViewModel(): VM

}