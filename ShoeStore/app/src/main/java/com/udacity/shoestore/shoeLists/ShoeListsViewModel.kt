package com.udacity.shoestore.shoeLists

import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class ShoeListsViewModel: ViewModel() {
    private lateinit var _shoeList : MutableList<Shoe>
    val shoeList : MutableList<Shoe>
        get() = _shoeList

    init {
        setShoeList()
    }

    private fun setShoeList() {
        _shoeList = mutableListOf(
            Shoe("Nike AIR", 6.5, "Nike", "Nike new shoes", listOf("shoe1", "shoe2")),

        )
    }

    fun addShoe(s: Shoe) {
       _shoeList.add(s)
    }
}