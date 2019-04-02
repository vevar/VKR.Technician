package com.nstu.technician.feature.plan.jobs.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.nstu.technician.databinding.FragmentMapBinding
import com.nstu.technician.feature.BaseFragment


class GMapFragment : BaseFragment() {

    companion object {
        private const val TAG = "GMapFragment"
        const val PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 1
    }

    private lateinit var mBinding: FragmentMapBinding
    private lateinit var mViewModel: MapViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        setupViewModel()
        hideActionBar()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
        mBinding.mapView.getMapAsync { map ->
            Log.d(TAG, "Method onMapReady is called")
            if (checkLocationPermission()) {
                fusedLocationClient.lastLocation.addOnFailureListener {
                    Log.d(TAG, "onFailureListener(lastLocation) is called")
                    Log.d(TAG, it.message)

                }
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    Log.d(TAG, "onSuccessListener(lastLocation) is called")
                    val latLng = LatLng(location.latitude, location.longitude)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
                fusedLocationClient.lastLocation.addOnCanceledListener {
                    Log.d(TAG, "onCanceledListener(lastLocation) is called")
                }
                fusedLocationClient.lastLocation.addOnCompleteListener {
                    Log.d(TAG, "onCompleteListener(lastLocation) is called")
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    GMapFragment.PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION
                )
            }
        }
    }


    private fun setupViewModel() {
        val vmFactory = MapVMFactory()
        mViewModel = ViewModelProviders.of(this, vmFactory).get(MapViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, com.nstu.technician.R.layout.fragment_map, container, false)
        mBinding.mapView.onCreate(savedInstanceState)

        return mBinding.root
    }

    private fun hideActionBar() {
        val activity = activity
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.hide()
        }
    }

    private fun checkLocationPermission(): Boolean {
        return PermissionChecker.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mBinding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mBinding.mapView.onStop()
    }
}