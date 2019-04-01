package com.nstu.technician.feature.plan.jobs.map

import android.content.Context
import android.content.Intent
import com.nstu.technician.feature.BaseActivity

class MapActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, MapActivity::class.java)
            context.startActivity(intent)
        }
    }
}