package com.udacity.asteroidradar.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = AsteroidAdapter(AsteroidAdapter.AsteroidListener { asteroid ->
            viewModel.onAsteroidClicked(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter
        viewModel.asteroidList.observe(viewLifecycleOwner, Observer{
            it?.let {
                adapter.submitList(it)
            }
        })
        viewModel.navigateToAsteroid.observe(viewLifecycleOwner) { asteroid ->
            asteroid?.let {
                this.findNavController()
                    .navigate(MainFragmentDirections.actionShowDetail(asteroid))
                viewModel.onAsteroidNavigated()
            }
        }
        viewModel.imageOfDay.observe(viewLifecycleOwner) { pictureOfDay ->
            pictureOfDay?.let {
                binding.pictureOfDay = pictureOfDay
                binding.executePendingBindings()
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.hindi -> AppCompatDelegate.setApplicationLocales (
                LocaleListCompat.forLanguageTags(Constants.HINDI_LANGUAGE)
            )
            R.id.english -> AppCompatDelegate.setApplicationLocales (
                LocaleListCompat.forLanguageTags(Constants.ENGLISH_LANGUAGE)
            )
            R.id.spanish -> AppCompatDelegate.setApplicationLocales (
                LocaleListCompat.forLanguageTags(Constants.SPANISH_LANGUAGE)
            )
            R.id.show_today_asteroids -> {
                viewModel.setAsteroidDateFilter(AsteroidDateFilter.TODAY)
            }
            R.id.show_saved_asteroids -> {
                viewModel.setAsteroidDateFilter(AsteroidDateFilter.SAVED)
            }
            R.id.show_week_asteroids -> {
                viewModel.setAsteroidDateFilter(AsteroidDateFilter.WEEK)
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        activity?.registerReceiver(
            networkStateReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        activity?.unregisterReceiver(networkStateReceiver)
        super.onPause()
    }

    private val networkStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo
            viewModel.updateOnlineStatus(networkInfo?.isConnected == true)
        }
    }
}
