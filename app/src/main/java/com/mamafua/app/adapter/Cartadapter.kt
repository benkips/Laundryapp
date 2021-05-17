package com.mamafua.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mamafua.app.R
import com.mamafua.app.databinding.CartinfBinding
import com.mamafua.app.Databasestuff.Washitems
import com.mamafua.app.Utils.capitalizeString

class Cartadapter (private val listner:OnItemClickListner) :ListAdapter<Washitems,Cartadapter.Cartholder> (Diffcallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Cartholder {
        val binding= CartinfBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Cartholder(binding)
    }

    override fun onBindViewHolder(holder: Cartholder, position: Int) {
        val currentItem=getItem(position)
        holder.bind(currentItem)
    }

    inner class Cartholder(private val binding:CartinfBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.apply{
                root.setOnClickListener {
                    val position=adapterPosition
                    if(position!= RecyclerView.NO_POSITION){
                        val category=getItem(position)
                        listner.onItemclick(category)
                    }
                }

            }
        }
        fun bind(wshitem: Washitems){
            binding.apply {
                Glide.with(itemView)
                    .load("http://mamafua.howtoinkenya.co.ke/static/uploads/" + wshitem.images)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivcategory)
                categoryname.text=itemView.context.capitalizeString(wshitem.service)
                tvprice.text="@"+wshitem.price.toString()+"KES"
                qnty.setText(wshitem.quantity.toString())

            }
            binding.rmv.setOnClickListener {
                listner.onItemclick(wshitem)
            }
            binding.qnty.doAfterTextChanged {
                    if (!it.toString().isEmpty()) {
                        listner.onchangeQuantity(it.toString().toInt(), wshitem.sid)
                        if(it.toString().toInt()==0){
                            listner.onItemclick(wshitem)
                        }
                    }
            }

        }
    }

    interface  OnItemClickListner{
        fun onItemclick(wshitem: Washitems)
        fun onchangeQuantity(quanty:Int,id:Int)
    }

    class Diffcallback: DiffUtil.ItemCallback<Washitems>(){
        override fun areItemsTheSame(oldItem: Washitems, newItem: Washitems)=
            oldItem.sid==newItem.sid

        override fun areContentsTheSame(oldItem: Washitems, newItem: Washitems)=
            oldItem==newItem


    }
}