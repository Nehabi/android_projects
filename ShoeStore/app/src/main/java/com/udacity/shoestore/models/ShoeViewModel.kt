package com.udacity.shoestore.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import timber.log.Timber
import java.lang.Exception

class ShoeViewModel: ViewModel() {
    private lateinit var _shoeList : MutableList<Shoe>
    val shoeList : MutableList<Shoe>
        get() = _shoeList

    var _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    var _size = MutableLiveData<String>()
    val size: LiveData<String>
        get() = _size

    var _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

    var _company = MutableLiveData<String>()
    val company: LiveData<String>
        get() = _company

    var _image = MutableLiveData<String>()
    val image: LiveData<String>
        get() = _image

    var _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    private val _onSuccess = MutableLiveData<Boolean>()
    val onSuccess: LiveData<Boolean>
        get() = _onSuccess

    private val _onCancel = MutableLiveData<Boolean>()
    val onCancel: LiveData<Boolean>
        get() = _onCancel

    init {
        _onSuccess.value = false
        _onCancel.value = false
        setShoeList()
    }

    fun onSubmitClick() {
        if(name.value.isNullOrEmpty()) {
            showToast("Please enter a valid name")
            return
        }
        if(size.value.isNullOrEmpty() || !isValidSize()) {
            showToast("Please enter a valid size")
            return
        }
        if(company.value.isNullOrEmpty()) {
            showToast("Please enter a valid company")
            return
        }
        if(description.value.isNullOrEmpty()) {
            showToast("Please enter a valid description")
            return
        }
        if(image.value.isNullOrEmpty()) {
            showToast("Please enter a valid image path")
            return
        }
        _onSuccess.value = true
    }

    private fun isValidSize(): Boolean {
        try {
            size.value.toString().toFloat()
        } catch (exception : Exception) {
            Timber.e(exception)
            return false
        }
        return true
    }

    fun onCancel() {
        _onCancel.value = true
    }

    private fun showToast(message: String) {
        _toastMessage.value = message
    }

    fun resetValues() {
        _name.value = ""
        _size.value = ""
        _description.value = ""
        _company.value = ""
        _image.value = ""
        _toastMessage.value = ""
        _onCancel.value = false
        _onSuccess.value = false
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