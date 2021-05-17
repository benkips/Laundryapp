package com.mamafua.app.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mamafua.app.R
import com.mamafua.app.Repo.Resource
import com.mamafua.app.Utils.*
import com.mamafua.app.databinding.FragmentLoginBinding
import com.mamafua.app.models.Users
import com.mamafua.app.viewmodels.Prefviewmodel
import com.mamafua.app.viewmodels.loginviewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<loginviewmodel>()
    private  val pviewmodel by viewModels<Prefviewmodel>()
    private lateinit var email: String
    private lateinit var password: String
    private val repositoryObserver = Observer<Resource<Users>> {
        binding.pgbar.visible(it is Resource.Loading)
        when (it) {
            is Resource.Success -> {
                if (it.value.status.contains("success")) {
                    requireContext().showmessages("Success",it.value.status,{cachenow(it.value.personaldata[0].email,it.value.personaldata[0].id.toString())})

                } else {
                    // requireView().showmessages("Error",it.value[0].response)
                    //val s:String=it.value.status
                    requireContext().showmessages("Error",it.value.status)
                }
            }
            is Resource.Failure -> handleApiError(it) {
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)
        binding.pgbar.visible(false)
        binding.signinbtn.setOnClickListener {
            email = binding.etemail.text.trim().toString()
            password = binding.etpass.text.trim().toString()

            if (email.isEmpty()) {
                binding.etemail.setError("please enter a valid email")
            } else if (password.isEmpty()) {
                binding.etpass.setError("please  enter a valid password")
            } else {
                if (!isValidEmail(email)) {
                    binding.etemail.setError("please  enter a valid email")
                } else if (isValidPassword(password)) {
                    binding.etpass.setError("password must be less than 6 characters")
                } else {
                    vdatelogin(email ,password)
                }


            }
        }

        binding.signuptxt.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_login_to_registration)
        }


    }

    private fun vdatelogin(em: String, pass: String) {
        val loginstuff=viewmodel.loginusers(em, pass)
        loginstuff.observe(viewLifecycleOwner, repositoryObserver)
    }
    private  fun  cachenow( email:String,cid:String) {
        pviewmodel.saveToDataStore(email,cid)
        Navigation.findNavController(requireView()).navigate(R.id.action_login_to_Home)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
