package com.udacity.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.main.AsteroidApiStatus

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.sad_emoji)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = imageView.context.getString(R.string.happy_emoji)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.hazardous_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = imageView.context.getString(R.string.non_hazardous_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("pictureOfDay")
fun bindImage(imgView: ImageView, pictureOfDay: PictureOfDay?) {
    if (pictureOfDay != null) {
        Glide.with(imgView.context)
            .load(pictureOfDay.url)
            .placeholder(R.drawable.placeholder_picture_of_day)
            .error(R.drawable.placeholder_picture_of_day)
            .into(imgView)
        imgView.contentDescription = pictureOfDay.title
    } else {
        imgView.contentDescription = imgView.context.getString(R.string.hazardous_image)
    }
}

@BindingAdapter("asteroidApiStatus")
fun bindAsteroidApiStatus(statusImageView: ProgressBar, status: AsteroidApiStatus) {
    when (status) {
        AsteroidApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
        }
        AsteroidApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
        }
        AsteroidApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}