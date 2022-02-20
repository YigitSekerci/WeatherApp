package com.example.weatherapp.util

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment


fun Fragment.makeExceptionDialogue(text: String) {
    AlertDialog.Builder(requireContext())
        .setMessage(text)
        .setTitle("Alert")
        .setNegativeButton(
            "Close",
        ) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        .create()
        .show()
}