package com.mamafua.app.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamafua.app.Databasestuff.Washitems
import com.mamafua.app.R
import com.mamafua.app.adapter.Cartadapter
import com.mamafua.app.databinding.FragmentCartfragmentBinding
import com.mamafua.app.viewmodels.Cartviewmodel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Cartfragment : Fragment(R.layout.fragment_cartfragment), Cartadapter.OnItemClickListner {
    private  val cartviewmodel by viewModels<Cartviewmodel>()
    private  var _binding : FragmentCartfragmentBinding?=null
    private val binding get() = _binding!!
    val cartAdapter= Cartadapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentCartfragmentBinding.bind(view)
        cartviewmodel.getcartcontentz()

        cartviewmodel.cartLists.observe(viewLifecycleOwner) {
            var total = 0.0
            binding.rvcart.also { rv ->
                rv.layoutManager = LinearLayoutManager(requireContext())
                rv.setHasFixedSize(true)
                rv.adapter = cartAdapter
                cartAdapter.submitList(it)
            }
            for (cartItem in it) {
                total += cartItem.price * cartItem.quantity
            }
            binding.orderTotalTextView.text=total.toString()+"KES"
        }
            binding.placeOrderButton.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_cartfragment_to_orders)
            }
    }

    override fun onItemclick(wshitem: Washitems) {
        cartviewmodel.deletecartstuff(wshitem.sid)
    }

    override fun onchangeQuantity(quanty: Int, id: Int) {
        cartviewmodel.updatecartstuff(quanty,id)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}