package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import timber.log.Timber
import java.util.Locale

class RepresentativeFragment : Fragment() {

    companion object {
        const val REQUEST_LOCATION_PERMISSION_RESULT_CODE = 7
        val foregroundPermissionsArray = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private lateinit var representativeViewModel : RepresentativeViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: FragmentRepresentativeBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        representativeViewModel = ViewModelProvider(this).get(RepresentativeViewModel::class.java)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
            requireActivity()
        )
        binding = FragmentRepresentativeBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = representativeViewModel
        binding.listRepresentatives.adapter = RepresentativeListAdapter()

        //handling for spinner selection
        binding.state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?,
                                        selectedItemView: View?,
                                        position: Int, id: Long) {
                val state = parentView?.getItemAtPosition(position).toString()
                binding.selectedState.text = state
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                //do nothing
            }
        }

        //handling for use my location button
        binding.buttonLocation.setOnClickListener {
            if(checkLocationPermissions()) {
                getLocation()
            }
        }

        representativeViewModel.representatives.observe(viewLifecycleOwner) { representatives ->
            (binding.listRepresentatives.adapter as RepresentativeListAdapter).submitList(representatives)
        }

        representativeViewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            if(message.isNotBlank()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_LOCATION_PERMISSION_RESULT_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                Toast.makeText(
                    requireContext(), getString(R.string.location_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun checkLocationPermissions(): Boolean {
        return if (isPermissionGranted()) {
            true
        } else {
            requestPermissions(
                foregroundPermissionsArray,
                REQUEST_LOCATION_PERMISSION_RESULT_CODE
            )
            false
        }
    }

    private fun isPermissionGranted() : Boolean {
        return checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                try {
                    val address = geoCodeLocation(location)
                    binding.state.setSelection(findPositionOfState(address.state.toString()))
                    representativeViewModel.updateAddress(address)
                    representativeViewModel.onFindMyRepresentativeClick()
                } catch (e: Exception) {
                    Timber.e(e, "Error getting location")
                }
            }
            .addOnFailureListener { e ->
                Timber.e(e, "Error getting location")
            }
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
            ?.map { address ->
                Address(address.thoroughfare,
                        address.subThoroughfare,
                        address.locality,
                        address.adminArea,
                        address.postalCode)
            }
            ?.first() ?: Address("", "", "", "", "")
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun findPositionOfState(state: String): Int {
        val myArray = resources.getStringArray(R.array.states)
        var position = -1
        for (i in myArray.indices) {
            if (myArray[i] == state) {
                position = i
                break
            }
        }
        return position
    }
}