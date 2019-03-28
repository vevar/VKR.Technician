package com.nstu.technician.feature.login

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nstu.technician.R
import com.nstu.technician.databinding.ActivityLoginBinding
import com.nstu.technician.feature.BaseActivity
import com.nstu.technician.feature.common.ErrorDialog
import com.nstu.technician.feature.util.TAG_ERROR_DIALOG
import com.nstu.technician.feature.util.TAG_PRESENTATION

class LoginActivity : BaseActivity() {
    companion object {
        private const val STATE_USERNAME = "STATE_USERNAME"
        private const val STATE_PASSWORD = "STATE_PASSWORD"
    }

    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel(savedInstanceState)
        setupView(mViewModel)
    }

    private fun setupViewModel(savedInstanceState: Bundle?) {
        val factory = LoginViewModelFactory(this)
        mViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        if (savedInstanceState != null) {
            mViewModel.username.value = savedInstanceState.getString(STATE_USERNAME) ?: ""
            mViewModel.password.value = savedInstanceState.getString(STATE_PASSWORD) ?: ""
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
        mViewModel.technician.observe(this, Observer {
            Log.d(TAG_PRESENTATION, "Technician auth is success")
        })
        mViewModel.message.observe(this, Observer {
            val dialog = ErrorDialog.createDialog(it)
            dialog.show(supportFragmentManager, TAG_ERROR_DIALOG)
        })
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        if (outState != null) {
            outState.putString(STATE_USERNAME, mViewModel.username.value)
            outState.putString(STATE_PASSWORD, mViewModel.password.value)
            outState.putString(ErrorDialog.EXTRA_MESSAGE, mViewModel.password.value)
        }
    }
}