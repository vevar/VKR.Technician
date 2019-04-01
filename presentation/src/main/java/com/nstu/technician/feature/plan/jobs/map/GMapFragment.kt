package com.nstu.technician.feature.plan.jobs.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.nstu.technician.databinding.FragmentMapBinding
import com.nstu.technician.feature.BaseFragment


class GMapFragment : BaseFragment(), OnMapReadyCallback {

    companion object {
        const val PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 1

    }

    private lateinit var mBinding: FragmentMapBinding
    private lateinit var mViewModel: MapViewModel
    private var mLocationPermissionGranted: Boolean = false


    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        setupViewModel()
        hideActionBar()
    }

    private fun setupViewModel() {
        val vmFactory = MapVMFactory()
        mViewModel = ViewModelProviders.of(this, vmFactory).get(MapViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, com.nstu.technician.R.layout.fragment_map, container, false)
        return mBinding.root
    }


    private fun hideActionBar() {
        val activity = activity
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.hide()
        }
    }

    override fun onMapReady(map: GoogleMap?) {

    }

    private fun getLocationPermission() {

    }
}