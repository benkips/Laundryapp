package com.mamafua.app.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mamafua.app.R
import com.mamafua.app.databinding.FragmentLauncherscreenBinding
import com.mamafua.app.index
import com.mamafua.app.viewmodels.Prefviewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Launcherscreen : Fragment(R.layout.fragment_launcherscreen) {
    private var _binding: FragmentLauncherscreenBinding? = null
    private val binding get() = _binding!!
    private  val pviewmodel by viewModels<Prefviewmodel>()
    private val handler= Handler()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       _binding = FragmentLauncherscreenBinding.bind(view)

    }
    private val runnable= Runnable {

        pviewmodel.readFromDataStore.observe(viewLifecycleOwner, Observer {
            if(it.em!="none"){
                Navigation.findNavController(requireView()).navigate(R.id.action_launcherscreen_to_Home)
            }else{
                Navigation.findNavController(requireView()).navigate(R.id.action_launcherscreen_to_login)
            }
        })

    }
    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,1000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}