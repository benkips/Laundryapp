package com.mamafua.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mamafua.app.R
import com.mamafua.app.Utils.capitalizeString
import com.mamafua.app.databinding.CategoryinfBinding
import com.mamafua.app.models.Category


class Categoryadapter (private val listner:OnItemClickListner): ListAdapter<Category, Categoryadapter.CategoryHolder>(Diffcallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  CategoryHolder {
        val binding=CategoryinfBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder:  CategoryHolder, position: Int) {
        val currentItem=getItem(position)
        holder.bind(currentItem)

    }


    inner  class CategoryHolder(private val binding:CategoryinfBinding): RecyclerView.ViewHolder(binding.root){
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
        fun bind(category:Category){
            binding.apply {
                Glide.with(itemView)
                    .load("http://mamafua.howtoinkenya.co.ke/static/uploads/" + category.images)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivcategory)
                categoryname.text=itemView.context.capitalizeString(category.name)
                tvdesc.text=itemView.context.capitalizeString(category.description)
            }

        }
    }

    interface  OnItemClickListner{
        fun onItemclick(category:Category)
    }

    class Diffcallback: DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category)=
            oldItem.id==newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category)=
            oldItem==newItem


    }
}