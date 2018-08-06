package com.hanihashemi.sleepwellbaby.ui.feedback

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatButton
import com.hanihashemi.sleepwellbaby.R
import com.hanihashemi.sleepwellbaby.helper.IntentHelper


class FeedbackDialog : DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = activity!!.layoutInflater
        val builder = AlertDialog.Builder(context!!)
        val view = inflater.inflate(R.layout.feedback_dialog, null)

        view.findViewById<AppCompatButton>(R.id.close).setOnClickListener { this.dismiss() }
        view.findViewById<AppCompatButton>(R.id.sendEmail).setOnClickListener { sendEmail() }
        view.findViewById<AppCompatButton>(R.id.sendReview).setOnClickListener { sendReview() }

        isCancelable = true
        builder.setMessage("")
        return builder
                .setView(view)
                .create()
    }

    fun sendEmail() {
        IntentHelper().sendMail(
                activity!!,
                "sleepwellbaby@fire.fundersclub.com",
                context!!.getString(R.string.feedback_email_subject)
                , "")
    }

    fun sendReview() {
        IntentHelper().openLink(activity!!, "https://myket.ir/app/com.hanihashemi.sleepwellbaby")
    }
}