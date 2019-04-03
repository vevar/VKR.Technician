package com.nstu.technician.feature.plan.jobs.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.nstu.technician.databinding.FragmentMapBinding
import com.nstu.technician.domain.model.facility.GPSPoint
import com.nstu.technician.feature.BaseFragment


class GMapFragment : BaseFragment() {

    companion object {
        private const val TAG = "GMapFragment"
        const val PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 1
    }

    private lateinit var mBinding: FragmentMapBinding
    private lateinit var mViewModel: MapViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var mainTargetMarker: Marker? = null
    private val mainTargetObserver = Observer<GPSPoint> { gpsPoint ->
        val latLng = LatLng(gpsPoint.geox, gpsPoint.geoy)
        if (mainTargetMarker == null) {
            mBinding.mapView.getMapAsync { map ->
                mainTargetMarker = map.addMarker(
                    MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker())
                )
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                mViewModel.mainTargetGPSPoint.value = gpsPoint
                Log.d(TAG, "mainTargetMarker is added to map")
            }
        }
        mainTargetMarker?.position = latLng
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setupViewModel()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    private fun setupViewModel() {
        val vmFactory = MapVMFactory(object : MapViewModel.MapListener {
            override fun onUpdateDevicePosition(marker: MarkerOptions) {
                Log.d(TAG, "onUpdateDevicePosition is called")
            }

            override fun onGoToDevicePosition(gpsPoint: GPSPoint) {
                Log.d(TAG, "onGoToDevicePosition is called")
            }

            override fun onGoToMainTarget(gpsPoint: GPSPoint) {
                Log.d(TAG, "onGoToMainTarget is called")
                mBinding.mapView.getMapAsync { map ->
                    val latLng = LatLng(gpsPoint.geoy, gpsPoint.geox)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
            }
        })
        mViewModel = ViewModelProviders.of(this, vmFactory).get(MapViewModel::class.java)
        val gMapFragmentArgs = GMapFragmentArgs.fromBundle(
            arguments ?: throw NullPointerException("Arguments are null")
        )
        gMapFragmentArgs.apply {
            mViewModel.init(latitude.toDouble(), longitude.toDouble())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, com.nstu.technician.R.layout.fragment_map, container, false)
        mBinding.viewModel = mViewModel
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.lifecycleOwner = this
        hideActionBar()

        return mBinding.root
    }

    private fun hideActionBar() {
        val activity = activity
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.hide()
        }
    }

    private fun showActionBar() {
        val activity = activity
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.show()
        }
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
        mViewModel.mainTargetGPSPoint.observe(this, mainTargetObserver)
    }

    override fun onPause() {
        super.onPause()
        mBinding.mapView.onPause()
        mViewModel.mainTargetGPSPoint.removeObserver(mainTargetObserver)
    }

    override fun onStop() {
        super.onStop()
        mBinding.mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showActionBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.mapView.onDestroy()
    }
}