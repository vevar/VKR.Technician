package com.nstu.technician.feature.maintenance.job

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nstu.technician.R
import com.nstu.technician.databinding.ViewComponentsBinding
import com.nstu.technician.databinding.ViewImplementsBinding
import com.nstu.technician.databinding.ViewMaintenanceJobBinding
import com.nstu.technician.domain.JobDone
import com.nstu.technician.domain.JobInPlan
import com.nstu.technician.domain.JobInProcess
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.model.tool.ComponentUnit
import com.nstu.technician.domain.model.tool.Implements

class MaintenanceJobRVAdapter(
    private val mMaintenanceJob: MaintenanceJob,
    private val detailJobListener: DetailJobListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val DESCRIPTION_TYPE = 0
        private const val COMPONENT_TYPE = 1
        private const val IMPLEMENTS_TYPE = 2
        private const val BIAS = 1
    }

    private val components: List<ComponentUnit> = mMaintenanceJob.components
    private val implements: List<Implements> = mMaintenanceJob.implList


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> DESCRIPTION_TYPE
            1 -> COMPONENT_TYPE
            2 -> IMPLEMENTS_TYPE
            else -> throw IllegalStateException("More 3th position not implements")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            DESCRIPTION_TYPE -> {
                val view = inflater.inflate(R.layout.view_maintenance_job, parent, false)
                val marginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
                marginLayoutParams.topMargin = view.context.resources.getDimension(R.dimen.mini_margin).toInt()
                DescriptionHolder(view, detailJobListener)
            }
            COMPONENT_TYPE -> {
                val view = inflater.inflate(R.layout.view_components, parent, false)
                val marginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
                marginLayoutParams.topMargin = view.context.resources.getDimension(R.dimen.mini_margin).toInt()
                ComponentListHolder(view)
            }
            IMPLEMENTS_TYPE -> {
                val view = inflater.inflate(R.layout.view_implements, parent, false)
                val marginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
                marginLayoutParams.topMargin = view.context.resources.getDimension(R.dimen.mini_margin).toInt()
                ImplementsListHolder(view)
            }
            else -> throw IllegalStateException("Incorrect viewType")
        }
    }

    override fun getItemCount(): Int {
        val hasImpl = if (mMaintenanceJob.implList.isNotEmpty()) 1 else 0
        val hasComps = if (mMaintenanceJob.components.isNotEmpty()) 1 else 0
        return BIAS + hasImpl + hasComps
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            DESCRIPTION_TYPE -> (holder as DescriptionHolder).bind(mMaintenanceJob)
            COMPONENT_TYPE -> (holder as ComponentListHolder).bind(components)
            IMPLEMENTS_TYPE -> (holder as ImplementsListHolder).bind(implements)
        }
    }

    class DescriptionHolder(
        view: View,
        private val detailJobListener: DetailJobListener
    ) : RecyclerView.ViewHolder(view) {
        private val binding: ViewMaintenanceJobBinding =
            DataBindingUtil.bind(view) ?: throw IllegalStateException("Incorrect view")

        fun bind(mMaintenanceJob: MaintenanceJob) {
            binding.apply {
                maintenanceJob = mMaintenanceJob
                when (mMaintenanceJob.jobState) {
                    JobInPlan -> inPlanState()
                    JobInProcess -> inProgressState()
                    JobDone -> doneState()
                }

                btnEndJob.setOnClickListener {
                    this@DescriptionHolder.detailJobListener.onEndJob(mMaintenanceJob)
                }
                btnStartJob.setOnClickListener {
                    this@DescriptionHolder.detailJobListener.onStartJob(mMaintenanceJob)
                }
                btnSendAboutError.setOnClickListener {
                    this@DescriptionHolder.detailJobListener.onSendAboutError(mMaintenanceJob)
                }

                notifyChange()
            }
        }

        private fun doneState() {
            binding.btnEndJob.visibility = View.GONE
            binding.btnSendAboutError.visibility = View.GONE
            binding.btnStartJob.visibility = View.GONE
        }

        private fun inPlanState() {
            binding.btnEndJob.visibility = View.GONE
            binding.btnSendAboutError.visibility = View.GONE
            binding.btnStartJob.visibility = View.VISIBLE
        }

        private fun inProgressState() {
            binding.btnEndJob.visibility = View.VISIBLE
            binding.btnSendAboutError.visibility = View.VISIBLE
            binding.btnStartJob.visibility = View.GONE
        }
    }

    class ComponentListHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ViewComponentsBinding =
            DataBindingUtil.bind(view) ?: throw IllegalStateException("Incorrect view")

        fun bind(components: List<ComponentUnit>) {
            val linearLayout = binding.componentsContainer
            linearLayout.removeAllViews()
            for (index in 0 until components.size) {
                val textView = TextView(itemView.context)
                textView.text = components[index].component.name
                linearLayout.addView(textView)
            }
        }
    }

    class ImplementsListHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ViewImplementsBinding =
            DataBindingUtil.bind(view) ?: throw IllegalStateException("Incorrect view")

        fun bind(implements: List<Implements>) {
            val linearLayout = binding.implementsContainer
            linearLayout.removeAllViews()
            for (index in 0 until implements.size) {
                val textView = TextView(itemView.context)
                textView.text = implements[index].name
                linearLayout.addView(textView)
            }
        }
    }

    interface DetailJobListener {
        fun onSendAboutError(maintenanceJob: MaintenanceJob)
        fun onStartJob(maintenanceJob: MaintenanceJob)
        fun onEndJob(maintenanceJob: MaintenanceJob)
    }
}