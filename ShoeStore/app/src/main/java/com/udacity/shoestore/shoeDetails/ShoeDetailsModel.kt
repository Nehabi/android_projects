package com.udacity.shoestore.shoeDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.lang.Exception

class ShoeDetailsModel: ViewModel() {
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
    }

    fun onSubmitClick() {
        if(name.value == null) {
            showToast("Please enter a valid name")
            return
        }
        if(size.value == null || !isValidSize()) {
            showToast("Please enter a valid size")
            return
        }
        if(company.value == null) {
            showToast("Please enter a valid company")
            return
        }
        if(description.value == null) {
            showToast("Please enter a valid description")
            return
        }
        if(image.value == null) {
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

    fun setSuccess(value: Boolean) {
        _onSuccess.value = value
    }
}