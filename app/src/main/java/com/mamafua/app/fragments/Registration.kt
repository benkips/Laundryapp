package com.mamafua.app.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mamafua.app.R
import com.mamafua.app.Repo.Resource
import com.mamafua.app.Utils.*
import com.mamafua.app.databinding.FragmentRegistrationBinding
import com.mamafua.app.models.Locationdata
import com.mamafua.app.models.Returnstatus
import com.mamafua.app.models.Users
import com.mamafua.app.viewmodels.registrationviewmodel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Registration : Fragment(R.layout.fragment_registration) {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<registrationviewmodel>()

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var  name: String
    private lateinit var phone: String
    private lateinit var location: String

    private val repositoryObserver = Observer<Resource<Locationdata>>{
        when (it) {
            is Resource.Success -> {
                if (it.value.locationdata != null) {
                    val camOptions = arrayOfNulls<String>(it.value.locationdata.size)
                    for (i in it.value.locationdata.indices) {
                        camOptions[i] = it.value.locationdata[i].locations
                    }
                    val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        camOptions
                    )
                    binding.slocations.adapter = adapter

                }
            }
            is Resource.Failure -> handleApiError(it) {
            }
        }

    }

    private val repositoryObserverreg = Observer<Resource<Returnstatus>> {
        binding.pgbar.visible(it is Resource.Loading)
        when (it) {
            is Resource.Success -> {
                if (!it.value.err.isNullOrEmpty()) {
                    requireContext().showmessages("Error",it.value.err)
                } else {
                    requireContext().showmessages("Success",it.value.suc,{proceedtologin()})
                }
            }
            is Resource.Failure -> handleApiError(it) {
            }
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegistrationBinding.bind(view)

        binding.signintxt.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_registration_to_login)
        }

        loadlocations("nairobi")

         binding.slocations.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,view: View,position: Int,id: Long) {
                    val locoitem = parent.getItemAtPosition(position)
                    location=locoitem  as String
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }




        binding.signupbtn.setOnClickListener {
            email = binding.etemail.text.trim().toString()
            password = binding.etpass.text.trim().toString()
            name = binding.etusername.text.trim().toString()
            phone = binding.etphone.text.trim().toString()



            if (name.isEmpty()) {
                binding.etusername.setError("please  enter a valid username")
            } else if (phone.isEmpty()) {
                binding.etphone.setError("please  enter a valid phone number")
            } else if (email.isEmpty()) {
                binding.etemail.setError("please enter a valid email")
            } else if (password.isEmpty()) {
                binding.etpass.setError("please  enter a valid pass")
            } else   {
                if (!isValidEmail(email)) {
                    binding.etemail.setError("please  enter a valid email")
                } else if (isValidPassword(password)) {
                    binding.etpass.setError("password must be less than 6 characters")
                } else if(!isValidMobile(phone) || phone.length !=10) {
                    binding.etphone.setError("please  enter a valid phone number")
                } else{
                    registration(email,password,name,phone,location)
                }


            }
        }
    }


    private fun loadlocations(loco: String) {
        val locationstuff=viewmodel.getlocs(loco)
        locationstuff.observe(viewLifecycleOwner, repositoryObserver)
    }
    private fun registration(email: String,pass:String,name:String,phone:String,location:String) {
        val reg=viewmodel.registerationusers(email, pass, name, phone, location)
        reg.observe(viewLifecycleOwner, repositoryObserverreg)
    }
    private fun proceedtologin(){
        Navigation.findNavController(requireView()).navigate(R.id.action_registration_to_login)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}