package com.nstu.technician.feature

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.nstu.technician.R
import com.nstu.technician.databinding.ActivityContainerBinding

class ContainerActivity : BaseActivity() {

    private lateinit var mBinding: ActivityContainerBinding

    companion object {
        fun startActivity(activity: BaseActivity) {
            val intent = Intent(activity, ContainerActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_container)
        setSupportActionBar(mBinding.toolbar)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.host_fragment) as NavHostFragment? ?: return
        val navController = host.navController
        setupActionBar(navController, AppBarConfiguration(navController.graph))
    }

    private fun setupActionBar(navController: NavController, appBarConfiguration: AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

}