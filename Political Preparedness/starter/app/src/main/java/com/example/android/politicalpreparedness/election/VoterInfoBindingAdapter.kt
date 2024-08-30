package com.example.android.politicalpreparedness.election

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.network.models.State

@BindingAdapter("correspondenceAddress")
fun bindCorrespondenceAddress(textView: TextView, states: List<State>?) {
    val address = states?.get(0)?.electionAdministrationBody?.correspondenceAddress
    if (address != null) {
        textView.text = address.toFormattedString()
    } else {
        textView.text = textView.context.getString(R.string.address_error)
    }
}

@BindingAdapter("buttonText")
fun bindButtonText(textView: TextView, isFollowed: Boolean) {
    if (isFollowed) {
        textView.text = textView.context.getString(R.string.unfollow_election)
    } else {
        textView.text = textView.context.getString(R.string.follow_election)
    }
}