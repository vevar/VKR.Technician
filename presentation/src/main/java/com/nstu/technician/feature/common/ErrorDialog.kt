package com.nstu.technician.feature.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.nstu.technician.R

class ErrorDialog : DialogFragment() {

    companion object {
        const val STATE_MESSAGE = "STATE_MESSAGE"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.error)
            val view = requireActivity().layoutInflater.inflate(R.layout.dialog_error, null)
            val textView = view.findViewById<TextView>(R.id.message)
            textView.text = savedInstanceState?.getString(STATE_MESSAGE) ?: throw NullPointerException()
            builder.setView(view)
                .setPositiveButton(R.string.ok, { dialog, id ->

                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot ve null")
    }
}