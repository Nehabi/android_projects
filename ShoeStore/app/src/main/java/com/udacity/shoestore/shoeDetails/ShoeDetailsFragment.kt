package com.udacity.shoestore.shoeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailsBinding

class ShoeDetailsFragment: Fragment() {
    private val shoeDetailsModel : ShoeDetailsModel by activityViewModels()

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

        binding.shoeDetailsModel = shoeDetailsModel
        binding.lifecycleOwner = viewLifecycleOwner

        shoeDetailsModel.toastMessage.observe(viewLifecycleOwner) { message ->
            if(message.isNotBlank()) {
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
            }
        }

        shoeDetailsModel.onSuccess.observe(viewLifecycleOwner) { value ->
            if(value){
                this.findNavController()
                    .navigate(ShoeDetailsFragmentDirections.actionShoeDetailsFragmentToShoeListsFragment(
                        binding.name.text.toString(),
                        binding.size.text.toString().toFloat(),
                        binding.company.text.toString(),
                        binding.description.text.toString(),
                        binding.image.text.toString()))
                shoeDetailsModel.resetValues()
            }
        }

        shoeDetailsModel.onCancel.observe(viewLifecycleOwner) { value ->
            if(value) {
                this.findNavController()
                    .navigate(ShoeDetailsFragmentDirections.actionShoeDetailsFragmentToShoeListsFragment(null, 0.0F, null, null, null))
                shoeDetailsModel.resetValues()
            }
        }

        return binding.root
    }
}