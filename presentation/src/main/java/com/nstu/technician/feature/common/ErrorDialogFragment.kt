package com.nstu.technician.feature.common

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.nstu.technician.R

class ErrorDialogFragment : DialogFragment() {

    companion object {
        private const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        const val TAG = "ErrorDialogFragment"

        fun createDialog(message: String): ErrorDialogFragment {
            val dialog = ErrorDialogFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_MESSAGE, message)
            dialog.arguments = bundle

            return dialog
        }
    }

    var errorDialogListener: ErrorDialogListener? = null

    interface ErrorDialogListener {
        fun onClickOk(dialogFragment: ErrorDialogFragment)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = requireActivity()
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_error, null)
        val textView = view.findViewById<TextView>(R.id.message)

        builder.setTitle(R.string.error)
        textView.text = arguments?.getString(EXTRA_MESSAGE) ?: throw NullPointerException()
        builder.setView(view)
            .setPositiveButton(R.string.ok) { _, _ ->
                errorDialogListener?.onClickOk(this)
                    ?: throw IllegalStateException("errorDialogListener must be set")
            }
        return builder.create()
    }
}