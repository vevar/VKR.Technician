package com.nstu.technician.feature

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.nstu.technician.R
import com.nstu.technician.databinding.ActivityContainerBinding

class ContainerActivity : BaseActivity() {

    private lateinit var mBinding: ActivityContainerBinding

    companion object {
        private const val TAG = "ContainerActivity"

        private const val EXTRA_USER_ID = "EXTRA_USER_ID"

        fun startActivity(activity: BaseActivity, userId: Int) {
            val intent = Intent(activity, ContainerActivity::class.java)
            intent.putExtra(EXTRA_USER_ID, userId)
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

        supportActionBar?.show()
    }

    private fun setupActionBar(navController: NavController, appBarConfiguration: AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}