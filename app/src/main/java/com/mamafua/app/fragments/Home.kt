package com.mamafua.app.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamafua.app.R
import com.mamafua.app.Repo.Resource
import com.mamafua.app.Utils.handleApiError
import com.mamafua.app.Utils.visible
import com.mamafua.app.adapter.Categoryadapter
import com.mamafua.app.databinding.FragmentHomeBinding
import com.mamafua.app.databinding.FragmentLoginBinding
import com.mamafua.app.models.Category
import com.mamafua.app.models.Categorydata
import com.mamafua.app.viewmodels.Categoryviewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment(R.layout.fragment_home),Categoryadapter.OnItemClickListner {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private  val cviewmodel by viewModels<Categoryviewmodel>()
    val categoryAdapter=Categoryadapter(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding =FragmentHomeBinding.bind(view)

        cviewmodel.retrivecategory.observe(viewLifecycleOwner, Observer {
            binding.pgbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Log.d("myvalues", it.value.toString())
                    binding.rvcategory.also { rv ->
                        //val manager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                        rv.layoutManager =LinearLayoutManager(requireContext())
                        rv.setHasFixedSize(true)
                        rv.adapter = categoryAdapter
                        categoryAdapter.submitList(it.value.category)
                    }
                }
                is Resource.Failure -> handleApiError(it) {
                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemclick(category: Category) {
        Log.d("myids", category.id.toString())
        val x= bundleOf(
            "id" to category.id,
            "name" to category.name,
            "description" to category.description,
            "photo" to category.images
        )
        findNavController().navigate(
            R.id.action_Home_to_services,
            x
        )
    }
}