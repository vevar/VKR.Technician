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
import com.nstu.technician.di.component.DaggerGMapComponent
import com.nstu.technician.di.component.DaggerGMapScreen
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.model.facility.GPSPoint
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseFragment
import javax.inject.Inject


class GMapFragment : BaseFragment() {
    companion object {
        private const val TAG = "GMapFragment"
        const val PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 1
    }

    @Inject
    lateinit var mapVMFactory: MapVMFactory

    private lateinit var mBinding: FragmentMapBinding
    private lateinit var mViewModel: MapViewModel

    private var mainTargetMarker: Marker? = null
    private var mainFacility = Observer<Facility> { facility ->
        val gpsPoint = facility.address.location ?: throw NullPointerException("gpsPoint is null")
        val latLng = LatLng(gpsPoint.geox, gpsPoint.geoy)
        if (mainTargetMarker == null) {
            addMarkerToMap(latLng)
        } else {
            mainTargetMarker!!.position = latLng
            Log.d(TAG, "Position of mainTargetMarker is updated ")
        }
    }

    private fun addMarkerToMap(latLng: LatLng) {
        mBinding.mapView.getMapAsync { map ->
            mainTargetMarker = map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .visible(true)
            )
            if (mainTargetMarker == null) {
                Log.d(TAG, "mainTargetMarker isn't added to map")
            } else {
                Log.d(GMapFragment.TAG, "mainTargetMarker is added to map")
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }
        }
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setupInjection()
        setupViewModel()
    }

    private fun setupInjection() {
        val gMapComponent = DaggerGMapComponent.builder()
            .build()

        val gMapScreen = DaggerGMapScreen.builder()
            .appComponent(App.getApp(requireContext()).getAppComponent())
            .gMapComponent(gMapComponent)
            .build()

        gMapScreen.inject(this)
    }

    private fun setupViewModel() {
        val gMapFragmentArgs = GMapFragmentArgs.fromBundle(
            arguments ?: throw NullPointerException("Arguments are null")
        )
        gMapFragmentArgs.apply {
            if (idFacility != -1) {
                mapVMFactory.init(idFacility, object : MapViewModel.MapListener {
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
                mViewModel = ViewModelProviders.of(this@GMapFragment, mapVMFactory).get(MapViewModel::class.java)
            } else {
                throw IllegalArgumentException("idFacility must not equals -1")
            }
        }

    }

    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
        mViewModel.loadFacility()
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
        mViewModel.mainFacility.observe(this, mainFacility)
    }

    override fun onPause() {
        super.onPause()
        mBinding.mapView.onPause()
        mViewModel.mainFacility.removeObserver(mainFacility)
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