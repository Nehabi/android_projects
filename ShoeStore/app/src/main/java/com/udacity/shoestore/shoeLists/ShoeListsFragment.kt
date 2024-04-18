package com.udacity.shoestore.shoeLists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListsBinding
import com.udacity.shoestore.models.Shoe


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
                        args.size!!.toDouble(),
                        args.company.toString(),
                        args.decription.toString(),
                        args.images.toString().split(',')
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
            val imagesLists = i.images
            val imageList = ArrayList<SlideModel>()
            for(i in imagesLists)
            {
                imageList.add(SlideModel(findId(i)))
            }
            val imageSlider = myButton.findViewById<ImageSlider>(R.id.shoeImage)
            imageSlider.setImageList(imageList)
            myButton.findViewById<TextView>(R.id.shoeName).text = i.name
            myButton.findViewById<TextView>(R.id.shoeSize).text = i.size.toString()
            myButton.findViewById<TextView>(R.id.shoeBrand).text = i.company
            myButton.findViewById<TextView>(R.id.shoeDescription).text = i.description
            myButton.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setHasOptionsMenu(true)
            myLayout.addView(myButton)
        }
    }


    private fun addShoe(s: Shoe) {
        shoeListsViewModel.addShoe(s)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logoutFragment) {
            this.findNavController()
                .navigate(ShoeListsFragmentDirections.actionShoeListsFragmentToLoginFragment())
            return super.onOptionsItemSelected(item)
        }
        return false
    }

    private fun findId(path: String): Int {
        return when(path) {
            "shoe1" -> R.drawable.shoe1
            "shoe2" -> R.drawable.shoe2
            "shoe3" -> R.drawable.shoe3
            "shoe4" -> R.drawable.shoe4
            "shoe5" -> R.drawable.shoe5
            "shoe6" -> R.drawable.shoe6
            "shoe7" -> R.drawable.shoe7
            "shoe8" -> R.drawable.shoe8
            "shoe9" -> R.drawable.shoe9
            "shoe10" -> R.drawable.shoe10
            else -> {
                R.drawable.shoe1
            }
        }
    }
}