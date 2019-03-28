package com.nstu.technician.feature.common

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.nstu.technician.R

class ErrorDialog : DialogFragment() {

    companion object {
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"

        fun createDialog(message: String): ErrorDialog {
            val dialog = ErrorDialog()
            val bundle = Bundle()
            bundle.putString(EXTRA_MESSAGE, message)
            dialog.arguments = bundle
            dialog.noticeDialogListener = object : NoticeDialogListener {
                override fun onClick() {
                    dialog.dialog?.cancel()
                }

            }
            return dialog
        }
    }

    private lateinit var noticeDialogListener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onClick()
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.error)
            val view = requireActivity().layoutInflater.inflate(R.layout.dialog_error, null)
            val textView = view.findViewById<TextView>(R.id.message)
            textView.text = arguments?.getString(EXTRA_MESSAGE) ?: throw NullPointerException()
            builder.setView(view)
                .setPositiveButton(R.string.ok) { _, _ ->
                    noticeDialogListener.onClick()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot ve null")
    }
}