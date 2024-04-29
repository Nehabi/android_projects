package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidItemViewHolder
import com.udacity.asteroidradar.R

class AsteroidAdapter: RecyclerView.Adapter<AsteroidItemViewHolder>() {
    var asteroidList = listOf<Asteroid>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.asteroid_item_view, parent, false) as TextView
        //val asteroidView = AsteroidItemViewBinding.inflate(layoutInflater, null, false)
        //return AsteroidItemViewHolder(asteroidView.root)
        return AsteroidItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return asteroidList.size
    }

    override fun onBindViewHolder(holder: AsteroidItemViewHolder, position: Int) {
        val item = asteroidList[position]
        holder.textView.text = item.codename
    }

}