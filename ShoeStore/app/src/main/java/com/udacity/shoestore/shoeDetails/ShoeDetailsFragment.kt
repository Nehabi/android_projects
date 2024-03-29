package com.udacity.shoestore.shoeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailsBinding

class ShoeDetailsFragment: Fragment() {
    private lateinit var shoeDetailsModel : ShoeDetailsModel

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

        shoeDetailsModel = ViewModelProvider(this)[ShoeDetailsModel::class.java]

        binding.shoeDetailsModel = shoeDetailsModel

        shoeDetailsModel.toastMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
        }

        shoeDetailsModel.onSuccess.observe(viewLifecycleOwner) { value ->
            if(value){
                this.findNavController()
                    .navigate(ShoeDetailsFragmentDirections.actionShoeDetailsFragmentToShoeListsFragment(
                        shoeDetailsModel.name.value.toString(),
                        shoeDetailsModel.size.value.toString(),
                        shoeDetailsModel.company.value.toString(),
                        shoeDetailsModel.description.value.toString(),
                        shoeDetailsModel.image.value.toString()
                        ))
            }
        }

        shoeDetailsModel.onCancel.observe(viewLifecycleOwner) { value ->
            if(value) {
                this.findNavController()
                    .navigate(ShoeDetailsFragmentDirections.actionShoeDetailsFragmentToShoeListsFragment(null, null, null, null, null))
            }
        }

        return binding.root
    }
}