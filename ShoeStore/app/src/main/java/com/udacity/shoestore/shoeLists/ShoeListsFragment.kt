package com.udacity.shoestore.shoeLists

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListsBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.shoeDetails.ShoeDetailsModel


class ShoeListsFragment : Fragment() {
    private lateinit var shoeListsViewModel: ShoeListsViewModel
    private lateinit var binding: FragmentShoeListsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_lists, container,
            false
        )

        binding.add.setOnClickListener { view ->
            view.findNavController().navigate(ShoeListsFragmentDirections.actionShoeListsFragmentToShoeDetailsFragment())
        }

        shoeListsViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ShoeListsViewModel::class.java)

        binding.shoeListsViewModel = shoeListsViewModel

        if(arguments != null && requireArguments().containsKey("name")) {
            val args = ShoeListsFragmentArgs.fromBundle(requireArguments())
            if (!args.name.isNullOrEmpty()) {
                addShoe(
                    Shoe(
                        args.name.toString(),
                        6.5,
                        args.name.toString(),
                        args.name.toString(),
                        listOf()
                    ),
                )
            }
        }

        setShoeLists()

        return binding.root
    }

    private fun setShoeLists() {
        val myLayout: LinearLayout = binding.shoeList
        for (i in shoeListsViewModel.shoeList) {
            val myButton = View.inflate(context, R.layout.item_shoe, null)
            myButton.findViewById<ImageView>(R.id.shoeImage).setImageResource(R.drawable.shoe2)
            myButton.findViewById<TextView>(R.id.shoeName).text = i.name
            myButton.findViewById<TextView>(R.id.shoeSize).text = i.size.toString()
            myButton.findViewById<TextView>(R.id.shoeBrand).text = i.company
            myButton.findViewById<TextView>(R.id.shoeDescription).text = i.description
            myButton.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            myLayout.addView(myButton)
        }
    }


    private fun addShoe(s: Shoe) {
        shoeListsViewModel.addShoe(s)
    }

}