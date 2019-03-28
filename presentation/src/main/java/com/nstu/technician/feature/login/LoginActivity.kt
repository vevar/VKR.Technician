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
import com.nstu.technician.feature.util.TAG_ERROR_DIALOG
import com.nstu.technician.feature.util.TAG_PRESENTATION

class LoginActivity : BaseActivity(), ErrorDialogFragment.ErrorDialogListener {

    companion object {
        private const val STATE_USERNAME = "STATE_USERNAME"
        private const val STATE_PASSWORD = "STATE_PASSWORD"
    }

    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var mViewModel: LoginViewModel

    private lateinit var technicianObserver: Observer<Technician>
    private lateinit var messageObserver: Observer<String>


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

        technicianObserver = Observer {
            Log.d(TAG_PRESENTATION, "Technician auth is success")
        }
        messageObserver = Observer {
            if (it.isNotBlank()) {
                val fragment = supportFragmentManager.findFragmentByTag(TAG_ERROR_DIALOG) as? ErrorDialogFragment

                if (fragment == null) {
                    val dialog = ErrorDialogFragment.createDialog(it)
                    dialog.show(supportFragmentManager, TAG_ERROR_DIALOG)
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
        mViewModel.message.observe(this, messageObserver)
    }

    override fun onStop() {
        super.onStop()
        mViewModel.technician.removeObserver(technicianObserver)
        mViewModel.message.removeObserver(messageObserver)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.let {
            it.putString(STATE_USERNAME, mViewModel.username.value)
            it.putString(STATE_PASSWORD, mViewModel.password.value)
            it.putString(ErrorDialogFragment.EXTRA_MESSAGE, mViewModel.message.value)
        }
    }

    override fun onClickOk(dialogFragment: ErrorDialogFragment) {
        dialogFragment.dismiss()
        mViewModel.message.value = ""
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        if (fragment is ErrorDialogFragment) {
            fragment.errorDialogListener = this
        }
    }
}