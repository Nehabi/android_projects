package com.udacity.shoestore.shoeLists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.models.SlideModel
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListsBinding
import com.udacity.shoestore.databinding.ItemShoeBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.models.ShoeViewModel


class ShoeListsFragment : Fragment() {
    private val shoeViewModel: ShoeViewModel by activityViewModels()
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

        binding.shoeListsViewModel = shoeViewModel


        if (!shoeViewModel.name.value.isNullOrEmpty()) {
            addShoe(Shoe(
                shoeViewModel.name.value!!,
                shoeViewModel.size.value!!.toDouble(),
                shoeViewModel.company.value!!,
                shoeViewModel.description.value!!,
                shoeViewModel.image.value!!.split(',')))
            shoeViewModel.resetValues()
        }

        setShoeLists()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setShoeLists() {
        for (item in shoeViewModel.shoeList) {
            val shoeItemBiding = ItemShoeBinding.inflate(layoutInflater,
                                                    null,
                                             false)

            val shoe = Shoe(item.name,
                            item.size,
                            item.company,
                            item.description,
                            item.images)
            shoeItemBiding.shoe = shoe

            //setting image list as the library used doesn't support to set the image list from XML
            val imageList = ArrayList<SlideModel>()
            for(img in item.images)
            {
                imageList.add(SlideModel(findId(img)))
            }
            shoeItemBiding.shoeImage.setImageList(imageList)

            //setting layout parameters
            shoeItemBiding.root.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            //binding the item to the list
            binding.shoeList.addView(shoeItemBiding.root)
        }
    }


    private fun addShoe(s: Shoe) {
        shoeViewModel.addShoe(s)
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