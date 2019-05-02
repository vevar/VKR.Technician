package com.nstu.technician.feature

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.nstu.technician.R
import com.nstu.technician.databinding.ActivityContainerBinding
import com.nstu.technician.feature.plan.jobs.PlanJobsFragmentArgs

class ContainerActivity : BaseActivity() {

    private lateinit var mBinding: ActivityContainerBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    companion object {
        private const val TAG = "ContainerActivity"

        private const val EXTRA_TECHNICIAN_ID = "technicianId"

        fun startActivity(activity: BaseActivity, userId: Long) {
            val intent = Intent(activity, ContainerActivity::class.java)
            intent.putExtra(EXTRA_TECHNICIAN_ID, userId)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_container)
        setSupportActionBar(findViewById(R.id.toolbar))
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.host_fragment) as NavHostFragment? ?: return
        host.arguments = navArgs<PlanJobsFragmentArgs>().value.toBundle()
        val navController = host.navController
        setupActionBar(navController, AppBarConfiguration(navController.graph))

        supportActionBar?.show()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun setupActionBar(navController: NavController, appBarConfiguration: AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun createLocationRequest(intervalInMillisecond: Long): LocationRequest {
        val request = LocationRequest()
        request.interval = intervalInMillisecond
        return request
    }

}