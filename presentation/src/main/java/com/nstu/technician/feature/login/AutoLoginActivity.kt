package com.nstu.technician.feature.login

import android.os.Bundle
import android.util.Log
import com.nstu.technician.di.component.login.DaggerAutoLoginComponent
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.user.AutoAuthUseCase
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseActivity
import com.nstu.technician.feature.ContainerActivity
import kotlinx.coroutines.*
import javax.inject.Inject

class AutoLoginActivity : BaseActivity() {

    companion object {
        private const val TAG = "AutoLoginActivity"
    }

    @Inject
    lateinit var autoAuthUseCase: AutoAuthUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            autoAuthUseCase.execute(object : CallUseCase<Technician> {
                override suspend fun onSuccess(result: Technician) {
                    ContainerActivity.startActivity(
                        this@AutoLoginActivity,
                        result.oid
                    )
                    this@AutoLoginActivity.finish()
                }

                override suspend fun onFailure(throwable: Throwable) {
                    Log.d(TAG, "AutoAuthUseCase  fail")
                    LoginActivity.startActivity(this@AutoLoginActivity)
                    this@AutoLoginActivity.finish()
                }
            }, Unit)
        }
    }

    private fun setupInjection() {
        val app = App.getApp(this)
        DaggerAutoLoginComponent.builder()
            .appComponent(app.getAppComponent())
            .authComponent(app.getDataClient().getAuthComponent())
            .build().inject(this)
    }
}