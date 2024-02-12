package com.udacity.shoestore.shoeDetails

import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class ShoeDetailsModel: ViewModel() {
    private var _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _size = MutableLiveData<Float>()
    val size: LiveData<Float>
        get() = _size

    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

    private val _company = MutableLiveData<String>()
    val company: LiveData<String>
        get() = _company

    private val _image = MutableLiveData<String>()
    val image: LiveData<String>
        get() = _image

    private val _toastMessage = MutableLiveData<String>()
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
        if(_name.value.isNullOrEmpty()) {
            showToast("Please enter a valid name")
            return
        }
        if(_size.value == null) {
            showToast("Please enter a valid size")
            return
        }
        if(_company.value.isNullOrEmpty()) {
            showToast("Please enter a valid company")
            return
        }
        if(_description.value.isNullOrEmpty()) {
            showToast("Please enter a valid description")
            return
        }
        if(_image.value.isNullOrEmpty()) {
            showToast("Please enter a valid image path")
            return
        }
        _onSuccess.value = true
    }

    fun onCancel() {
        _onCancel.value = true
    }


    private fun showToast(message: String) {
        _toastMessage.value = message
    }

    fun updateName(s: Editable) {
        _name.value = s.toString()
    }

    fun updateCompany(s: Editable) {
        _company.value = s.toString()
    }

    fun updateDescription(s: Editable) {
        _description.value = s.toString()
    }

    fun updateImage(s: Editable) {
        _image.value = s.toString()
    }

    fun updateSize(s: Editable) {
        _size.value = s.toString().toFloat()
    }
}