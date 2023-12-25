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
            Shoe("Nike Air", 6.5, "Nike", "Nike new shoes", listOf()),
            Shoe("Nike Air", 6.5, "Nike", "Nike new shoes", listOf())
        )
    }
}