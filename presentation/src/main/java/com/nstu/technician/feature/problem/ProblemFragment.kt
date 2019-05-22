package com.nstu.technician.feature.problem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.nstu.technician.R
import com.nstu.technician.databinding.FragmentProblemBinding
import com.nstu.technician.di.component.problem.DaggerProblemScreen
import com.nstu.technician.di.module.model.MaintenanceJobModule
import com.nstu.technician.feature.App
import com.nstu.technician.feature.util.BaseViewModelFactory
import javax.inject.Inject


class ProblemFragment : Fragment() {

    @Inject
    lateinit var mFactory: BaseViewModelFactory<ProblemViewModel>

    private lateinit var mViewModel: ProblemViewModel
    private lateinit var mBinding: FragmentProblemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        setupViewModel()
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, mFactory).get(ProblemViewModel::class.java)
    }

    private fun setupInjection() {
        val app = App.getApp(requireContext())

        DaggerProblemScreen.builder()
            .appComponent(app.getAppComponent())
            .problemComponent(app.getDataClient().createProblemComponent())
            .maintenanceJobModule(MaintenanceJobModule(getMaintenanceJobId()))
            .build().inject(this)
    }

    private fun getMaintenanceJobId() =
        ProblemFragmentArgs.fromBundle(arguments ?: throw IllegalStateException("args is null"))
            .maintenanceId

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_problem, container, false)
        mBinding.apply {
            viewModel = mViewModel
            lifecycleOwner = viewLifecycleOwner
            val context = requireContext()
            val typesProblem = context.resources.getStringArray(mViewModel.typesProblems)
            for (index in 0 until typesProblem.size) {
                groupTypeProblem.addView(
                    RadioButton(context).apply {
                        text = typesProblem[index]
                        layoutDirection = RadioButton.LAYOUT_DIRECTION_RTL
                        layoutParams = ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply {
                            topMargin = context.resources.getDimension(R.dimen.mini_margin).toInt()
                        }
                        setOnClickListener {
                            mViewModel.mChooseTypeProblem = index
                        }
                    }
                )
            }
            btnSend.setOnClickListener {
                mViewModel.send()
                findNavController().navigateUp()
            }
        }
        return mBinding.root
    }


}
