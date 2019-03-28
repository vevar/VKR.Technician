package com.nstu.technician.feature.listjobsforday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmnetListFaciliitiesBinding
import com.nstu.technician.feature.BaseFragment

class ListFacilitiesForDayFragment : BaseFragment() {

    private lateinit var mBinding: FragmnetListFaciliitiesBinding
    private lateinit var mViewModel: ListFacilitiesForDayViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragmnet_list_faciliities, container, false)
        mBinding.recycleViewFacilities.adapter =
            FacilitiesRecyclerViewAdapter(requireContext(), object : FacilitiesRecyclerViewAdapter.FacilityListener {
                override fun onShowOnMap() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onStartJob() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        return mBinding.root
    }

    private fun setupViewModel() {
        val factory = ListFacilitiesForDayViewModelFactory()
        mViewModel = ViewModelProviders.of(this, factory).get(ListFacilitiesForDayViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        mViewModel.loadFacilities()
    }
}