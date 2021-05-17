package com.mamafua.app.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import com.mamafua.app.Databasestuff.Washitems
import com.mamafua.app.R
import com.mamafua.app.Repo.Resource
import com.mamafua.app.Utils.handleApiError
import com.mamafua.app.Utils.showmessages
import com.mamafua.app.Utils.snackbarz
import com.mamafua.app.databinding.FragmentOrdersBinding
import com.mamafua.app.models.Endorder
import com.mamafua.app.models.Returnstatus
import com.mamafua.app.viewmodels.Cartviewmodel
import com.mamafua.app.viewmodels.Orderviewmodel
import com.sucho.placepicker.AddressData
import com.sucho.placepicker.Constants
import com.sucho.placepicker.MapType
import com.sucho.placepicker.PlacePicker
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Orders : Fragment(R.layout.fragment_orders) ,EasyPermissions.PermissionCallbacks{
    companion object {
        const val PERMISSION_LOCATION_REQUEST_CODE = 1
    }
    private  var _binding : FragmentOrdersBinding?=null
    private val binding get() = _binding!!
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    // globally declare LocationRequest
    private lateinit var locationRequest: LocationRequest
    // globally declare LocationCallback
    private lateinit var locationCallback: LocationCallback
    private  val oviewmodel by viewModels<Orderviewmodel>()
    private  val cartviewmodel by viewModels<Cartviewmodel>()
    var orderList: ArrayList<Washitems> = arrayListOf()


    private val repositoryObserverreg = Observer<Resource<Returnstatus>> {
        when (it) {
            is Resource.Success -> {
                if (!it.value.err.isNullOrEmpty()) {
                    requireContext().showmessages("Error",it.value.err)
                } else {
                    requireContext().showmessages("Success",it.value.suc)
                }
            }
            is Resource.Failure -> handleApiError(it) {
            }
        }
    }
    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentOrdersBinding.bind(view)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        getLocationUpdates()
        var resultLauncher =registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    try {
                        val addressData =
                            result.data?.getParcelableExtra<AddressData>(Constants.ADDRESS_INTENT)
                        binding.loco.text = addressData.toString()
                    } catch (e: Exception) {
                        Log.e("MainActivity", e.message.toString())
                    }
                }
            }

        cartviewmodel.getcartcontentz()

        cartviewmodel.cartLists.observe(viewLifecycleOwner) {
            for (order in it){
                val ord=Washitems(order.sid,order.service,order.price,order.images,order.quantity)
                orderList.add(ord)
            }

        }

        binding.pinloco.setOnClickListener {
            if (hasLocationPermission() ) {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    if (location == null) {
                        requireView().snackbarz("please turn on GPS","ok",{null})
                        getLocationUpdates()
                    } else {
                        val intent = PlacePicker.IntentBuilder()
                            .setLatLong(location.latitude, location.longitude)
                            .showLatLong(true)
                            .setAddressRequired(true)
                            .setMapType(MapType.NORMAL)
                            .build(requireActivity())
                        resultLauncher.launch(intent)
                        //startActivityForResult(intent, Constants.PLACE_PICKER_REQUEST)
                    }
                }
            } else {
                requestLocationPermission()
            }
        }

        binding.requstorder.setOnClickListener {

         sentorder("kip@gmail.com","halima", orderList,"shgdjhg","nearme")
        }

    }

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )



    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            "This application cannot work without Location Permission.",
            Mylocation.PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms.first())) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(
            requireContext(),
            "Permission Granted!",
            Toast.LENGTH_SHORT
        ).show()
    }


    private fun getLocationUpdates() {

        locationRequest = LocationRequest()
        locationRequest.interval = 50000
        locationRequest.fastestInterval = 50000
        locationRequest.smallestDisplacement = 170f // 170 m = 0.1 mile
        locationRequest.priority =
            LocationRequest.PRIORITY_HIGH_ACCURACY //set according to your app function
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                if (locationResult.locations.isNotEmpty()) {
                    // get latest location
                    val location =
                        locationResult.lastLocation
                    // use your location object
                    // get latitude , longitude and other info from this
                }


            }
        }
    }

     private fun sentorder(email: String,location:String,orders:ArrayList<Washitems>,cordinates:String,locationinstructions:String) {
         val order=Endorder(email,location,orders,locationinstructions,cordinates)
        val reg=oviewmodel.sendorderz(order)
        reg.observe(viewLifecycleOwner, repositoryObserverreg)
    }

    //start location updates
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    // stop location updates
    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    // stop receiving location update when activity not visible/foreground
    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    // start receiving location update when activity  visible/foreground
    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}