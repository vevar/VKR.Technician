package com.nstu.technician.feature.login

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nstu.technician.R
import com.nstu.technician.databinding.ActivityLoginBinding
import com.nstu.technician.domain.model.Technician
import com.nstu.technician.feature.BaseActivity
import com.nstu.technician.feature.common.ErrorDialogFragment

class LoginActivity : BaseActivity(), ErrorDialogFragment.ErrorDialogListener {

    companion object {
        private const val TAG = "LoginActivity"

        private const val STATE_USERNAME = "STATE_USERNAME"
        private const val STATE_PASSWORD = "STATE_PASSWORD"
        private const val STATE_MESSAGE = "STATE_MESSAGE"
    }

    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var mViewModel: LoginViewModel

    private lateinit var technicianObserver: Observer<Technician>
    private lateinit var messageObserver: Observer<Int?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel(savedInstanceState)
        setupView(mViewModel)
    }

    private fun setupViewModel(savedInstanceState: Bundle?) {
        val factory = LoginViewModelFactory()
        mViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        if (savedInstanceState != null) {
            mViewModel.username.value = savedInstanceState.getString(STATE_USERNAME) ?: ""
            mViewModel.password.value = savedInstanceState.getString(STATE_PASSWORD) ?: ""
            mViewModel.messageIdResource.value = savedInstanceState.getInt(STATE_MESSAGE)
        }

        technicianObserver = Observer {
            Log.d(TAG, "Technician auth is success")
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