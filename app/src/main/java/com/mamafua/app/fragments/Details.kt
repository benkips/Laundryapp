package com.mamafua.app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mamafua.app.R
import com.mamafua.app.databinding.FragmentDetailsBinding
import com.mamafua.app.databinding.FragmentServicesBinding


class Details : Fragment(R.layout.fragment_details) {
    private  var _binding : FragmentDetailsBinding?=null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentDetailsBinding.bind(view)

        val name: String? =arguments?.getString("name")
        val description=arguments?.getString("description")
        val photo=arguments?.getString("photo")
        val price=arguments?.getString("price")


        binding.stitle.text=name
        binding.tvdetails.text=description

        Glide.with(requireContext())
            .load("http://mamafua.howtoinkenya.co.ke/static/uploads/" + photo)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(binding.ivcategory)

        binding.pricebtn.text=price+"KES"

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}