package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding

class AsteroidAdapter: RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder>() {
    var asteroidList = listOf<Asteroid>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return asteroidList.size
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = asteroidList[position]
        holder.bind(item)
    }

    class AsteroidViewHolder private constructor(binding: ListItemAsteroidBinding): RecyclerView.ViewHolder(binding.root) {
        private val asteroidName: TextView = binding.asteroidName
        private val asteroidDate: TextView = binding.asteroidDate
        private val asteroidStatus: ImageView = binding.asteroidStatus

        fun bind(item: Asteroid) {
            asteroidName.text = item.codename
            asteroidDate.text = item.closeApproachDate
            asteroidStatus.setImageResource(
                when (item.isPotentiallyHazardous) {
                    true -> R.drawable.ic_status_potentially_hazardous
                    false -> R.drawable.ic_status_normal
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemAsteroidBinding.inflate(layoutInflater, null, false)
                return AsteroidViewHolder(binding)
            }
        }
    }
}