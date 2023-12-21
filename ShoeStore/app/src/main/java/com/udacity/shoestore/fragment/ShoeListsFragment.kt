package com.udacity.shoestore.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListsBinding

class ShoeListsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentShoeListsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_lists, container, false
        )
        binding.nextButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.shoeDetailsFragment)
        }
        return binding.root
    }
}