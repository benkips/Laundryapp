package com.mamafua.app.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.mamafua.app.Databasestuff.Washitems
import com.mamafua.app.R
import com.mamafua.app.Utils.snackbarz
import com.mamafua.app.adapter.ServiceLoadStateAdapter
import com.mamafua.app.adapter.Serviceadapter
import com.mamafua.app.databinding.FragmentServicesBinding
import com.mamafua.app.models.Subservicecategories
import com.mamafua.app.viewmodels.Cartviewmodel
import com.mamafua.app.viewmodels.Categoryservicesviewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Services : Fragment(R.layout.fragment_services),Serviceadapter.OnItemClickListner{
    private  val viewmodel by viewModels<Categoryservicesviewmodel>()
    private  val cartviewmodel by viewModels<Cartviewmodel>()
    private  var _binding :FragmentServicesBinding?=null
    private val binding get() = _binding!!
    private val mutableCart = MutableLiveData<List<Washitems>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding=FragmentServicesBinding.bind(view)

        cartviewmodel.getcartcontentz()

        val adapter= Serviceadapter(this)


        val id: String? =arguments?.getString("id")
        val name: String? =arguments?.getString("name")
        val description=arguments?.getString("description")
        val photo=arguments?.getString("photo")
         Log.d("myidservice", name!!)
        srchnow("",id!!)

        binding.srchbtn.setOnClickListener {
            val qry:String=binding.srchquery.text.toString().trim()
            if(!qry.isEmpty()) {
                srchnow(qry, id)
            }
        }
        binding.srchquery.doAfterTextChanged {
            srchnow(it.toString(), id)
            }
        binding.apply {
            rvservices.setHasFixedSize(true)
            rvservices.adapter=adapter.withLoadStateHeaderAndFooter(
                header = ServiceLoadStateAdapter{adapter.retry()},
                footer = ServiceLoadStateAdapter{adapter.retry()}
            )
            btnretry.setOnClickListener {
                adapter.retry();
            }
        }
        viewmodel.result.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
        cartviewmodel.cartLists.observe(viewLifecycleOwner){
            mutableCart?.value=it
        }
        adapter.addLoadStateListener {loadstate->
            binding.apply {
                pgbar.isVisible=loadstate.source.refresh is LoadState.Loading
                rvservices.isVisible=loadstate.source.refresh is LoadState.NotLoading
                btnretry.isVisible=loadstate.source.refresh is LoadState.Error
                tvError.isVisible=loadstate.source.refresh is LoadState.Error
                //empty view
                if(loadstate.source.refresh is LoadState.NotLoading &&
                    loadstate.append.endOfPaginationReached &&
                    adapter.itemCount<1){
                    rvservices.isVisible=false
                    tvViewEmpty.isVisible=true
                }else{
                    tvViewEmpty.isVisible =false
                }
            }
        }
    }
    private  fun srchnow(qury:String,cid:String){
        viewmodel.getsearches(qury,cid)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onItemClick(subcategory: Subservicecategories) {
            val x= bundleOf(
            "name" to subcategory.name,
            "description" to subcategory.description,
            "photo" to subcategory.images,
            "price" to subcategory.price
        )
        findNavController().navigate(
            R.id.action_services_to_details,
            x
        )
    }

    override fun onAddcategory(subcategory: Subservicecategories) {
        val existingcart= mutableCart?.value
        val checkingallitems= existingcart?.singleOrNull { it.sid == subcategory.id}
        if(checkingallitems==null){
            val arrayList = ArrayList<Washitems>()
            val washstuff=Washitems(
                subcategory.id,
                subcategory.service,
                subcategory.price.toInt(),
                subcategory.images,
                1
            )
            arrayList.add(washstuff)
            //Toast.makeText(context, arrayList.size.toString(), Toast.LENGTH_SHORT).show()
            cartviewmodel.additems(arrayList)
            /*{proceedtologin()}*/
            requireView().snackbarz("added to laundry basket","checkout",{null})
        }else{
            requireView().snackbarz("already added laundry basket","checkout",{null})
        }

    }

}