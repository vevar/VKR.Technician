package com.nstu.technician.feature.maintenance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.nstu.technician.BR
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentMaintenanceBinding
import com.nstu.technician.di.component.maintenance.DaggerMaintenanceScreen
import com.nstu.technician.di.module.model.MaintenanceModule
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseActivity
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.util.BaseViewModelFactory
import javax.inject.Inject

class MaintenanceFragment : BaseFragment() {

    companion object {
        private const val TAG = "MaintenanceFragment"
    }

    @Inject
    lateinit var mFactory: BaseViewModelFactory<MaintenanceViewModel>

    private lateinit var mViewModel: MaintenanceViewModel
    private lateinit var mBinding: FragmentMaintenanceBinding

    private val maintenanceObserver = Observer<Maintenance> { maintenance ->
        mBinding.notifyPropertyChanged(BR.maintenance)
        mBinding.notifyPropertyChanged(BR.contract)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        setupViewModel()
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, mFactory).get(MaintenanceViewModel::class.java)
    }

    private fun getMaintenanceId(): Long {
        val args = MaintenanceFragmentArgs
            .fromBundle(arguments ?: throw IllegalStateException("args is null"))
        return args.maintenanceId
    }

    private fun setupInjection() {
        val dataClient = App.getApp(requireContext()).getDataClient()

        val screen = DaggerMaintenanceScreen.builder()
            .appComponent(App.getApp(requireContext()).getAppComponent())
            .maintenanceComponent(dataClient.createMaintenanceComponent())
            .maintenanceModule(MaintenanceModule(getMaintenanceId()))
            .build()

        screen.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_maintenance, container, false)
        mBinding.apply {
            viewModel = mViewModel
            lifecycleOwner = viewLifecycleOwner

            btnScanQr.setOnClickListener {
                mViewModel.maintenance.value?.let {
                    val action = MaintenanceFragmentDirections.actionMaintenanceDestToQrcodeScannerDest(it)
                    findNavController().navigate(action)
                }
            }
        }
        val activity = activity as BaseActivity
        activity.supportActionBar?.apply {
            title =
                "${resources.getString(R.string.lbl_maintenance)} #${mViewModel.maintenanceId}"
        }

        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mViewModel.maintenance.observe(viewLifecycleOwner, maintenanceObserver)
        mViewModel.loadDetailMaintenance()
    }
}