package com.mamafua.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mamafua.app.R
import com.mamafua.app.Utils.capitalizeString
import com.mamafua.app.databinding.CategoryinfBinding
import com.mamafua.app.databinding.SubservicecategoryinfBinding
import com.mamafua.app.models.Subservicecategories

class Serviceadapter(private val listener:OnItemClickListner) :
    PagingDataAdapter<Subservicecategories,Serviceadapter.ServiceViewholder>(
        SUBSERVICE_COMPARATOR
    )  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewholder {
        val binding=SubservicecategoryinfBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ServiceViewholder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewholder, position: Int) {
        val currentitem=getItem(position);
        if(currentitem!=null){
            holder.bind(currentitem)
        }

    }

    inner class  ServiceViewholder (private val binding: SubservicecategoryinfBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                val position=bindingAdapterPosition
                if(position!=RecyclerView.NO_POSITION){
                    val item=getItem(position)
                    if(item!=null){
                        listener.onItemClick(item)
                    }
                }


            }
        }
        fun bind(subcategory:Subservicecategories){
            binding.apply {
                Glide.with(itemView)
                    .load("http://mamafua.howtoinkenya.co.ke/static/uploads/" + subcategory.images)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivcategory)
                categoryname.text=itemView.context.capitalizeString(subcategory.service)
                tvprice.text=subcategory.price +"KES"
            }
            binding.addl.setOnClickListener {
                listener.onAddcategory(subcategory)
            }
        }

    }

    interface OnItemClickListner {
        fun onItemClick(subcategory:Subservicecategories)
        fun onAddcategory(subcategory:Subservicecategories)
    }

    companion object {
        private val SUBSERVICE_COMPARATOR = object : DiffUtil.ItemCallback<Subservicecategories>() {
            override fun areItemsTheSame(oldItem: Subservicecategories, newItem: Subservicecategories) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Subservicecategories, newItem: Subservicecategories
            ) = oldItem == newItem

        }
    }



}