package com.nstu.technician.feature.login

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nstu.technician.R
import com.nstu.technician.data.di.component.DaggerLoginComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.di.model.RepositoryModule
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.databinding.ActivityLoginBinding
import com.nstu.technician.di.component.login.DaggerLoginScreen
import com.nstu.technician.di.module.model.LoginModule
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseActivity
import com.nstu.technician.feature.ContainerActivity
import com.nstu.technician.feature.common.ErrorDialogFragment
import com.nstu.technician.feature.util.BaseViewModelFactory
import javax.inject.Inject

class LoginActivity : BaseActivity(), ErrorDialogFragment.ErrorDialogListener {

    companion object {
        private const val TAG = "LoginActivity"

        private const val STATE_USERNAME = "STATE_USERNAME"
        private const val STATE_PASSWORD = "STATE_PASSWORD"
        private const val STATE_MESSAGE = "STATE_MESSAGE"
    }

    @Inject
    lateinit var vmFactory: BaseViewModelFactory<LoginViewModel>

    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var mViewModel: LoginViewModel

    private lateinit var technicianObserver: Observer<Technician>
    private lateinit var messageObserver: Observer<Int?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        setupViewModel(savedInstanceState)
        setupView(mViewModel)
    }

    private fun setupInjection() {
        val app = App.getApp(this)
        val appComponent = app.getAppComponent()

        val loginComponent = DaggerLoginComponent.builder()
            .apiModule(ApiModule(ApiProvider()))
            .dataSourceModule(DataSourceModule())
            .repositoryModule(RepositoryModule())
            .build()
        val loginScreen = DaggerLoginScreen.builder()
            .appComponent(appComponent)
            .loginComponent(loginComponent)
            .loginModule(LoginModule())
            .build()
        loginScreen.inject(this)
    }

    private fun setupViewModel(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProviders.of(this, vmFactory).get(LoginViewModel::class.java)
        if (savedInstanceState != null) {
            mViewModel.username.value = savedInstanceState.getString(STATE_USERNAME) ?: ""
            mViewModel.password.value = savedInstanceState.getString(STATE_PASSWORD) ?: ""
            mViewModel.messageIdResource.value = savedInstanceState.getInt(STATE_MESSAGE)
        }

        technicianObserver = Observer {
            Log.d(TAG, "Technician auth is success")
            ContainerActivity.startActivity(this, it.user.oid)
        }
        messageObserver = Observer {
            if (it != null) {
                val fragment = supportFragmentManager.findFragmentByTag(ErrorDialogFragment.TAG)
                        as? ErrorDialogFragment

                if (fragment == null) {
                    val dialog = ErrorDialogFragment.createDialog(resources.getString(it))
                    dialog.show(supportFragmentManager, ErrorDialogFragment.TAG)
                }
            }
        }
    }

    private fun setupView(loginViewModel: LoginViewModel) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = loginViewModel
        mBinding.btnSingIn.setOnClickListener {
            mViewModel.singIn()
        }
    }

    override fun onStart() {
        super.onStart()
        mViewModel.technician.observe(this, technicianObserver)
        mViewModel.messageIdResource.observe(this, messageObserver)
    }

    override fun onStop() {
        super.onStop()
        mViewModel.technician.removeObserver(technicianObserver)
        mViewModel.messageIdResource.removeObserver(messageObserver)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.let {
            it.putString(STATE_USERNAME, mViewModel.username.value)
            it.putString(STATE_PASSWORD, mViewModel.password.value)
            if (mViewModel.messageIdResource.value != null) {
                it.putInt(STATE_MESSAGE, mViewModel.messageIdResource.value!!)
            }
        }
    }

    override fun onClickOk(dialogFragment: ErrorDialogFragment) {
        dialogFragment.dismiss()
        mViewModel.messageIdResource.value = null
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        if (fragment is ErrorDialogFragment) {
            fragment.errorDialogListener = this
        }
    }
}