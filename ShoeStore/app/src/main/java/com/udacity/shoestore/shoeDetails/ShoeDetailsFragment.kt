package com.udacity.shoestore.shoeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailsBinding
import com.udacity.shoestore.models.ShoeViewModel

class ShoeDetailsFragment: Fragment() {
    private val shoeViewModel : ShoeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentShoeDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_details,
            container,
            false
        )

        binding.shoeDetailsModel = shoeViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        shoeViewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            if(message.isNotBlank()) {
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
            }
        }

        shoeViewModel.onSuccess.observe(viewLifecycleOwner) { value ->
            if(value){
                this.findNavController()
                    .navigate(ShoeDetailsFragmentDirections.actionShoeDetailsFragmentToShoeListsFragment())
            }
        }

        shoeViewModel.onCancel.observe(viewLifecycleOwner) { value ->
            if(value) {
                this.findNavController()
                    .navigate(ShoeDetailsFragmentDirections.actionShoeDetailsFragmentToShoeListsFragment())
                shoeViewModel.resetValues()
            }
        }

        return binding.root
    }
}