package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch
import timber.log.Timber

class RepresentativeViewModel: ViewModel() {
    var _line1 = MutableLiveData<String>()
    val line1: LiveData<String>
        get() = _line1

    var _line2 = MutableLiveData<String>()
    val line2: LiveData<String>
        get() = _line2

    var _city = MutableLiveData<String>()
    val city: LiveData<String>
        get() = _city

    var _zip = MutableLiveData<String>()
    val zip: LiveData<String>
        get() = _zip

    var _state = MutableLiveData<String>()
    val state: LiveData<String>
        get() = _state

    private var _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    val representatives = MutableLiveData<List<Representative>>()

    fun fetchRepresentatives(address: String) {
        viewModelScope.launch {
            try {
                val representativesList = CivicsApi.retrofitService.getRepresentatives(address)
                representatives.value = representativesList.offices.flatMap { office ->
                    office.getRepresentatives(representativesList.officials)
                }
            } catch (e: Exception) {
                Timber.e(e)
                showToast("Failed to fetch representatives. Please verify address and try again.")
            }
        }
    }

    fun onFindMyRepresentativeClick() {
        if(line1.value.isNullOrBlank()) {
            showToast("Please enter a valid Address Line 1")
            return
        }
        if(line2.value.isNullOrBlank()) {
            showToast("Please enter a valid Address Line 2")
            return
        }
        if(city.value.isNullOrBlank()){
            showToast("Please enter a valid City")
            return
        }
        if(zip.value.isNullOrBlank() || zip.value!!.length != 5) {
            showToast("Please enter a valid Zip")
            return
        }
        val address = Address(line1.value!!, line2.value!!, city.value!!, state.value!!, zip.value!!)
        fetchRepresentatives(address.toFormattedString())
    }

    fun showToast(message: String) {
        _toastMessage.value = message
    }

    fun updateAddress(address: Address) {
        _line1.value = address.line1
        _line2.value = address.line2
        _zip.value = address.zip
        _city.value = address.city
    }
}
