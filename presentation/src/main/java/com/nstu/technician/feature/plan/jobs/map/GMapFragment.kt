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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.nstu.technician.databinding.FragmentMapBinding
import com.nstu.technician.di.component.map.DaggerGMapComponent
import com.nstu.technician.di.component.map.DaggerGMapScreen
import com.nstu.technician.di.module.model.MapModule
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.feature.App
import com.nstu.technician.feature.BaseFragment
import com.nstu.technician.feature.common.DEFAULT_VALUE_ARG
import com.nstu.technician.feature.util.BaseViewModelFactory
import javax.inject.Inject


class GMapFragment : BaseFragment() {
    companion object {
        private const val TAG = "GMapFragment"
    }

    @Inject
    lateinit var mapVMFactory: BaseViewModelFactory<MapViewModel>

    private lateinit var mBinding: FragmentMapBinding
    private lateinit var mViewModel: MapViewModel

    private lateinit var mMap: GoogleMap
    private var mainTargetMarker: Marker? = null
    private var mainFacility = Observer<Facility> { facility ->
        val gpsPoint = facility.address.location ?: throw NullPointerException("gpsPoint is null")
        val latLng = LatLng(gpsPoint.geoy, gpsPoint.geox)
        if (mainTargetMarker == null) {
            addMarkerToMap(latLng)
        } else {
            mainTargetMarker!!.position = latLng
            Log.d(TAG, "Position of mainTargetMarker is updated ")
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }

    private fun addMarkerToMap(latLng: LatLng) {
        mainTargetMarker = mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("TARGET")
                .visible(true)
        )

        if (mainTargetMarker == null) {
            Log.d(TAG, "mainTargetMarker isn't added to map")
        } else {
            Log.d(
                GMapFragment.TAG,
                "mainTargetMarker is added to map(" +
                        "${mainTargetMarker!!.position.latitude}, ${mainTargetMarker!!.position.longitude})"
            )
        }
    }


    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        val gMapFragmentArgs = GMapFragmentArgs.fromBundle(
            arguments ?: throw NullPointerException("Arguments are null")
        )
        setupInjection(gMapFragmentArgs)
        setupViewModel()
    }

    private fun setupInjection(gMapFragmentArgs: GMapFragmentArgs) {
        val gMapComponent = DaggerGMapComponent.builder()
            .build()
        gMapFragmentArgs.apply {
            if (idFacility != DEFAULT_VALUE_ARG) {

                val gMapScreen = DaggerGMapScreen.builder()
                    .appComponent(App.getApp(requireContext()).getAppComponent())
                    .gMapComponent(gMapComponent)
                    .mapModule(MapModule(idFacility, object : MapViewModel.MapListener {
                        override fun onUpdateDevicePosition(marker: MarkerOptions) {
                            Log.d(TAG, "onUpdateDevicePosition is called")
                        }

                        override fun onGoToDevicePosition(gpsPoint: GPSPoint) {
                            Log.d(TAG, "onGoToDevicePosition is called")
                        }

                        override fun onGoToMainTarget(gpsPoint: GPSPoint) {
                            Log.d(TAG, "onGoToMainTarget is called")
                            val latLng = LatLng(gpsPoint.geoy, gpsPoint.geox)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                        }
                    })).build()
                gMapScreen.inject(this@GMapFragment)
            } else{
                throw IllegalArgumentException("idFacility must not equals default")
            }
        }
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this@GMapFragment, mapVMFactory).get(MapViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
        mViewModel.loadFacility()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, com.nstu.technician.R.layout.fragment_map, container, false)
        mBinding.apply {
            viewModel = mViewModel
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync { map ->
                mMap = map
            }
            lifecycleOwner = this@GMapFragment

        }

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